package me.zaksen.deathLabyrinth.game.room

import me.zaksen.deathLabyrinth.config.ConfigContainer
import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.game.room.logic.tags.StartRoomSpawnOffset
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World

object RoomFloorController {

    private lateinit var configs: ConfigContainer
    private lateinit var world: World

    private var floor: Int = 1

    private var subFloor: Int = 0
    private var maxSubFloor: Int = 3

    private var nextFloorRooms: MutableSet<RoomType> = mutableSetOf(RoomType.NORMAL)

    fun setup(configs: ConfigContainer) {
        this.configs = configs
        this.world = Bukkit.getWorld(configs.mainConfig().world)!!
    }

    fun reload() {
        clearSubFloor()

        this.floor = 1
        this.nextFloorRooms = mutableSetOf(RoomType.NORMAL)
    }

    fun clearSubFloor() {
        RoomController.clear()
    }

    fun startSubFloor(roomCount: Int, seed: Long) {
        RoomGenerator.startSubFloorGeneration(
            world,
            configs.mainConfig().roomSpawnLocation.x.toInt(),
            configs.mainConfig().roomSpawnLocation.y.toInt(),
            roomCount,
            floor,
            seed,
            nextFloorRooms
        )

        val startRoom = RoomGenerator.getPlayersStartRoom()
        val offsetTag = startRoom.roomConfig.getTag<StartRoomSpawnOffset>()

        var spawnX = startRoom.roomX.toDouble()
        var spawnY = startRoom.roomY.toDouble()
        var spawnZ = startRoom.roomZ.toDouble()

        if(offsetTag != null) {
            spawnX += offsetTag.offset.x
            spawnY += offsetTag.offset.y
            spawnZ += offsetTag.offset.z
        }

        val location = Location(world, spawnX, spawnY, spawnZ)

        GameController.players.forEach {
            it.key.teleport(location)
        }

        world.worldBorder.setCenter(location.x, location.z)
        world.worldBorder.size = startRoom.roomConfig.roomSize.x + (RoomController.getRoom(1).roomConfig.roomSize.x * 2)
    }
}