package me.zaksen.deathLabyrinth.game.room.logic.tags

import kotlinx.serialization.Serializable
import me.zaksen.deathLabyrinth.config.data.Position

@Serializable
data class StartRoomSpawnOffset(
    val offset: Position = Position(16.0, 1.5, 16.0)
) : RoomTag {
    override fun addOffset(x: Int, y: Int, z: Int) {
        offset.x += x
        offset.y += y
        offset.z += z
    }
}