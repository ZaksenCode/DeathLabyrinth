package me.zaksen.deathLabyrinth.game.pot

import org.bukkit.inventory.ItemStack

data class PotEntry(
    var stack: ItemStack,
    val isStackable: Boolean = false
)