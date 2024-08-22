package me.zaksen.deathLabyrinth.game.room

import com.sk89q.worldedit.WorldEdit
import com.sk89q.worldedit.bukkit.BukkitWorld
import com.sk89q.worldedit.extent.clipboard.Clipboard
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats
import com.sk89q.worldedit.function.operation.Operation
import com.sk89q.worldedit.function.operation.Operations
import com.sk89q.worldedit.math.BlockVector3
import com.sk89q.worldedit.session.ClipboardHolder
import com.sk89q.worldedit.world.AbstractWorld
import me.zaksen.deathLabyrinth.config.*
import me.zaksen.deathLabyrinth.entity.EntityController
import me.zaksen.deathLabyrinth.util.tryAddEntity
import net.minecraft.world.entity.Entity
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.craftbukkit.entity.CraftEntity
import org.bukkit.event.entity.EntityDeathEvent
import java.io.File
import java.io.FileInputStream
import kotlin.random.Random

object RoomController {

    private lateinit var configs: ConfigContainer
    private lateinit var world: AbstractWorld

    val rooms: MutableMap<String, Room> = mutableMapOf()

    private val generationQuery: MutableList<Room> = mutableListOf()
    private var actualQueryRoom: Room? = null

    private val actualRoomEntities: MutableList<Entity> = mutableListOf()

    private var roomGenerated: Int = 0

    fun setup(configs: ConfigContainer) {
        this.configs = configs
        val loadedWorld = Bukkit.getWorld(configs.generationConfig().roomSpawningPos.world)

        if(loadedWorld != null) {
            this.world = BukkitWorld(loadedWorld)
        } else {
            println("Unable to init room controller - world is null")
        }
    }

    fun reloadRooms(directory: File) {
        rooms.clear()

        if(!directory.exists()) {
            directory.mkdirs()
        }

        val files = directory.list() ?: return

        for(fileName in files) {
            if(fileName.endsWith(".yml")) {
                val config = loadConfig<RoomConfig>(directory, fileName)
                val id = fileName.replace(".yml", "")
                val schemFileName = fileName.replace(".yml", ".schem")
                val schemFile = File(directory, schemFileName)

                if(schemFile.exists()) {
                    rooms[id] = Room(config, schemFile)
                }
            } else if(fileName.endsWith(".schem")) {
                val configFile = fileName.replace(".schem", ".yml")
                if(!File(directory, configFile).exists()) {
                    saveConfig(directory, configFile, RoomConfig())
                    println("config for room $fileName was generated! please reload plugin using /reload_game!")
                }
            }
        }
    }

    fun getRandomRoom(type: RoomType? = null): Room {
        val availableRooms = rooms.values

        if(type == null) {
            return availableRooms.random()
        } else {
            availableRooms.removeIf {
                it.roomConfig.roomType != type
            }

            return availableRooms.random()
        }
    }

    fun processEntityRoomDeath(event: EntityDeathEvent) {
        val entity = (event.entity as CraftEntity).handle

        if(actualRoomEntities.contains(entity)) {
            actualRoomEntities.remove(entity)
            checkRoomCompletion()
        }
    }

    private fun checkRoomCompletion() {
        if(actualRoomEntities.isEmpty()) {
            // TODO - Open door
            generateAndUpdateQueryRoom()
            println("room completed")
        }
    }

    fun startGeneration() {
        for(i in 0..<configs.generationConfig().roomLimit) {
            generationQuery.add(getRandomRoom(RoomType.NORMAL))
        }

        generateAndUpdateQueryRoom()
    }

    private fun generateAndUpdateQueryRoom() {
        actualRoomEntities.clear()

        if(generationQuery.isEmpty()) {
            println("Generation query end")
            return
        }

        val queryRoom: Room = generationQuery.first()
        generationQuery.remove(queryRoom)
        actualQueryRoom = queryRoom

        val spawnX = configs.generationConfig().roomSpawningPos.x - (configs.generationConfig().roomSize * roomGenerated)

        buildRoom(
            queryRoom,
            spawnX.toInt(),
            configs.generationConfig().roomSpawningPos.y.toInt(),
            configs.generationConfig().roomSpawningPos.z.toInt()
        )

        roomGenerated++
    }

    fun clearGeneration() {
        generationQuery.clear()
        actualQueryRoom = null
        actualRoomEntities.clear()

        val roomWorld = Bukkit.getWorld(configs.generationConfig().roomSpawningPos.world) ?: return

        val spawnX = configs.generationConfig().roomSpawningPos.x + 1
        val spawnZ = configs.generationConfig().roomSpawningPos.z + 1

        for(i in 0..<configs.generationConfig().roomLimit) {
            val roomX = spawnX - i * configs.generationConfig().roomSize

            for(y in -64..320) {
                for(x in 1..32) {
                    for(z in 1..32) {
                        roomWorld.setType(roomX.toInt() - x, y, spawnZ.toInt() - z, Material.AIR)
                    }
                }
            }

        }
    }

    fun clearCachedData() {

    }

    // TODO - при построении комнаты учитывать spawn_entry_offset и spawn_exit_offset
    fun buildRoom(room: Room, x: Int, y: Int, z: Int, debug: Boolean = false) {
        val roomWorld = Bukkit.getWorld(configs.generationConfig().roomSpawningPos.world) ?: return
        var clipboard: Clipboard

        val offsetX = x + room.roomConfig.spawnOffset.x
        val offsetY = y + room.roomConfig.spawnOffset.y
        val offsetZ = z + room.roomConfig.spawnOffset.z

        val format = ClipboardFormats.findByFile(room.schematic)
        format!!.getReader(FileInputStream(room.schematic)).use { reader ->
            clipboard = reader.read()
        }

        WorldEdit.getInstance().newEditSession(world).use { editSession ->
            val operation: Operation = ClipboardHolder(clipboard)
                .createPaste(editSession)
                .to(BlockVector3.at(offsetX, offsetY, offsetZ))
                .build()
            Operations.complete(operation)
        }

        for(entityEntry in room.roomConfig.roomEntities.random()) {
            val entity = EntityController.entities[entityEntry.entityName]

            if(entity != null) {
                val toSpawn = entity.getDeclaredConstructor(Location::class.java).newInstance(Location(roomWorld,
                    offsetX - entityEntry.spawnPosition.x,
                    offsetY + entityEntry.spawnPosition.y,
                    offsetZ - entityEntry.spawnPosition.z
                ))
                roomWorld.tryAddEntity(toSpawn)
                if(!debug) {
                    if(entityEntry.requireKill) {
                        actualRoomEntities.add(toSpawn)
                    }
                }
            } else {
                println("Unable to found entity with id ${entityEntry.entityName}")
            }
        }

        val potSpawns = room.roomConfig.potSpawns.toMutableList()
        val numOfPots = Random.Default.nextInt(0, 3)

        if(potSpawns.size < numOfPots) {
            return
        }

        if(numOfPots > 0) {
            for (i in 1..numOfPots) {
                val pot = potSpawns.random()
                potSpawns.remove(pot)
                roomWorld.setType(offsetX.toInt() - pot.x.toInt(), offsetY.toInt() + pot.y.toInt(), offsetZ.toInt() - pot.z.toInt(), Material.DECORATED_POT)
            }
        }
    }
}