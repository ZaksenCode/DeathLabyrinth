package me.zaksen.deathLabyrinth.entity.difficulty

@FunctionalInterface
fun interface ScalingStrategy {
    fun scale(base: Double): Double
}