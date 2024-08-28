package me.zaksen.deathLabyrinth.entity.silverfish

import net.kyori.adventure.text.format.TextColor
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.ai.goal.FloatGoal
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
import net.minecraft.world.entity.monster.Silverfish
import net.minecraft.world.entity.player.Player
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.craftbukkit.CraftWorld

class MouseEntity(location: Location): Silverfish(EntityType.SILVERFISH, (location.getWorld() as CraftWorld).handle) {

    init {
        this.getAttribute(Attributes.MAX_HEALTH)?.baseValue = 10.0
        this.health = 10.0f
        this.customName = Component.literal("Мышь").withColor(TextColor.color(124, 242, 81).value())
        this.isCustomNameVisible = true

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
}