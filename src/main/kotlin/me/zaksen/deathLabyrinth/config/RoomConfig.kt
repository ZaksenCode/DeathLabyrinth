package me.zaksen.deathLabyrinth.config

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.zaksen.deathLabyrinth.config.data.Position
import net.minecraft.core.Direction

@Serializable
data class RoomConfig(
    @SerialName("spawn_pos")
    val spawnPos: Position,

    @SerialName("open_sides")
    val openSides: List<Direction>
)
