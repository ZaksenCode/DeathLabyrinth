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
import org.bukkit.Bukkit
import java.io.File
import java.io.FileInputStream

object RoomController {

    private lateinit var config: MainConfig
    private lateinit var world: AbstractWorld

    private val rooms: MutableSet<Room> = mutableSetOf()

    fun setup(config: MainConfig) {
        this.config = config
        val loadedWorld = Bukkit.getWorld(config.roomSpawnPos.world)

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
                rooms.add(Room(config, File(directory, fileName.replace(".yml", ".schem"))))
                println("$fileName was loaded!")
            }
        }
    }

    fun getRandomRoom(type: RoomType? = null): Room {
        if(type == null) {
            return rooms.random()
        } else {
            rooms.removeIf {
                it.roomConfig.roomType != type
            }

            return rooms.random()
        }
    }

    fun buildRoom(room: Room) {
        var clipboard: Clipboard

        val format = ClipboardFormats.findByFile(room.schematic)
        format!!.getReader(FileInputStream(room.schematic)).use { reader ->
            clipboard = reader.read()
        }

        WorldEdit.getInstance().newEditSession(world).use { editSession ->
            val operation: Operation = ClipboardHolder(clipboard)
                .createPaste(editSession)
                .to(BlockVector3.at(config.roomSpawnPos.x, config.roomSpawnPos.y, config.roomSpawnPos.z))
                .build()
            Operations.complete(operation)
        }
    }
}