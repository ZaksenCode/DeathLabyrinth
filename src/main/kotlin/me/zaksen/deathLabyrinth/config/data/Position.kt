package me.zaksen.deathLabyrinth.config.data

import kotlinx.serialization.Serializable
import org.bukkit.Location
import org.bukkit.World

@Serializable
data class Position(
    val x: Double,
    val y: Double,
    val z: Double
) {
    fun location(world: World) = Location(world, x, y, z)
}
