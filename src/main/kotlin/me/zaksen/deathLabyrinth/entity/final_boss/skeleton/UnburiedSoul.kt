package me.zaksen.deathLabyrinth.entity.final_boss.skeleton

import me.zaksen.deathLabyrinth.entity.EnemyMarketable
import me.zaksen.deathLabyrinth.entity.difficulty.Scaleable
import me.zaksen.deathLabyrinth.entity.difficulty.ScalingStrategies
import me.zaksen.deathLabyrinth.entity.goal.ability.*
import net.kyori.adventure.text.format.TextColor
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
import net.minecraft.world.entity.monster.Stray
import net.minecraft.world.entity.player.Player
import org.bukkit.Location
import org.bukkit.craftbukkit.CraftWorld

class UnburiedSoul(location: Location): Stray(EntityType.STRAY, (location.world as CraftWorld).handle), Scaleable {

    init {
        this.getAttribute(Attributes.MOVEMENT_SPEED)?.baseValue = 0.3
        this.getAttribute(Attributes.ATTACK_DAMAGE)?.baseValue = defaultAttackDamage
        this.getAttribute(Attributes.MAX_HEALTH)?.baseValue = defaultMaxHealth
        this.getAttribute(Attributes.SCALE)?.baseValue = 1.5
        this.health = defaultMaxHealth.toFloat()

        this.customName = Component.translatable("entity.unburied_soul.name").withColor(TextColor.color(124, 242, 81).value())
        this.isCustomNameVisible = true

        this.setPos(location.x, location.y, location.z)
    }

    override fun registerGoals() {
        goalSelector.addGoal(0, CastWindBallGoal(this, 12.0, 2.0))
        goalSelector.addGoal(1, SummonDruidsGoal(this))
        goalSelector.addGoal(2, StunPlayersGoal(this))

        targetSelector.addGoal(1, HurtByTargetGoal(this, *arrayOfNulls(0)))
        targetSelector.addGoal(
            2, NearestAttackableTargetGoal(
                this,
                LivingEntity::class.java,
                true
            ) {
                it is Player || it is EnemyMarketable
            }
        )
    }

    override fun checkDespawn() { }

    override fun dropExperience(attacker: Entity?) { }

    override fun dropEquipment() { }

    override fun shouldDropLoot(): Boolean {
        return false
    }

    override fun scale() {
        this.getAttribute(Attributes.MAX_HEALTH)?.baseValue = ScalingStrategies.BY_COMPLETED_BOSSES.strategy.scale(defaultMaxHealth)
        this.health = ScalingStrategies.BY_COMPLETED_BOSSES.strategy.scale(defaultMaxHealth).toFloat()
        this.getAttribute(Attributes.ATTACK_DAMAGE)?.baseValue = ScalingStrategies.BY_COMPLETED_BOSSES.strategy.scale(defaultAttackDamage)
    }

    companion object {
        const val defaultMaxHealth = 2500.0
        const val defaultAttackDamage = 25.0
    }
}