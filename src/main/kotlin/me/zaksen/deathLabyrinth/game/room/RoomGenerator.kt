package me.zaksen.deathLabyrinth.game.room

import me.zaksen.deathLabyrinth.exception.room.RoomGenerationException
import net.minecraft.core.Direction
import org.bukkit.World
import org.joml.Vector2i
import kotlin.random.Random

object RoomGenerator {

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
    private var random: Random = Random

    private var world: World? = null
    private var toGenerate: Int = 0
    private var roomPool: MutableSet<RoomEntry> = mutableSetOf()

    private var spawnX: Int = 0
    private var spawnZ: Int = 0

    private var roomAreas: MutableSet<Pair<Vector2i, Vector2i>> = mutableSetOf()
    private var preparedRooms: MutableSet<Room> = mutableSetOf()
    private var generateQuery: MutableMap<Room, Direction> = mutableMapOf()

    private var maxSizeX: Int = 0
    private var maxSizeZ: Int = 0


    fun setupGenerator(world: World, spawnX: Int, spawnZ: Int, roomCount: Int, floor: Int) {
        val location = LocationType.getLocationFor(floor) ?: return

        roomAreas.clear()
        preparedRooms.clear()
        generateQuery.clear()

        roomPool.clear()
        roomPool.addAll(RoomController.loadRoomsFor(location))

        this.world = world
        this.toGenerate = roomCount

        this.spawnX = spawnX
        this.spawnZ = spawnZ

        this.maxSizeX = 0
        this.maxSizeZ = 0

        roomPool.forEach {
            if(it.roomConfig.roomSize.x > maxSizeX) {
                maxSizeX = it.roomConfig.roomSize.x.toInt()
            }

            if(it.roomConfig.roomSize.z > maxSizeZ) {
                maxSizeZ = it.roomConfig.roomSize.z.toInt()
            }
        }
    }

    fun startGeneration() {
        val startRoom = getFromPool(setOf(Direction.EAST))
        val room = prepareRoom(spawnX, world!!.maxHeight / 2, spawnZ, startRoom)

        if(room != null) {
            addRoom(room)
            generateSubRooms(room)

            generateFromQuery()
        }
    }

    fun processGeneration() {
        preparedRooms.forEach {
            RoomBuilder.buildRoom(it)
            RoomController.addProcessingRoom(it)
        }

        preparedRooms.clear()

        RoomController.genareParticles = true
    }

    private fun addRoom(room: Room) {
        preparedRooms.add(room)
        roomAreas.add(Pair(
            Vector2i(room.roomX, room.roomZ),
            Vector2i(
                room.roomX + room.roomConfig.roomSize.x.toInt(),
                room.roomZ + room.roomConfig.roomSize.z.toInt()
            )
        ))
    }

    private fun prepareRoom(x: Int, y: Int, z: Int, room: RoomEntry): Room? {
        if(world == null) {
            return null
        }

        val roomOffsetX = (maxSizeX - room.roomConfig.roomSize.x).toInt() / 2
        val roomOffsetZ = (maxSizeZ - room.roomConfig.roomSize.z).toInt() / 2

        var actualX = x + roomOffsetX
        var actualZ = z + roomOffsetZ

        val prepared = RoomBuilder.prepareRoom(room, world!!, actualX, y, actualZ, random.nextInt(0, room.roomConfig.potSpawns.size))
        return prepared
    }

