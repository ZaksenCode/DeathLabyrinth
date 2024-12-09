package me.zaksen.deathLabyrinth.game.room

import me.zaksen.deathLabyrinth.artifacts.ArtifactsController
import me.zaksen.deathLabyrinth.exception.room.RoomGenerationException
import org.bukkit.World
import org.bukkit.entity.Item
import kotlin.random.Random

object RoomGenerator {

    private var random: Random = Random

    private var world: World? = null
    private var toGenerate: Int = 0
    private var roomPool: MutableSet<RoomEntry> = mutableSetOf()
    private var roomTypes: MutableSet<RoomType> = mutableSetOf()

    private var spawnX: Int = 0
    private var spawnY: Int = 0
    private var spawnZ: Int = 0

    private var startRoom: Room? = null
    private var requiredRooms: MutableList<RoomType> = mutableListOf()
    private var preparedRooms: MutableList<Room> = mutableListOf()

    fun startSubFloorGeneration(world: World, x: Int, z: Int, roomCount: Int, location: LocationType, seed: Long, requiredRooms: MutableList<RoomType>, roomTypes: Set<RoomType>) {
        ArtifactsController.despawnArtifacts()

        world.getEntitiesByClass(Item::class.java).forEach {
            it.remove()
        }

        RoomController.roomsOrder.clear()
        RoomController.lastCompletedRoom = 0

        setupGenerator(world, x, z, roomCount, location, seed, requiredRooms, roomTypes)
        startGeneration()
        processGeneration()
    }

    fun getPlayersStartRoom(): Room {
        return startRoom ?: throw RoomGenerationException("Start room didn't exists")
    }

    private fun setupGenerator(world: World, spawnX: Int, spawnZ: Int, roomCount: Int, location: LocationType, seed: Long, requiredRooms: MutableList<RoomType>, roomTypes: Set<RoomType>) {
        random = Random(seed)

        preparedRooms.clear()

        roomPool.clear()
        roomPool.addAll(RoomController.loadRoomsFor(location))

        this.world = world
        this.toGenerate = roomCount

        this.spawnX = spawnX
        this.spawnY = world.maxHeight / 2
        this.spawnZ = spawnZ

        println("Old require rooms: ${this.requiredRooms}")

        this.requiredRooms.clear()
        this.requiredRooms.addAll(requiredRooms)

        println("New require rooms: ${this.requiredRooms}")

        this.roomTypes.clear()
        this.roomTypes.addAll(roomTypes)
    }

    private fun startGeneration() {
        val startRoomEntry = getStartRoom()
        startRoom = addRoom(spawnX, spawnY, spawnZ, startRoomEntry)

        startRoom!!.isStarted = true
        startRoom!!.completeRoom()

        generateNextRoom()
    }

    private fun processGeneration() {
        preparedRooms.forEach {
            RoomBuilder.buildRoom(it)
            RoomController.addProcessingRoom(it)
        }

        preparedRooms.clear()
    }

    private fun generateNextRoom() {
        if(toGenerate > 0) {
            val lastRoom = preparedRooms.last()
            val nextRoom = getRoom(roomTypes)

            addRoom(
                lastRoom.roomX + lastRoom.roomConfig.exitOffset.x.toInt() + nextRoom.roomConfig.entranceOffset.x.toInt(),
                lastRoom.roomY + lastRoom.roomConfig.exitOffset.y.toInt() + nextRoom.roomConfig.entranceOffset.y.toInt(),
                lastRoom.roomZ + lastRoom.roomConfig.exitOffset.z.toInt() + nextRoom.roomConfig.entranceOffset.z.toInt(),
                nextRoom
            )

            this.toGenerate--
            generateNextRoom()
        } else if(requiredRooms.isNotEmpty()) {
            val lastRoom = preparedRooms.last()
            val nextRoom = getRoom(setOf(requiredRooms.removeFirst()))

            addRoom(
                lastRoom.roomX + lastRoom.roomConfig.exitOffset.x.toInt() + nextRoom.roomConfig.entranceOffset.x.toInt(),
                lastRoom.roomY + lastRoom.roomConfig.exitOffset.y.toInt() + nextRoom.roomConfig.entranceOffset.y.toInt(),
                lastRoom.roomZ + lastRoom.roomConfig.exitOffset.z.toInt() + nextRoom.roomConfig.entranceOffset.z.toInt(),
                nextRoom
            )

            generateNextRoom()
        } else {
            val lastRoom = preparedRooms.last()
            val nextRoom = getEndRoom()

            addRoom(
                lastRoom.roomX + lastRoom.roomConfig.exitOffset.x.toInt() + nextRoom.roomConfig.entranceOffset.x.toInt(),
                lastRoom.roomY + lastRoom.roomConfig.exitOffset.y.toInt() + nextRoom.roomConfig.entranceOffset.y.toInt(),
                lastRoom.roomZ + lastRoom.roomConfig.exitOffset.z.toInt() + nextRoom.roomConfig.entranceOffset.z.toInt(),
                nextRoom
            )
        }
    }

    private fun getStartRoom(): RoomEntry {
        val startRoom = roomPool.filter { it.roomConfig.roomType == RoomType.START_ROOM }.randomOrNull()

        if(startRoom == null) {
            throw RoomGenerationException("Unable to get start room")
        }

        return startRoom
    }

    private fun getEndRoom(): RoomEntry {
        val startRoom = roomPool.filter { it.roomConfig.roomType == RoomType.END_ROOM }.randomOrNull()

        if(startRoom == null) {
            throw RoomGenerationException("Unable to get end room")
        }

        return startRoom
    }

    private fun getRoom(types: Set<RoomType>): RoomEntry {
        val room = roomPool.filter { types.contains(it.roomConfig.roomType) }.randomOrNull()

        if(room == null) {
            throw RoomGenerationException("Unable to get room for types: $types")
        }

        return room
    }

    private fun addRoom(x: Int, y: Int, z: Int, room: RoomEntry): Room {
        val potsNum = if(room.roomConfig.potSpawns.isEmpty()) 0 else random.nextInt(0, room.roomConfig.potSpawns.size)
        val newRoom = RoomBuilder.prepareRoom(room, world!!, x, y, z, potsNum)
        preparedRooms.add(newRoom)
        RoomController.roomsOrder.add(newRoom)
        return newRoom
    }
}