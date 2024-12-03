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
import java.io.FileInputStream

object RoomBuilder {

    lateinit var configs: ConfigContainer

    fun setup(configs: ConfigContainer) {
        this.configs = configs
    }

    fun buildRoom(roomEntry: RoomEntry, x: Int, y: Int, z: Int, numOfPots: Int): Room {
        val roomWorld = Bukkit.getWorld(configs.mainConfig().world) ?: throw RoomBuildingException("Unable to build room -> can't load world!")
        var clipboard: Clipboard

        val format = ClipboardFormats.findByFile(roomEntry.schematic)
        format!!.getReader(FileInputStream(roomEntry.schematic)).use { reader ->
            clipboard = reader.read()
        }

        WorldEdit.getInstance().newEditSession(BukkitWorld(roomWorld)).use { editSession ->
            val operation: Operation = ClipboardHolder(clipboard)
                .createPaste(editSession)
                .to(BlockVector3.at(x, y, z))
                .build()
            Operations.complete(operation)
        }

        var toSpawn = numOfPots
        val potSpawns = roomEntry.roomConfig.potSpawns.toMutableList()

        if(potSpawns.size < numOfPots) {
            toSpawn = potSpawns.size
        }

        if(numOfPots > 0) {
            for (i in 1..toSpawn) {
                val pot = potSpawns.random()
                potSpawns.remove(pot)
                roomWorld.setType(x - pot.x.toInt(), y + pot.y.toInt(), z - pot.z.toInt(), Material.DECORATED_POT)
            }
        }

        return Room(roomEntry.roomConfig, roomWorld, x, y, z)
    }
}