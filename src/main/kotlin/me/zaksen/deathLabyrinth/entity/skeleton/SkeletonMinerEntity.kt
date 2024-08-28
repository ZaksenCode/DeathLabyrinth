package me.zaksen.deathLabyrinth.entity.skeleton

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
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.craftbukkit.CraftWorld

class SkeletonMinerEntity(location: Location): Skeleton(EntityType.SKELETON, (location.getWorld() as CraftWorld).handle) {

    init {
        this.getAttribute(Attributes.MAX_HEALTH)?.baseValue = 50.0
        this.health = 50.0f
        this.customName = Component.literal("Скелет-шахтёр").withColor(TextColor.color(124, 242, 81).value())
        this.isCustomNameVisible = true

        this.getAttribute(Attributes.MOVEMENT_SPEED)?.baseValue = 0.2

        this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack(Items.IRON_PICKAXE))

        this.setPos(location.x, location.y, location.z)
    }

    override fun registerGoals() {
        goalSelector.addGoal(1, MeleeAttackGoal(this, 1.0, false))
        goalSelector.addGoal(2, WaterAvoidingRandomStrollGoal(this, 1.0))
        targetSelector.addGoal(1, HurtByTargetGoal(this,
            Skeleton::class.java,
            SkeletonArcherEntity::class.java,
            SkeletonWarriorEntity::class.java
        ))
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
}