package me.zaksen.deathLabyrinth.trading.pricing

@FunctionalInterface
interface PricingStrategy {
    fun scale(base: Int): Int {
        return base
    }
}