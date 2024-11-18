package me.zaksen.deathLabyrinth.item.ability.consume

import me.zaksen.deathLabyrinth.event.item.ItemConsumeEvent
import me.zaksen.deathLabyrinth.item.ability.ItemAbility
import net.kyori.adventure.text.Component
import org.bukkit.attribute.Attribute
import org.bukkit.event.Event
import org.bukkit.event.entity.EntityRegainHealthEvent

class SmallHealEffect: ItemAbility(
    Component.translatable("ability.small_heal_effect.name"),
    Component.translatable("ability.small_heal_effect.description")
) {
    override fun invoke(event: Event) {
        if(event !is ItemConsumeEvent) return

        val player =  event.player
        val toRegen = player.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.value * 0.25
        player.heal(toRegen, EntityRegainHealthEvent.RegainReason.REGEN)
    }
}