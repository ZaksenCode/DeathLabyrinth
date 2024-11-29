package me.zaksen.deathLabyrinth.item.accessory.ability

import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.inventory.ItemStack

fun interface AccessoryAbility {
    fun invoke(event: Event, itemStack: ItemStack, player: Player)
}