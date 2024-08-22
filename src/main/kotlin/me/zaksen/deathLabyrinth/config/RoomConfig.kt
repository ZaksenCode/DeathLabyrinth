package me.zaksen.deathLabyrinth.config

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.zaksen.deathLabyrinth.config.data.Entity
import me.zaksen.deathLabyrinth.config.data.Position
import me.zaksen.deathLabyrinth.game.room.RoomType

// TODO - Удалить поле spawn_offset и заменить в коде на room_entry_offset и room_exit_offset
@Serializable
data class RoomConfig(
    @SerialName("spawn_offset")
    val spawnOffset: Position = Position("world", 0.0, 0.0, 0.0),

    @SerialName("room_entry_offset")
    val spawnEntryOffset: Position = Position("world", 0.0, 1.0, 12.0),
    @SerialName("room_exit_offset")
    val spawnExitOffset: Position = Position("world", 32.0, 1.0, 12.0),

    @SerialName("room_type")
    val roomType: RoomType = RoomType.NORMAL,
    @SerialName("room_entities")
    val roomEntities: List<List<Entity>> = listOf(
        listOf(
            Entity(),
            Entity("big_bone_wolf")
        )
    ),
    @SerialName("pot_spawns")
    val potSpawns: List<Position> = listOf(
        Position("world", 16.0, 3.0, 16.0)
    )
)
