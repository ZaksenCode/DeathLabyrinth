package me.zaksen.deathLabyrinth.shop

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

data class TradeOffer(
    val price: Int = 10,
    val stack: ItemStack = ItemStack(Material.AIR)
)
