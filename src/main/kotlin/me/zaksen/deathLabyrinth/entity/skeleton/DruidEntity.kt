package me.zaksen.deathLabyrinth.entity.skeleton

import me.zaksen.deathLabyrinth.entity.difficulty.Scaleable
import me.zaksen.deathLabyrinth.entity.difficulty.ScalingStrategies
import net.kyori.adventure.text.format.TextColor
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

class DruidEntity(location: Location): Stray(EntityType.STRAY, (location.world as CraftWorld).handle), Scaleable {

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
        goalSelector.addGoal(
            1, AvoidEntityGoal(
                this,
                Player::class.java, 6.0f, 0.4, 0.6
            )
        )
        goalSelector.addGoal(2, WaterAvoidingRandomStrollGoal(this, 1.0))
        goalSelector.addGoal(3, LookAtPlayerGoal(this, Player::class.java, 3.0f, 1.0f))
        targetSelector.addGoal(1, HurtByTargetGoal(this, *arrayOfNulls(0)))
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
        const val defaultMaxHealth = 25.0
        const val defaultAttackDamage = 8.0
    }
}