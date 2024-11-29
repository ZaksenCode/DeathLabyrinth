package me.zaksen.deathLabyrinth.item.accessory.ability

import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.inventory.ItemStack

class AccessoryAbilityContainer {
    private val abilities: MutableSet<AccessoryAbility> = mutableSetOf()

    fun invoke(event: Event, stack: ItemStack, player: Player) {
        abilities.forEach {
            it.invoke(event, stack, player)
        }
    }

    fun add(ability: AccessoryAbility) {
        abilities.add(ability)
    }
}