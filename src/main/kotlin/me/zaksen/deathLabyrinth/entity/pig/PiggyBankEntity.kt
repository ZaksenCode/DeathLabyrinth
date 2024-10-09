package me.zaksen.deathLabyrinth.entity.pig

import me.zaksen.deathLabyrinth.data.PlayerData
import me.zaksen.deathLabyrinth.game.GameController
import net.kyori.adventure.text.format.TextColor
import net.minecraft.network.chat.Component
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal
import net.minecraft.world.entity.animal.Pig
import net.minecraft.world.entity.player.Player
import org.bukkit.Location
import org.bukkit.craftbukkit.CraftWorld

class PiggyBankEntity(location: Location): Pig(EntityType.PIG, (location.getWorld() as CraftWorld).handle) {

    init {
        this.getAttribute(Attributes.MAX_HEALTH)?.baseValue = 2.0
        this.health = 2.0f
        this.customName = Component.translatable("entity.piggy_bank.name").withColor(TextColor.color(124, 242, 81).value())
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

    override fun hurt(source: DamageSource, amount: Float): Boolean {
        val attacker = source.entity

        if(attacker is org.bukkit.entity.Player) {
            val data = GameController.players[attacker] ?: return false

            data.money += amount.toInt() * 2
            GameController.players[attacker] = data
        }

        return super.hurt(source, amount)
    }

    override fun checkDespawn() { }

    override fun dropExperience(attacker: Entity?) { }

    override fun dropEquipment() { }

    override fun shouldDropLoot(): Boolean {
        return false
    }
}