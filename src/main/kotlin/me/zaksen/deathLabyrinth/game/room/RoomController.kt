package me.zaksen.deathLabyrinth.game.room

import me.zaksen.deathLabyrinth.config.RoomConfig
import me.zaksen.deathLabyrinth.config.loadConfig
import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.exception.room.RoomLoadingException
import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.util.loadDirectoryFiles
import net.minecraft.world.entity.Entity
import org.bukkit.entity.Player
import java.io.File
import kotlin.concurrent.timer

object RoomController {

    val roomIds: MutableMap<String, String> = mutableMapOf()
    private val processingRooms: MutableSet<Room> = mutableSetOf()
    var roomsOrder: MutableList<Room> = mutableListOf()

    private var lastCompletedRoom = 0

    private val roomStartingTask = timer(period = 1000) {
        GameController.players.forEach { player ->
            val room = getPlayerProcessingRoom(player.key)
            GameController.runTask {
                room?.startRoomCompletion()
            }
        }
    }

    fun getRoom(index: Int): Room {
        return roomsOrder[index]
    }

    fun hasRoom(index: Int): Boolean {
        return roomsOrder.getOrNull(index) != null
    }

    fun reload(directory: File) {
        roomIds.clear()

        directory.loadDirectoryFiles().filter {
            it.name.endsWith(".yml")
        }.forEach {
            roomIds[it.name.replace(".yml", "")] = it.absolutePath
        }
    }

    fun clear() {
        processingRooms.forEach {
            it.clear()
        }
        processingRooms.clear()
    }

    fun tryLoadRoom(id: String): Pair<File?, File?> {
        val room = roomIds[id] ?: return Pair(null, null)

        val configFile = File(room)
        val schemFile = File(room.replace(".yml", ".schem"))

        return Pair(configFile, schemFile)
    }

    fun loadRoom(id: String): RoomEntry {
        val roomFiles = tryLoadRoom(id)

        val configFile = roomFiles.first
        val schemFile = roomFiles.second

        if(configFile == null || schemFile == null) {
            throw RoomLoadingException("Unable to load room ($id) files!")
        }

        return RoomEntry(loadConfig<RoomConfig>(configFile), schemFile)
    }

    fun addProcessingRoom(room: Room) {
        processingRooms.add(room)
    }

    fun removeProcessingRoom(room: Room) {
        processingRooms.remove(room)
    }

    fun getPlayerProcessingRoom(player: Player): Room? {
        val rooms = processingRooms.filter {
            player.x >= it.roomX && player.x <= it.roomX + it.roomConfig.roomSize.x &&
            player.y >= it.roomY && player.y <= it.roomY + it.roomConfig.roomSize.y &&
            player.z >= it.roomZ && player.z <= it.roomZ + it.roomConfig.roomSize.z
        }

        if(rooms.isNotEmpty()) {
            return rooms.first()
        }

        return null
    }

    fun processRooms() {
        for(playerEntry in GameController.players.filter { it.value.isAlive }) {
            val room = getPlayerProcessingRoom(playerEntry.key) ?: continue

            if(room.isStarted && !room.isCompleted && room.checkRoomCompletion()) {
                room.completeRoom()
            }

            room.processRoomTick()
        }
    }

    fun processRoomsDeath(entity: Entity) {
        processingRooms.forEach {
            it.processRoomEntityDeath(entity)
        }
    }

    fun loadRoomsFor(location: LocationType): MutableSet<RoomEntry> {
        val result: MutableSet<RoomEntry> = mutableSetOf()

        roomIds.forEach {
            val loaded = loadRoom(it.key)

            if(loaded.roomConfig.locationType == location) {
                result.add(loaded)
            }
        }

        return result
    }

    fun processRoomCompletion(players: List<Player>, room: Room) {
        players.forEach { EventManager.callPlayerRoomCompleteEvent(it, room, room.roomConfig.roomType.reward.generate()) }

        lastCompletedRoom++

        if(hasRoom(lastCompletedRoom)) {
            val lastRoom = getRoom(lastCompletedRoom)
            room.world.worldBorder.size += (lastRoom.roomConfig.roomSize.x * 2)
        }
    }
}