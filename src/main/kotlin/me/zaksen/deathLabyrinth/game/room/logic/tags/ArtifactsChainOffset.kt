package me.zaksen.deathLabyrinth.game.room.logic.tags

import kotlinx.serialization.Serializable
import me.zaksen.deathLabyrinth.config.data.Position

@Serializable
data class ArtifactsChainOffset(
    val offset: Position = Position(16.0, 3.0, 16.0)
) : RoomTag