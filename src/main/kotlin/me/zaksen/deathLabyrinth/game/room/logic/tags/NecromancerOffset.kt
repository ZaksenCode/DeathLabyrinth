package me.zaksen.deathLabyrinth.game.room.logic.tags

import kotlinx.serialization.Serializable
import me.zaksen.deathLabyrinth.config.data.Position

@Serializable
data class NecromancerOffset(
    val offset: Position = Position(16.0, 2.0, 16.0)
) : RoomTag