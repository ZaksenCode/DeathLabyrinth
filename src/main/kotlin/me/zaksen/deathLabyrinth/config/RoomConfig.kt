package me.zaksen.deathLabyrinth.config

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.zaksen.deathLabyrinth.config.data.Entity
import me.zaksen.deathLabyrinth.config.data.Position
import me.zaksen.deathLabyrinth.game.room.LocationType
import me.zaksen.deathLabyrinth.game.room.RoomType
import me.zaksen.deathLabyrinth.game.room.logic.completion.CompletionCheck
import me.zaksen.deathLabyrinth.game.room.logic.completion.EntityCompletionCheck
import me.zaksen.deathLabyrinth.game.room.logic.tags.RoomTag
import me.zaksen.deathLabyrinth.game.room.logic.tags.StartRoomSpawnOffset
import me.zaksen.deathLabyrinth.game.room.logic.tick.TickProcess

// TODO - add way to store custom tags into room
@Serializable
data class RoomConfig(
    @SerialName("room_size")
    val roomSize: Position = Position(32.0, 32.0, 32.0),

    @SerialName("entrance_offset")
    val entranceOffset: Position = Position(0.0, 1.0, 12.0),

    @SerialName("exit_offset")
    val exitOffset: Position = Position(32.0, 1.0, 12.0),

    @SerialName("room_type")
    val roomType: RoomType = RoomType.NORMAL,

    @SerialName("location_type")
    val locationType: LocationType = LocationType.SHAFT,

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

    @SerialName("completion_condition")
    val completionConditions: List<CompletionCheck> = listOf(
        EntityCompletionCheck()
    ),

    @SerialName("tick_processes")
    val tickProcesses: List<TickProcess> = listOf(

    ),

    @SerialName("tags")
    val tags: List<RoomTag> = listOf(
        StartRoomSpawnOffset()
    )
) {
    inline fun <reified T> getTag(): T? {
        tags.forEach {
            if(it is T) {
                return it
            }
        }

        return null
    }
}
