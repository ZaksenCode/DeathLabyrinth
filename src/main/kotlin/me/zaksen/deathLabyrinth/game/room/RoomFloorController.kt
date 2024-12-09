package me.zaksen.deathLabyrinth.game.room

import me.zaksen.deathLabyrinth.config.ConfigContainer
import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.game.room.exit.choice.Choice
import me.zaksen.deathLabyrinth.game.room.exit.choice.ChoiceContainer
import me.zaksen.deathLabyrinth.game.room.logic.tags.StartRoomSpawnOffset
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World

object RoomFloorController {

    private lateinit var configs: ConfigContainer
    private lateinit var world: World

    var floor: Int = 1
    var subFloor: Int = 0

    var actualLocation: LocationType = LocationType.SHAFT

    private var maxSubFloor: Int = 4
    private var nextFloorRooms: MutableSet<RoomType> = mutableSetOf(RoomType.NORMAL)

    fun setup(configs: ConfigContainer) {
        this.configs = configs
        this.world = Bukkit.getWorld(configs.mainConfig().world)!!
    }

    fun reload() {
        clearSubFloor()

        this.floor = 1
        this.subFloor = 0
        this.nextFloorRooms = mutableSetOf(RoomType.NORMAL)
    }

    fun clearSubFloor() {
        RoomController.clear()
    }

    fun startSubFloor(choice: Choice, seed: Long) {
        actualLocation = choice.location

        RoomGenerator.startSubFloorGeneration(
            world,
            configs.mainConfig().roomSpawnLocation.x.toInt(),
            configs.mainConfig().roomSpawnLocation.y.toInt(),
            choice.length,
            choice.location,
            seed,
            choice.requiredRooms,
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
        location.yaw = -90f

        GameController.players.forEach {
            it.key.teleport(location)
        }

        world.worldBorder.setCenter(location.x, location.z)
        world.worldBorder.size = startRoom.roomConfig.roomSize.x + (RoomController.getRoom(1).roomConfig.roomSize.x * 2)
    }

    fun shouldGenerateLocationChoice(): Boolean {
        return subFloor >= maxSubFloor - 1
    }


    fun shouldGenerateBossSubFloor(): Boolean {
        return subFloor >= maxSubFloor - 2
    }

    fun completeSubFloor(newFloor: Choice, seed: Long) {
        subFloor++

        RoomController.clear()

        if(subFloor >= maxSubFloor) {
            floor++
            subFloor = 0
        }

        startSubFloor(newFloor, seed)
    }

    fun countReward(base: Int): Int {
        var baseWithFloor: Int = base * floor
        baseWithFloor += (baseWithFloor * (0.3 * subFloor)).toInt()
        return baseWithFloor
    }

    fun countPrice(base: Int): Int {
        var baseWithFloor: Int = base * floor
        baseWithFloor += (baseWithFloor * (0.1 * subFloor)).toInt()
        return baseWithFloor
    }
}