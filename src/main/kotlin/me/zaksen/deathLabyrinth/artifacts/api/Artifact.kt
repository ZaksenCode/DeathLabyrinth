package me.zaksen.deathLabyrinth.artifacts.api

import me.zaksen.deathLabyrinth.artifacts.ability.AbilityContainer
import org.bukkit.event.Event
import org.bukkit.inventory.ItemStack

abstract class Artifact(val name: String, val rarity: ArtifactRarity) {
    var count = 1
    val abilityContainer = AbilityContainer()

    fun processAnyEvent(event: Event) {
        abilityContainer.invoke(event)
    }

    abstract fun asItemStack(): ItemStack

    // Maybe not good idea checking by name
    override fun equals(other: Any?): Boolean {
        if(other !is Artifact) {
            return false
        }

        if(other.name == name) {
            return true
        }

        return false
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + count
        result = 31 * result + abilityContainer.hashCode()
        return result
    }
}