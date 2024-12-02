package me.zaksen.deathLabyrinth.entity.friendly.slime

import com.destroystokyo.paper.event.entity.SlimeChangeDirectionEvent
import com.destroystokyo.paper.event.entity.SlimeTargetLivingEntityEvent
import com.destroystokyo.paper.event.entity.SlimeWanderEvent
import me.zaksen.deathLabyrinth.entity.EnemyMarketable
import me.zaksen.deathLabyrinth.entity.friendly.FriendlyEntity
import me.zaksen.deathLabyrinth.entity.goal.ability.toLocation
import me.zaksen.deathLabyrinth.entity.trader.Trader
import me.zaksen.deathLabyrinth.game.GameController
import net.kyori.adventure.text.format.TextColor
import net.minecraft.network.chat.Component
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.ai.control.MoveControl
import net.minecraft.world.entity.ai.goal.Goal
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
import net.minecraft.world.entity.monster.Slime
import net.minecraft.world.entity.player.Player
import org.bukkit.Location
import org.bukkit.craftbukkit.CraftWorld
import java.util.*

class BlobEntity(location: Location, val owner: org.bukkit.entity.Player): Slime(EntityType.SLIME, (location.world as CraftWorld).handle), FriendlyEntity, EnemyMarketable {

    private var despawnTime = 30 * 20

    init {
        this.fixupDimensions()
        this.moveControl = SlimeMoveControl(this)

        this.setSize(1, true)

        this.getAttribute(Attributes.MAX_HEALTH)?.baseValue = 100.0
        this.health = 100.0f
        this.customName = Component.translatable("entity.blob.name").withColor(TextColor.color(124, 242, 81).value())
        this.isCustomNameVisible = true
        this.getAttribute(Attributes.MOVEMENT_SPEED)?.baseValue = 0.25
        this.getAttribute(Attributes.ATTACK_DAMAGE)?.baseValue = 8.0
        this.getAttribute(Attributes.SCALE)?.baseValue = 1.5

        this.setPos(location.x, location.y, location.z)
    }

    override fun registerGoals() {
        goalSelector.addGoal(1, SlimeAttackGoal(this))
        goalSelector.addGoal(2, SlimeRandomDirectionGoal(this))
        goalSelector.addGoal(3, SlimeKeepOnJumpingGoal(this))

        targetSelector.addGoal(
            1, NearestAttackableTargetGoal(
                this,
                LivingEntity::class.java, false
            ) {
                it.isAlive && it !is Player && it !is FriendlyEntity && it !is Trader
            }
        )
    }

    override fun checkDespawn() {
        despawnTime--

        if(despawnTime <= 0) {
            GameController.makeExplode(
                owner,
                this.position().toLocation(this.level().world),
                1.5,
                10.0
            )
            this.discard()
        }
    }

    override fun die(damageSource: DamageSource) {
        GameController.makeExplode(
            owner,
            this.position().toLocation(this.level().world),
            1.5,
            10.0
        )
        super.die(damageSource)
    }

    override fun dropExperience(attacker: Entity?) { }

    override fun dropEquipment() { }

    override fun shouldDropLoot(): Boolean {
        return false
    }

    override fun hurt(source: DamageSource, amount: Float): Boolean {
        if(source.entity != null && (source.entity is Player || source.entity is FriendlyEntity)) {
            return false
        }

        GameController.makeExplode(
            owner,
            this.position().toLocation(this.level().world),
            1.0,
            5.0
        )

        return super.hurt(source, amount)
    }

    override fun isTiny(): Boolean {
        return false
    }

    override fun isDealsDamage(): Boolean {
        return true
    }

    fun jumpDelay(): Int {
        return jumpDelay
    }
}

class SlimeAttackGoal(private val slime: BlobEntity) : Goal() {
    private var growTiredTimer = 0

    init {
        this.setFlags(EnumSet.of(Flag.LOOK))
    }

    override fun canUse(): Boolean {
        val entityliving = slime.target

        // Paper start - Slime pathfinder events
        if (entityliving == null || !entityliving.isAlive) {
            return false
        }
        if (!slime.canAttack(entityliving)) {
            return false
        }
        return slime.moveControl is SlimeMoveControl && slime.canWander() && SlimeTargetLivingEntityEvent(
            slime.bukkitEntity as org.bukkit.entity.Slime, entityliving.bukkitEntity as org.bukkit.entity.LivingEntity
        ).callEvent()
        // Paper end - Slime pathfinder events
    }

    override fun start() {
        this.growTiredTimer = reducedTickDelay(300)
        super.start()
    }

