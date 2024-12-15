package me.zaksen.deathLabyrinth.config.data

import kotlinx.serialization.Serializable
import org.bukkit.Location
import org.bukkit.World

@Serializable
data class Position(
    var x: Double = 0.0,
    var y: Double = 0.0,
    var z: Double = 0.0
) {
    fun location(world: World) = Location(world, x, y, z)
}
