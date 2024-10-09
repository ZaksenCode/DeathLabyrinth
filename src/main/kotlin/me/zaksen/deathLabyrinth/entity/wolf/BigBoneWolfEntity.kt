package me.zaksen.deathLabyrinth.entity.wolf

import me.zaksen.deathLabyrinth.entity.difficulty.Scaleable
import me.zaksen.deathLabyrinth.entity.difficulty.ScalingStrategies
import net.kyori.adventure.text.format.TextColor
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.ai.goal.*
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
import net.minecraft.world.entity.animal.Wolf
import net.minecraft.world.entity.player.Player
import org.bukkit.Location
import org.bukkit.craftbukkit.CraftWorld

class BigBoneWolfEntity(location: Location): Wolf(EntityType.WOLF, (location.getWorld() as CraftWorld).handle), Scaleable {

    init {
        this.getAttribute(Attributes.MAX_HEALTH)?.baseValue = defaultMaxHealth
        this.health = defaultMaxHealth.toFloat()
        this.customName = Component.translatable("entity.big_wolf.name").withColor(TextColor.color(124, 242, 81).value())
        this.isCustomNameVisible = true
        this.getAttribute(Attributes.SCALE)?.baseValue = 1.5
        this.getAttribute(Attributes.ATTACK_DAMAGE)?.baseValue = defaultAttackDamage
        this.setPos(location.x, location.y, location.z)
    }

    override fun registerGoals() {
        goalSelector.addGoal(1, LeapAtTargetGoal(this, 0.4f))
        goalSelector.addGoal(2, MeleeAttackGoal(this, 1.0, false))
        goalSelector.addGoal(3, WaterAvoidingRandomStrollGoal(this, 1.0))

        targetSelector.addGoal(
            1,
            HurtByTargetGoal(
                this,
                *arrayOfNulls(0)
            )
        )
        targetSelector.addGoal(
            2, NearestAttackableTargetGoal(
                this,
                Player::class.java, true
            )
        )
    }

    override fun checkDespawn() { }

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
        const val defaultMaxHealth = 32.0
        const val defaultAttackDamage = 8.0
    }
}