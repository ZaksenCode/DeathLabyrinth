package me.zaksen.deathLabyrinth.entity.pig

import net.kyori.adventure.text.format.TextColor
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal
import net.minecraft.world.entity.animal.Pig
import net.minecraft.world.entity.player.Player
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.craftbukkit.CraftWorld

class PiggyBankEntity(location: Location): Pig(EntityType.PIG, (location.getWorld() as CraftWorld).handle) {

    init {
        this.getAttribute(Attributes.MAX_HEALTH)?.baseValue = 8.0
        this.health = 8.0f
        this.customName = Component.literal("Свинья-копилка").withColor(TextColor.color(124, 242, 81).value())
        this.isCustomNameVisible = true
        this.getAttribute(Attributes.SCALE)?.baseValue = 0.7

        this.getAttribute(Attributes.MOVEMENT_SPEED)?.baseValue = 0.4

        this.setPos(location.x, location.y, location.z)
    }

    override fun registerGoals() {
        goalSelector.addGoal(
            1, AvoidEntityGoal(
                this,
                Player::class.java, 8.0f, 0.8, 1.0
            )
        )
    }

    override fun checkDespawn() { }
}