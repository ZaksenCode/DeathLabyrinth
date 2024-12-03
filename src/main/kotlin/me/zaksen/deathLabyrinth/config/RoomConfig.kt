package me.zaksen.deathLabyrinth.config

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.zaksen.deathLabyrinth.config.data.Entity
import me.zaksen.deathLabyrinth.config.data.Position
import me.zaksen.deathLabyrinth.game.room.RoomType

@Serializable
data class RoomConfig(
    // Размер комнаты (зона, которую нужно будет очистить)
    @SerialName("room_size")
    val roomSize: Position = Position(32.0, 32.0, 32.0),
    @SerialName("room_type")
    val roomType: RoomType = RoomType.NORMAL,

    // Отступ от угла комнаты до входа
    @SerialName("room_entry_offset")
    val spawnEntryOffset: Position = Position(0.0, -1.0, -12.0),
    // Отступ от входа до выхода
    @SerialName("room_exit_offset")
    val spawnExitOffset: Position = Position(32.0, 0.0, 0.0),

    @SerialName("room_entities")
    val roomEntities: List<List<Entity>> = listOf(
        listOf(
            Entity(),
            Entity("big_bone_wolf")
        )
    ),

    @SerialName("pot_spawns")
    val potSpawns: List<Position> = listOf(
        Position(16.0, 3.0, 16.0)
    ),

    @SerialName("boss_artifacts_offset")
    val bossArtifactsSpawnOffset: Position = Position(-24.0, 2.0, 0.0),
    @SerialName("necromancer_spawn_offset")
    val necromancerSpawnOffset: Position = Position(-16.0, 2.0, 3.0),
)