    private fun randomCompletionDirectionsFor(direction: Direction): Set<Direction> {
        val opposite = direction.opposite
        return directions.filter { it.contains(opposite) }.random()
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

    private fun prepareCordsForDirection(x: Int, z: Int, direction: Direction, room: RoomEntry): Pair<Int, Int> {
        val prepared = when(direction) {
            Direction.DOWN -> Pair(x, z)
            Direction.UP ->  Pair(x, z)
            Direction.NORTH ->  Pair(x, z - room.roomConfig.roomSize.z.toInt())
            Direction.SOUTH ->  Pair(x, z + room.roomConfig.roomSize.z.toInt())
            Direction.WEST ->  Pair(x - room.roomConfig.roomSize.x.toInt(), z)
            Direction.EAST ->  Pair(x + room.roomConfig.roomSize.x.toInt(), z)
        }
        return prepared
    }

    private fun generateFromQuery() {
        if(generateQuery.isNotEmpty()) {
            val toGenerateQuery = generateQuery.toMap()

            generateQuery.clear()

            if (toGenerate > 0) {

                toGenerateQuery.forEach {
                    addRoom(it.key)
                }

                toGenerateQuery.forEach {
                    generateSubRooms(it.key, it.value)
                }

                generateFromQuery()
            }
            else {
                toGenerateQuery.forEach {
                    val replace = getFromPool(setOf(it.value))
                    val replacedRoom = prepareRoom(it.key.roomX, it.key.roomY, it.key.roomZ, replace)

                    if(replacedRoom != null) {
                        addRoom(replacedRoom)
                    }
                }

                checkFullGrid()
            }
        }
    }

    // TODO - Connect near rooms with each other
    private fun checkFullGrid() {

    }

    private fun generateSubRooms(room: Room, excludeDirection: Direction? = null): MutableSet<Room> {
        val toGenerate = room.roomConfig.entranceDirections.toMutableSet()
        toGenerate.remove(excludeDirection)

        val result = mutableSetOf<Room>()

        for(direction in toGenerate) {
            val newRoom = generateSubRoom(room, direction)

            if(newRoom != null) {
                result.add(newRoom)
            }
        }

        return result
    }

    private fun generateSubRoom(room: Room, from: Direction): Room? {
        val newRoomDirections = randomCompletionDirectionsFor(from).toMutableSet()
        val newRoom = getFromPool(newRoomDirections)
        val cords = prepareCordsForDirection(room.roomX, room.roomZ, from, newRoom)

        val checkedDirections = mutableSetOf<Direction>()

        newRoomDirections.remove(from.opposite)

        for(newRoomDirection in newRoomDirections) {
            if(checkDirectionOverlapsRoom(cords.first, cords.second, newRoomDirection, newRoom)) {
                checkedDirections.add(newRoomDirection)
            }
        }

        if(checkedDirections.isEmpty()) {
            val allDirections = mutableSetOf(Direction.EAST, Direction.WEST, Direction.SOUTH, Direction.NORTH)
            allDirections.removeAll(newRoomDirections)
            checkedDirections.addAll(allDirections)
        }

        checkedDirections.add(from.opposite)

        println("New room at (${room.roomX}, ${room.roomZ}) with directions: $checkedDirections")

        val checkedRoom = getFromPool(checkedDirections)
        val checkedRoomCords = prepareCordsForDirection(room.roomX, room.roomZ, from, checkedRoom)
        val newPreparedRoom = prepareRoom(checkedRoomCords.first, room.roomY, checkedRoomCords.second, checkedRoom)

        if(newPreparedRoom != null) {
            this.toGenerate--
            generateQuery[newPreparedRoom] = from.opposite
            return newPreparedRoom
        }

        return null
    }

    private fun checkDirectionOverlapsRoom(x: Int, z: Int, direction: Direction, room: RoomEntry): Boolean {
        val directionCords = prepareCordsForDirection(x, z, direction, room)

        return !checkOverlaps(
            directionCords.first,
            directionCords.second,
            directionCords.first + room.roomConfig.roomSize.x.toInt(),
            directionCords.second + room.roomConfig.roomSize.z.toInt()
        )
    }

    private fun checkOverlaps(minX: Int, minZ: Int, maxX: Int, maxZ: Int): Boolean {
        roomAreas.forEach {
            if(
                minX >= it.first.x && minX <= it.second.x && minZ >= it.first.y && minZ <= it.second.y ||
                maxX >= it.first.x && maxX <= it.second.x && maxZ >= it.first.y && maxZ <= it.second.y
            ) {
                return true
            }
        }

        return false
    }
}