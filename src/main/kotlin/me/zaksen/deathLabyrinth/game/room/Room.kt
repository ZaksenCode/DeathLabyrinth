package me.zaksen.deathLabyrinth.game.room

import com.sk89q.worldedit.WorldEdit
import com.sk89q.worldedit.bukkit.BukkitWorld
import com.sk89q.worldedit.extent.clipboard.Clipboard
import com.sk89q.worldedit.function.operation.Operation
import com.sk89q.worldedit.function.operation.Operations
import com.sk89q.worldedit.math.BlockVector3
import com.sk89q.worldedit.regions.CuboidRegion
import com.sk89q.worldedit.regions.Region
import com.sk89q.worldedit.regions.Regions
import com.sk89q.worldedit.session.ClipboardHolder
import com.sk89q.worldedit.world.block.BlockStateHolder
import com.sk89q.worldedit.world.block.BlockTypes
import me.zaksen.deathLabyrinth.config.RoomConfig
import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.game.GameController
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.craftbukkit.CraftWorld
import org.bukkit.craftbukkit.block.CraftBlockState
import java.io.File

class Room(
    val roomConfig: RoomConfig,
    val schematic: File,
    val world: World,
    val roomX: Int,
    val roomY: Int,
    val roomZ: Int,
    val numOfPots: Int
) {
    // Data
    val livingEntities: MutableSet<LivingEntity> = mutableSetOf()
    val otherEntities: MutableSet<Entity> = mutableSetOf()

    var isStarted = false
    var isCompleted = false

    fun clear() {
        livingEntities.forEach {
            it.discard()
        }
        otherEntities.forEach {
            it.discard()
        }

        livingEntities.clear()
        otherEntities.clear()

        WorldEdit.getInstance().newEditSession(BukkitWorld(world)).use { editSession ->
            val region = CuboidRegion(
                BlockVector3(roomX, roomY, roomZ),
                BlockVector3(
                    roomX + roomConfig.roomSize.x.toInt(),
                    roomY + roomConfig.roomSize.y.toInt(),
                    roomZ + roomConfig.roomSize.z.toInt()
                )
            )
            editSession.setBlocks(region, BlockTypes.AIR!!.defaultState)
        }
    }

    fun startRoomCompletion() {
        if(!isCompleted && !isStarted) {
            roomConfig.startProcesses.forEach {
                it.process(this)
            }
            isStarted = true
        }
    }

    fun checkRoomCompletion(): Boolean {
        var result = true

        for(condition in roomConfig.completionConditions) {
            if(!condition.check(this)) {
                result = false
            }
        }

        return result
    }

    fun completeRoom() {
        isCompleted = true
        EventManager.callRoomCompleteEvent(GameController.players.map { it.key }, this)
    }

    fun processRoomEntityDeath(entity: Entity) {
        livingEntities.remove(entity)
    }

    fun processRoomTick() {
        roomConfig.tickProcesses.forEach {
            it.process(this)
        }
    }
}