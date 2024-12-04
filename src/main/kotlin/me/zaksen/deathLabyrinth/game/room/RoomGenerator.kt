package me.zaksen.deathLabyrinth.game.room

import me.zaksen.deathLabyrinth.DeathLabyrinth
import me.zaksen.deathLabyrinth.exception.room.RoomGenerationException
import me.zaksen.deathLabyrinth.game.GameController
import net.minecraft.core.Direction
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.util.Vector
import kotlin.concurrent.thread
import kotlin.random.Random

// TODO - Add floor generation
object RoomGenerator {

    // TODO - Add rooms for all directions (IN ROOMS FOLDER)
    private val directions: Set<Set<Direction>> = setOf(
        setOf(Direction.EAST, Direction.NORTH),
        setOf(Direction.EAST, Direction.WEST),
        setOf(Direction.EAST, Direction.SOUTH),

        setOf(Direction.NORTH, Direction.WEST),
        setOf(Direction.NORTH, Direction.SOUTH),

        setOf(Direction.WEST, Direction.SOUTH),

        setOf(Direction.EAST, Direction.NORTH, Direction.WEST),
        setOf(Direction.EAST, Direction.SOUTH, Direction.NORTH),
        setOf(Direction.EAST, Direction.SOUTH, Direction.WEST),

        setOf(Direction.NORTH, Direction.WEST, Direction.SOUTH),
    )

    // TODO - Change random from default
    private var random: Random = Random.Default

    private var world: World? = null
    private var toGenerate: Int = 0
    private var roomPool: MutableSet<RoomEntry> = mutableSetOf()
    private var centerX: Int = 0
    private var centerZ: Int = 0

    private var preparedRooms: MutableList<Room> = mutableListOf()
    private var reservedPositions: MutableList<Pair<Vector, Vector>> = mutableListOf()
    private var nextStepRooms: MutableMap<Room, Set<Direction>> = mutableMapOf()

    fun setupGenerator(world: World, centerX: Int, centerZ: Int, roomCount: Int, floor: Int) {
        val location = LocationType.getLocationFor(floor) ?: return

        roomPool.clear()
        roomPool.addAll(RoomController.loadRoomsFor(location))

        reservedPositions.clear()
        nextStepRooms.clear()

        this.world = world
        this.toGenerate = roomCount
        this.centerX = centerX
        this.centerZ = centerZ
    }

    fun startGeneration() {
        val startRoom = getFromPool(setOf(Direction.EAST, Direction.WEST, Direction.NORTH, Direction.SOUTH))

        val prepared = RoomBuilder.prepareRoom(
            startRoom,
            world!!,
            centerX,
            world!!.maxHeight / 2,
            centerZ,
            random.nextInt(0, startRoom.roomConfig.potSpawns.size)
        )

        preparedRooms.add(prepared)

        reservedPositions.add(Pair(
            Vector(prepared.roomX, prepared.roomY, prepared.roomZ),
            Vector(
                prepared.roomX + prepared.roomConfig.roomSize.x - 1,
                prepared.roomY + prepared.roomConfig.roomSize.y - 1,
                prepared.roomZ + prepared.roomConfig.roomSize.z - 1
            )
        ))

        nextStepRooms[prepared] = setOf()
        processNextStepRooms()
    }

    private fun roomStep(room: Room, excludeDirection: Set<Direction>) {
        val processDirections = room.roomConfig.entranceDirections.toMutableSet()
        processDirections.removeAll(excludeDirection)
        println("From (${room.schematic.name}) build rooms for directions: $processDirections")

        for(direction in processDirections) {
            if(toGenerate > 1) {
                val directions = randomCompletionDirectionsFor(direction).toMutableSet()
                println("New room with complete directions: $directions")

                if(directions.size > toGenerate) {
                    for(i in 1..(directions.size - toGenerate)) {
                        directions.remove(directions.last())
                    }
                }

                val newRoom = getFromPool(directions)
                val prepared = prepareRoomForDirection(room, newRoom, direction)
                println("New prepared room (${prepared.schematic.name}) with complete directions: ${prepared.roomConfig.entranceDirections}")

                val checkedPrepared = removeImpossibleDirections(prepared, direction, directions)

                println("New checked prepared room (${checkedPrepared.schematic.name}) with complete directions: ${checkedPrepared.roomConfig.entranceDirections}")

                this.toGenerate--

                preparedRooms.add(checkedPrepared)

                reservedPositions.add(
                    Pair(
                        Vector(checkedPrepared.roomX, checkedPrepared.roomY, checkedPrepared.roomZ),
                        Vector(
                            checkedPrepared.roomX + checkedPrepared.roomConfig.roomSize.x - 1,
                            checkedPrepared.roomY + checkedPrepared.roomConfig.roomSize.y - 1,
                            checkedPrepared.roomZ + checkedPrepared.roomConfig.roomSize.z - 1
                        )
                    )
                )

                if(checkedPrepared.roomConfig.entranceDirections.size > 1) {
                    nextStepRooms[checkedPrepared] = setOf(direction.opposite)
                }
            } else {
                val newDirection = direction.opposite

                val newRoom = getFromPool(direction)
                val prepared = prepareRoomForDirection(room, newRoom, newDirection)

                this.toGenerate--

                preparedRooms.add(prepared)
                reservedPositions.add(Pair(
                    Vector(prepared.roomX, prepared.roomY, prepared.roomZ),
                    Vector(
                        prepared.roomX + prepared.roomConfig.roomSize.x - 1,
                        prepared.roomY + prepared.roomConfig.roomSize.y - 1,
                        prepared.roomZ + prepared.roomConfig.roomSize.z - 1
                    )
                ))
            }
        }
    }

