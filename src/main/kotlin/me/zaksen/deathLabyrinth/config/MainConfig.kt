package me.zaksen.deathLabyrinth.config

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.zaksen.deathLabyrinth.config.data.Position

@Serializable
data class MainConfig(
    @SerialName("world")
    val world: String = "world",

    @SerialName("minimal_players")
    val minimalPlayers: Int = 1,

    @SerialName("debug")
    val debug: Boolean = false,

    @SerialName("player_spawn_loc")
    val playerSpawnLocation: Position = Position(2.0, 1.1, 0.0),

    @SerialName("entrance_start")
    val entranceStart: Position = Position(-18.0, 2.0, -4.0),
    @SerialName("entrance_end")
    val entranceEnd: Position = Position(-18.0, 8.0, 3.0),
)
