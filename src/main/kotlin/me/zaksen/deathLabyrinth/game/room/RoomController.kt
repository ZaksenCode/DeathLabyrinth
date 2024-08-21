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
import me.zaksen.deathLabyrinth.config.MainConfig
import me.zaksen.deathLabyrinth.config.RoomConfig
import me.zaksen.deathLabyrinth.config.loadConfig
import me.zaksen.deathLabyrinth.config.saveConfig
import me.zaksen.deathLabyrinth.entity.CustomEntityController
import me.zaksen.deathLabyrinth.util.tryAddEntity
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import java.io.File
import java.io.FileInputStream
import kotlin.random.Random

object RoomController {

    private lateinit var config: MainConfig
    private lateinit var world: AbstractWorld

    val rooms: MutableMap<String, Room> = mutableMapOf()

    fun setup(config: MainConfig) {
        this.config = config
        val loadedWorld = Bukkit.getWorld(config.world)

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

    fun startGeneration() {

    }

    fun clearGeneration() {

    }

    fun clearCachedData() {

    }

    fun buildRoom(room: Room, x: Int, y: Int, z: Int) {
        val roomWorld = Bukkit.getWorld(config.world) ?: return

        var clipboard: Clipboard

        val format = ClipboardFormats.findByFile(room.schematic)
        format!!.getReader(FileInputStream(room.schematic)).use { reader ->
            clipboard = reader.read()
        }

        val offset = room.roomConfig.spawnOffset

        WorldEdit.getInstance().newEditSession(world).use { editSession ->
            val operation: Operation = ClipboardHolder(clipboard)
                .createPaste(editSession)
                .to(BlockVector3.at(x + offset.x, y + offset.y, z + offset.z))
                .build()
            Operations.complete(operation)
        }

        for(entityEntry in room.roomConfig.roomEntities.random()) {
            val entity = CustomEntityController.entities[entityEntry.entityName]

            if(entity != null) {
                val toSpawn = entity.getDeclaredConstructor(Location::class.java).newInstance(Location(roomWorld,
                    x - entityEntry.spawnPosition.x,
                    y + entityEntry.spawnPosition.y,
                    z - entityEntry.spawnPosition.z
                ))
                roomWorld.tryAddEntity(toSpawn)
            } else {
                println("Unable to found entity with id ${entityEntry.entityName}")
            }
        }

        val potSpawns = room.roomConfig.potSpawns.toMutableList()
        val numOfPots = Random.Default.nextInt(0, 3)

        if(numOfPots > 0) {
            for (i in 1..numOfPots) {
                val pot = potSpawns.random()
                potSpawns.remove(pot)
                roomWorld.setType(x - pot.x.toInt(), y + pot.y.toInt(), z - pot.z.toInt(), Material.DECORATED_POT)
            }
        }
    }
}