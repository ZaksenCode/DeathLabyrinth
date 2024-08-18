package me.zaksen.deathLabyrinth.config.data

import kotlinx.serialization.Serializable

@Serializable
data class Position(
    val world: String,
    val x: Double,
    val y: Double,
    val z: Double
)
