package me.zaksen.deathLabyrinth.entity.skeleton

import me.zaksen.deathLabyrinth.entity.difficulty.Scaleable
import me.zaksen.deathLabyrinth.entity.difficulty.ScalingStrategies
import me.zaksen.deathLabyrinth.entity.goal.ability.CastWindBallGoal
import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.game.room.RoomController
import net.kyori.adventure.text.format.TextColor
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.ai.goal.*
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
import net.minecraft.world.entity.monster.Stray
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import org.bukkit.Location
import org.bukkit.craftbukkit.CraftWorld

class DespawningDruidEntity(location: Location): Stray(EntityType.STRAY, (location.world as CraftWorld).handle), Scaleable {

    private var despawnTicks = 30 * 20

    init {
        this.getAttribute(Attributes.MAX_HEALTH)?.baseValue = defaultMaxHealth
        this.health = defaultMaxHealth.toFloat()
        this.customName = Component.translatable("entity.druid.name").withColor(TextColor.color(124, 242, 81).value())
        this.isCustomNameVisible = true

        this.getAttribute(Attributes.MOVEMENT_SPEED)?.baseValue = 0.23
        this.getAttribute(Attributes.ATTACK_DAMAGE)?.baseValue = defaultAttackDamage

        this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack(Items.STICK))

        this.setPos(location.x, location.y, location.z)
    }

    override fun registerGoals() {
        goalSelector.addGoal(0, FloatGoal(this))
        goalSelector.addGoal(1, WaterAvoidingRandomStrollGoal(this, 1.0))
        goalSelector.addGoal(2, LookAtPlayerGoal(this, Player::class.java, 12.0f, 1.0f))
        goalSelector.addGoal(3, CastWindBallGoal(this, this.getAttribute(Attributes.ATTACK_DAMAGE)!!.baseValue, 1.0))
        goalSelector.addGoal(4, AvoidEntityGoal(this, Player::class.java, 8.0f, 1.2, 1.8))
        targetSelector.addGoal(1, HurtByTargetGoal(this, *arrayOfNulls(0)))
        targetSelector.addGoal(
            2, NearestAttackableTargetGoal(
                this,
                Player::class.java, true
            )
        )
    }

    override fun checkDespawn() {
        despawnTicks--

        if(despawnTicks <= 0) {
            for(i in 0..30) {
                this.level().addParticle(
                    ParticleTypes.EFFECT,
                    x,
                    y,
                    z,
                    0.2,
                    0.4,
                    0.2
                )
            }

            EventManager.callPlayerKillEntityEvent(null, this.bukkitLivingEntity, listOf())
            this.discard()
        }
    }

    override fun dropExperience(attacker: Entity?) { }

    override fun dropEquipment() { }

    override fun shouldDropLoot(): Boolean {
        return false
    }

    override fun scale() {
        this.getAttribute(Attributes.MAX_HEALTH)?.baseValue = ScalingStrategies.DEFAULT.strategy.scale(defaultMaxHealth)
        this.health = ScalingStrategies.DEFAULT.strategy.scale(defaultMaxHealth).toFloat()
        this.getAttribute(Attributes.ATTACK_DAMAGE)?.baseValue = ScalingStrategies.DEFAULT.strategy.scale(defaultAttackDamage)
    }

    companion object {
        const val defaultMaxHealth = 25.0
        const val defaultAttackDamage = 8.0
    }
}