    private fun processNextStepRooms() {
        val toStep = nextStepRooms.toMap()
        nextStepRooms.clear()

        toStep.forEach {
            roomStep(it.key, it.value)
        }

        if(toStep.isNotEmpty()) {
            processNextStepRooms()
        }
    }

    private fun prepareRoomForDirection(oldRoom: Room, newRoom: RoomEntry, direction: Direction): Room {
        // TODO - Add entrance offsets
        return when(direction) {
            Direction.DOWN -> RoomBuilder.prepareRoom(
                newRoom,
                world!!,
                oldRoom.roomX,
                oldRoom.roomY - newRoom.roomConfig.roomSize.y.toInt(),
                oldRoom.roomZ,
                random.nextInt(0, newRoom.roomConfig.potSpawns.size)
            )
            Direction.UP -> RoomBuilder.prepareRoom(
                newRoom,
                world!!,
                oldRoom.roomX,
                oldRoom.roomY + oldRoom.roomConfig.roomSize.y.toInt(),
                oldRoom.roomZ,
                random.nextInt(0, newRoom.roomConfig.potSpawns.size)
            )
            Direction.NORTH -> RoomBuilder.prepareRoom(
                newRoom,
                world!!,
                oldRoom.roomX,
                oldRoom.roomY,
                oldRoom.roomZ - newRoom.roomConfig.roomSize.z.toInt(),
                random.nextInt(0, newRoom.roomConfig.potSpawns.size)
            )
            Direction.SOUTH -> RoomBuilder.prepareRoom(
                newRoom,
                world!!,
                oldRoom.roomX,
                oldRoom.roomY,
                oldRoom.roomZ + oldRoom.roomConfig.roomSize.z.toInt(),
                random.nextInt(0, newRoom.roomConfig.potSpawns.size)
            )
            Direction.WEST -> RoomBuilder.prepareRoom(
                newRoom,
                world!!,
                oldRoom.roomX - newRoom.roomConfig.roomSize.x.toInt(),
                oldRoom.roomY,
                oldRoom.roomZ,
                random.nextInt(0, newRoom.roomConfig.potSpawns.size)
            )
            Direction.EAST -> RoomBuilder.prepareRoom(
                newRoom,
                world!!,
                oldRoom.roomX + oldRoom.roomConfig.roomSize.x.toInt(),
                oldRoom.roomY,
                oldRoom.roomZ,
                random.nextInt(0, newRoom.roomConfig.potSpawns.size)
            )
        }
    }

    private fun randomCompletionDirectionsFor(direction: Direction): Set<Direction> {
        val opposite = direction.opposite
        return directions.filter { it.contains(opposite) }.random()
    }

    // TODO - add previous room cords check with direction.
    private fun removeImpossibleDirections(newRoom: Room, from: Direction, directions: MutableSet<Direction>): Room {
        val rightSize = directions.size

        directions.remove(from.opposite)

        val newDirections = directions.filter { direction -> checkDirection(newRoom, direction) }.toMutableSet()

        newDirections.add(from.opposite)

        if(newDirections.size == rightSize) {
            return newRoom
        } else {
            val newEntryRoom = getFromPool(newDirections)

            return RoomBuilder.prepareRoom(
                newEntryRoom,
                world!!,
                newRoom.roomX,
                newRoom.roomY,
                newRoom.roomZ,
                random.nextInt(0, newRoom.roomConfig.potSpawns.size)
            )
        }
    }

    // TODO - Remove hardcoded values
    private fun checkDirection(room: Room, direction: Direction): Boolean {
        val directionBuildPosition = when(direction) {
            Direction.DOWN -> Vector(room.roomX, room.roomY - 31, room.roomZ)
            Direction.UP -> Vector(room.roomX, room.roomY + 31, room.roomZ)
            Direction.NORTH -> Vector(room.roomX, room.roomY, room.roomZ - 31)
            Direction.SOUTH -> Vector(room.roomX, room.roomY, room.roomZ + 31)
            Direction.WEST -> Vector(room.roomX - 31, room.roomY, room.roomZ)
            Direction.EAST -> Vector(room.roomX + 31, room.roomY, room.roomZ)
        }

        reservedPositions.forEach { roomCorners ->
            if(directionBuildPosition.isInAABB(roomCorners.first, roomCorners.second)) {
                println(
                    "Unable to build room in ${directionBuildPosition} because into ${roomCorners.first}, ${roomCorners.second}"
                )
                return false
            }
        }

        return true
    }

    fun processGeneration() {
        var time: Long = 0

        preparedRooms.forEach {
            GameController.runTaskLater({
                RoomBuilder.buildRoom(it)
                RoomController.addProcessingRoom(it)
            }, time)
            time += 50
        }

        preparedRooms.clear()
    }

    private fun getFromPool(directions: Set<Direction>): RoomEntry {
        val toReturn = roomPool.filter {
            directions == it.roomConfig.entranceDirections
        }.randomOrNull(random)

        if(toReturn == null) {
            throw RoomGenerationException("Unable to get room for direction: $directions")
        }

        return toReturn
    }

    private fun getFromPool(direction: Direction): RoomEntry {
        val toReturn = roomPool.filter { it.roomConfig.entranceDirections.contains(direction) }.randomOrNull(random)

        if(toReturn == null) {
            throw RoomGenerationException("Unable to get room for direction: $direction")
        }

        return toReturn
    }
}