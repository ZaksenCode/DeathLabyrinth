package me.zaksen.deathLabyrinth.config

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.zaksen.deathLabyrinth.config.data.Position

@Serializable
data class MainConfig(
    @SerialName("world")
    val world: String = "world",

    @SerialName("room_spawn_loc")
    val roomSpawnLocation: Position = Position(32.0, 0.0, 32.0),

    @SerialName("minimal_players")
    val minimalPlayers: Int = 1,

    @SerialName("debug")
    val debug: Boolean = false,

    @SerialName("player_spawn_loc")
    val playerSpawnLocation: Position = Position(2.0, 1.1, 0.0),
)
