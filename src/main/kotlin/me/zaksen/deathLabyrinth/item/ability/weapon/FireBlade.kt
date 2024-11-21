package me.zaksen.deathLabyrinth.item.ability.weapon

import me.zaksen.deathLabyrinth.event.item.ItemHitEvent
import me.zaksen.deathLabyrinth.item.ability.ItemAbility
import net.kyori.adventure.text.Component
import org.bukkit.event.Event

class FireBlade: ItemAbility(
    Component.translatable("ability.fire_blade.name"),
    Component.translatable("ability.fire_blade.description")
) {
    override fun invoke(event: Event) {
        if(event !is ItemHitEvent) return
        event.damaged.fireTicks = 100
        event.damaged.maxFireTicks
    }
}