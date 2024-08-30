package me.zaksen.deathLabyrinth.entity.villager

import net.kyori.adventure.text.format.TextColor
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.ai.goal.FloatGoal
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
import net.minecraft.world.entity.npc.Villager
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import org.bukkit.Location
import org.bukkit.attribute.Attribute
import org.bukkit.craftbukkit.CraftWorld

class VillagerTwoEntity(location: Location): Villager(EntityType.VILLAGER, (location.getWorld() as CraftWorld).handle) {

    init {
        this.getAttribute(Attributes.MAX_HEALTH)?.baseValue = 30.0
        this.health = 30.0f
        this.customName = Component.literal("Крестьянин").withColor(TextColor.color(124, 242, 81).value())
        this.isCustomNameVisible = true
        
        this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack(Items.IRON_HOE))

        this.getAttribute(Attributes.MOVEMENT_SPEED)?.baseValue = 0.22
        this.craftAttributes.registerAttribute(Attribute.GENERIC_ATTACK_DAMAGE)
        this.getAttribute(Attributes.ATTACK_DAMAGE)?.baseValue = 8.0

        this.setPos(location.x, location.y, location.z)
    }

    override fun registerGoals() {
        goalSelector.addGoal(1, FloatGoal(this))
        goalSelector.addGoal(2, MeleeAttackGoal(this, 1.0, false))
        targetSelector.addGoal(
            1,
            HurtByTargetGoal(this, *arrayOfNulls(0)).setAlertOthers()
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
}