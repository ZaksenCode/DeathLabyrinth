package me.zaksen.deathLabyrinth.item.ability

import me.zaksen.deathLabyrinth.artifacts.ability.Ability
import net.kyori.adventure.text.Component
import org.bukkit.event.Event

abstract class ItemAbility(val name: Component, val description: Component): Ability {
    abstract override fun invoke(event: Event)
}