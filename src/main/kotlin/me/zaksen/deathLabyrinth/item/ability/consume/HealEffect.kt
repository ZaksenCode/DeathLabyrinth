package me.zaksen.deathLabyrinth.item.ability.consume

import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.event.item.ItemConsumeEvent
import me.zaksen.deathLabyrinth.item.ItemsController
import me.zaksen.deathLabyrinth.item.ability.ItemAbility
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.event.Event
import org.bukkit.event.entity.EntityRegainHealthEvent

class HealEffect: ItemAbility(
    Component.translatable("ability.heal_effect.name"),
    Component.translatable("ability.heal_effect.description"),
    isDisplayDamageType = false
) {
    override fun invoke(event: Event) {
        if(event !is ItemConsumeEvent) return

        EventManager.callPlayerHealingEvent(event.player, event.player, event.player.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.value * 0.6)

        event.event.replacement = ItemsController.get("bottle")!!.asItemStack()
    }
}