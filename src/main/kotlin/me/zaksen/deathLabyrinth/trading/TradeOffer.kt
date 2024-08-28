package me.zaksen.deathLabyrinth.trading

import me.zaksen.deathLabyrinth.trading.pricing.PricingStrategy
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

data class TradeOffer(
    val count: Int = 1,
    val price: Int = 10,
    val stack: ItemStack = ItemStack(Material.AIR),
    val pricingStrategy: PricingStrategy
)