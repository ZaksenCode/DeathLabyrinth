package me.zaksen.deathLabyrinth.artifacts.ability

import org.bukkit.event.Event

class AbilityContainer {
    private val abilities: MutableSet<Ability> = mutableSetOf()

    fun invoke(event: Event) {
        abilities.forEach {
            it.invoke(event)
        }
    }

    fun add(ability: Ability) {
        abilities.add(ability)
    }
}