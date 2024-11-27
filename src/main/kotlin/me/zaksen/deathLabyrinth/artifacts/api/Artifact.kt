package me.zaksen.deathLabyrinth.artifacts.api

import me.zaksen.deathLabyrinth.artifacts.ability.AbilityContainer
import net.kyori.adventure.text.Component
import org.bukkit.event.Event
import org.bukkit.inventory.ItemStack
import java.util.UUID

abstract class Artifact(val name: Component, val rarity: ArtifactRarity) {
    var count = 1
    val abilityContainer = AbilityContainer()
    var ownerUuid: UUID? = null

    fun processAnyEvent(event: Event) {
        abilityContainer.invoke(event)
    }

    abstract fun asItemStack(): ItemStack

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