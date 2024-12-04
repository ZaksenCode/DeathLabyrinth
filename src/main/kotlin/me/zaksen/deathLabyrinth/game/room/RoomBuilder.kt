package me.zaksen.deathLabyrinth.game.room

import com.sk89q.worldedit.WorldEdit
import com.sk89q.worldedit.bukkit.BukkitWorld
import com.sk89q.worldedit.extent.clipboard.Clipboard
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats
import com.sk89q.worldedit.function.operation.Operation
import com.sk89q.worldedit.function.operation.Operations
import com.sk89q.worldedit.math.BlockVector3
import com.sk89q.worldedit.session.ClipboardHolder
import me.zaksen.deathLabyrinth.config.ConfigContainer
import me.zaksen.deathLabyrinth.exception.room.RoomBuildingException
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.World
import java.io.FileInputStream

object RoomBuilder {

    lateinit var configs: ConfigContainer

    fun setup(configs: ConfigContainer) {
        this.configs = configs
    }

    fun buildRoom(room: Room) {
        var clipboard: Clipboard

        val format = ClipboardFormats.findByFile(room.schematic)
        format!!.getReader(FileInputStream(room.schematic)).use { reader ->
            clipboard = reader.read()
        }

        WorldEdit.getInstance().newEditSession(BukkitWorld(room.world)).use { editSession ->
            val operation: Operation = ClipboardHolder(clipboard)
                .createPaste(editSession)
                .to(BlockVector3.at(room.roomX, room.roomY, room.roomZ))
                .build()
            Operations.complete(operation)
        }

        var toSpawn = room.numOfPots
        val potSpawns = room.roomConfig.potSpawns.toMutableList()

        if(potSpawns.size < room.numOfPots) {
            toSpawn = potSpawns.size
        }

        if(room.numOfPots > 0) {
            for (i in 1..toSpawn) {
                val pot = potSpawns.random()
                potSpawns.remove(pot)
                room.world.setType(room.roomX + pot.x.toInt(), room.roomY + pot.y.toInt(), room.roomZ + pot.z.toInt(), Material.DECORATED_POT)
            }
        }
    }

    fun prepareRoom(roomEntry: RoomEntry, world: World, x: Int, y: Int, z: Int, numOfPots: Int): Room {
        return Room(roomEntry.roomConfig, roomEntry.schematic, world, x, y, z, numOfPots)
    }

}