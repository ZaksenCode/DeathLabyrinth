package me.zaksen.deathLabyrinth.item.ability.weapon

import me.zaksen.deathLabyrinth.event.item.ItemKillEvent
import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.item.ability.ItemAbility
import net.kyori.adventure.text.Component
import org.bukkit.attribute.Attribute
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.Event
import kotlin.random.Random

class Robbery: ItemAbility(
    Component.translatable("ability.robbery.name"),
    Component.translatable("ability.robbery.description"),
    isDisplayDamageType = false
) {
    val random: Random = Random.Default

    override fun invoke(event: Event) {
        if(event !is ItemKillEvent) return

        val entity = event.damaged
        val damager = event.damager

        if(entity !is LivingEntity) return

        if(damager is Player) {
            GameController.addMoney(damager, (entity.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.value / 4).toInt())
        }
    }
}