    override fun canContinueToUse(): Boolean {
        val entityliving = slime.target

        // Paper start - Slime pathfinder events
        if (entityliving == null || !entityliving.isAlive) {
            return false
        }
        if (!slime.canAttack(entityliving)) {
            return false
        }
        return --this.growTiredTimer > 0 && slime.canWander() && SlimeTargetLivingEntityEvent(
            slime.bukkitEntity as org.bukkit.entity.Slime, entityliving.bukkitEntity as org.bukkit.entity.LivingEntity
        ).callEvent()
        // Paper end - Slime pathfinder events
    }

    override fun requiresUpdateEveryTick(): Boolean {
        return true
    }

    override fun tick() {
        val entityliving = slime.target

        if (entityliving != null) {
            slime.lookAt(entityliving, 10.0f, 10.0f)
        }

        val controllermove = slime.moveControl

        if (controllermove is SlimeMoveControl) {
            controllermove.setDirection(slime.yRot, true)
        }
    }

    // Paper start - Slime pathfinder events; clear timer and target when goal resets
    override fun stop() {
        this.growTiredTimer = 0
        slime.target = null
    } // Paper end - Slime pathfinder events
}

class SlimeRandomDirectionGoal(private val slime: BlobEntity) : Goal() {
    private var chosenDegrees = 0f
    private var nextRandomizeTime = 0

    init {
        this.setFlags(EnumSet.of(Flag.LOOK))
    }

    override fun canUse(): Boolean {
        return slime.target == null && (slime.onGround() || slime.isInWater || slime.isInLava || slime.hasEffect(
            MobEffects.LEVITATION
        )) && slime.moveControl is SlimeMoveControl && slime.canWander() // Paper - Slime pathfinder events
    }

    override fun tick() {
        if (--this.nextRandomizeTime <= 0) {
            this.nextRandomizeTime = this.adjustedTickDelay(40 + slime.getRandom().nextInt(60))
            this.chosenDegrees = slime.getRandom().nextInt(360).toFloat()
            // Paper start - Slime pathfinder events
            val event = SlimeChangeDirectionEvent(slime.bukkitEntity as org.bukkit.entity.Slime, this.chosenDegrees)
            if (!slime.canWander() || !event.callEvent()) return
            this.chosenDegrees = event.newYaw
            // Paper end - Slime pathfinder events
        }

        val controllermove = slime.moveControl

        if (controllermove is SlimeMoveControl) {
            controllermove.setDirection(this.chosenDegrees, false)
        }
    }
}

class SlimeKeepOnJumpingGoal(private val slime: BlobEntity) : Goal() {
    init {
        this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE))
    }

    override fun canUse(): Boolean {
        return !slime.isPassenger && slime.canWander() && SlimeWanderEvent(slime.bukkitEntity as org.bukkit.entity.Slime).callEvent() // Paper - Slime pathfinder events
    }

    override fun tick() {
        val controllermove = slime.moveControl

        if (controllermove is SlimeMoveControl) {
            controllermove.setWantedMovement(1.0)
        }
    }
}

class SlimeMoveControl(private val slime: BlobEntity) : MoveControl(slime) {
    private var yRot: Float
    private var jumpDelay = 0
    private var isAggressive = false

    init {
        this.yRot = 180.0f * slime.yRot / 3.1415927f
    }

    fun setDirection(targetYaw: Float, jumpOften: Boolean) {
        this.yRot = targetYaw
        this.isAggressive = jumpOften
    }

    fun setWantedMovement(speed: Double) {
        this.speedModifier = speed
        this.operation = Operation.MOVE_TO
    }

    override fun tick() {
        mob.yRot = this.rotlerp(mob.yRot, this.yRot, 90.0f)
        mob.yHeadRot = mob.yRot
        mob.yBodyRot = mob.yRot
        if (this.operation != Operation.MOVE_TO) {
            mob.setZza(0.0f)
        } else {
            this.operation = Operation.WAIT
            if (mob.onGround()) {
                mob.speed = (this.speedModifier * mob.getAttributeValue(Attributes.MOVEMENT_SPEED)).toFloat()
                if (jumpDelay-- <= 0) {
                    this.jumpDelay = slime.jumpDelay()
                    if (this.isAggressive) {
                        this.jumpDelay /= 3
                    }

                    slime.jumpControl.jump()
                    slime.playSound(
                        SoundEvents.SLIME_JUMP_SMALL,
                        slime.soundVolume,
                        1.4f
                    )
                } else {
                    slime.xxa = 0.0f
                    slime.zza = 0.0f
                    mob.speed = 0.0f
                }
            } else {
                mob.speed = (this.speedModifier * mob.getAttributeValue(Attributes.MOVEMENT_SPEED)).toFloat()
            }
        }
    }
}