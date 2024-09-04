package me.zaksen.deathLabyrinth.artifacts.ability

import org.bukkit.event.Event

fun interface Ability {
    fun invoke(event: Event)
}