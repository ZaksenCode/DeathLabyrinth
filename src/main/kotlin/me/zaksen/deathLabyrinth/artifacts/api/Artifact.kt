package me.zaksen.deathLabyrinth.artifacts.api

import me.zaksen.deathLabyrinth.artifacts.ability.AbilityContainer
import me.zaksen.deathLabyrinth.classes.PlayerClass
import org.bukkit.event.Event
import org.bukkit.inventory.ItemStack

// TODO - добавить игрокам артифакты
abstract class Artifact(val name: String) {
    var count = 1
    val abilityContainer = AbilityContainer()

    fun processAnyEvent(event: Event) {
        abilityContainer.invoke(event)
    }

    abstract fun asItemStack(): ItemStack

    abstract fun usableFor(): Set<Class<out PlayerClass>>
}