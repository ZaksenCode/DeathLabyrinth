package me.zaksen.deathLabyrinth.config

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.zaksen.deathLabyrinth.config.data.Position
import me.zaksen.deathLabyrinth.game.room.LocationType
import me.zaksen.deathLabyrinth.game.room.RoomType
import me.zaksen.deathLabyrinth.game.room.logic.completion.CompletionCheck
import me.zaksen.deathLabyrinth.game.room.logic.completion.EntityCompletionCheck
import me.zaksen.deathLabyrinth.game.room.logic.completion.FollowMinecartExcept
import me.zaksen.deathLabyrinth.game.room.logic.start.SpawnEntitiesProcess
import me.zaksen.deathLabyrinth.game.room.logic.start.StartProcess
import me.zaksen.deathLabyrinth.game.room.logic.tags.EntitiesPool
import me.zaksen.deathLabyrinth.game.room.logic.tags.RoomTag
import me.zaksen.deathLabyrinth.game.room.logic.tags.StartRoomSpawnOffset
import me.zaksen.deathLabyrinth.game.room.logic.tick.HeightMinLimit
import me.zaksen.deathLabyrinth.game.room.logic.tick.SpawnEntitiesByTime
import me.zaksen.deathLabyrinth.game.room.logic.tick.SpawnEntitiesByTimeNearMinecart
import me.zaksen.deathLabyrinth.game.room.logic.tick.TickProcess

@Serializable
data class RoomConfig(
    @SerialName("room_size")
    var roomSize: Position = Position(32.0, 32.0, 32.0),

    @SerialName("entrance_offset")
    var entranceOffset: Position = Position(0.0, -1.0, -12.0),

    @SerialName("exit_offset")
    var exitOffset: Position = Position(32.0, 1.0, 12.0),

    @SerialName("room_type")
    var roomType: RoomType = RoomType.NORMAL,

    @SerialName("location_type")
    var locationType: LocationType = LocationType.SHAFT,

    @SerialName("pot_spawns")
    var potSpawns: MutableList<Position> = mutableListOf(
        Position(16.0, 3.0, 16.0)
    ),

    @SerialName("start_processes")
    var startProcesses: MutableList<StartProcess> = mutableListOf(
        SpawnEntitiesProcess()
    ),

    @SerialName("completion_condition")
    var completionConditions: MutableList<CompletionCheck> = mutableListOf(
        EntityCompletionCheck()
    ),

    @SerialName("tick_processes")
    var tickProcesses: MutableList<TickProcess> = mutableListOf(

    ),

    @SerialName("tags")
    var tags: MutableList<RoomTag> = mutableListOf(
        EntitiesPool()
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

    inline fun <reified T> getTickProcess(): T? {
        tickProcesses.forEach {
            if(it is T) {
                return it
            }
        }

        return null
    }

    inline fun <reified T> getStartProcess(): T? {
        startProcesses.forEach {
            if(it is T) {
                return it
            }
        }

        return null
    }

    inline fun <reified T> getCompletionCondition(): T? {
        completionConditions.forEach {
            if(it is T) {
                return it
            }
        }

        return null
    }
}
