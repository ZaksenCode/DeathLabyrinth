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
import net.minecraft.world.entity.monster.Skeleton
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import org.bukkit.Location
import org.bukkit.craftbukkit.CraftWorld

class SkeletonArcherEntity(location: Location): Skeleton(EntityType.SKELETON, (location.world as CraftWorld).handle),
    Scaleable {

    init {
        this.getAttribute(Attributes.MAX_HEALTH)?.baseValue = defaultMaxHealth
        this.health = defaultMaxHealth.toFloat()
        this.customName = Component.translatable("entity.skeleton_archer.name").withColor(TextColor.color(124, 242, 81).value())
        this.isCustomNameVisible = true

        this.getAttribute(Attributes.MOVEMENT_SPEED)?.baseValue = 0.24

        this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack(Items.BOW))

        this.setPos(location.x, location.y, location.z)
    }

    override fun registerGoals() {
        goalSelector.addGoal(1, RestrictSunGoal(this))
        goalSelector.addGoal(2, FleeSunGoal(this, 1.0))
        goalSelector.addGoal(3, WaterAvoidingRandomStrollGoal(this, 1.0))
        targetSelector.addGoal(1, HurtByTargetGoal(this,
            Skeleton::class.java,
            SkeletonWarriorEntity::class.java,
            SkeletonArcherEntity::class.java)
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
    }

    companion object {
        const val defaultMaxHealth = 20.0
    }
}