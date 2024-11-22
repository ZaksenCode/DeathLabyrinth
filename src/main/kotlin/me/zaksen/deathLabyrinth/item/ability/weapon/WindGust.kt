package me.zaksen.deathLabyrinth.item.ability.weapon

import me.zaksen.deathLabyrinth.event.item.ItemUseEvent
import me.zaksen.deathLabyrinth.item.ability.ItemAbility
import net.kyori.adventure.text.Component
import org.bukkit.event.Event
import org.bukkit.event.block.Action

class WindGust: ItemAbility(
    Component.translatable("ability.wind_gust.name"),
    Component.translatable("ability.wind_gust.description")
) {
    override fun invoke(event: Event) {
        if(event !is ItemUseEvent) return

        val stack = event.stack!!
        val item = event.item!!

        if((event.event.action == Action.RIGHT_CLICK_AIR || event.event.action == Action.RIGHT_CLICK_BLOCK) && item.checkCooldown(stack)) {
            val leapVelocity = event.player.location.direction.multiply(2).normalize()
            event.player.velocity = leapVelocity
        }
    }
}