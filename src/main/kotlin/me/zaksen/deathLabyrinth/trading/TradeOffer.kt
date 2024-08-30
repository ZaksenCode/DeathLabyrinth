package me.zaksen.deathLabyrinth.trading

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

data class TradeOffer(
    var count: Int = 1,
    val price: Int = 10,
    val stack: ItemStack = ItemStack(Material.AIR),
    val buy: Boolean = true
)