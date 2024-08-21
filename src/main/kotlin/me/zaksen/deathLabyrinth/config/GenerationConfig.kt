package me.zaksen.deathLabyrinth.config

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.zaksen.deathLabyrinth.config.data.Position

@Serializable
data class GenerationConfig(
    @SerialName("room_limit")
    val roomLimit: Int = 32,
    @SerialName("room_size")
    val roomSize: Int = 32,
    @SerialName("room_spawning_pos")
    val roomSpawningPos: Position = Position("world", -18.0, 2.0, 15.0)
)
