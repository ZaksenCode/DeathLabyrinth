package me.zaksen.deathLabyrinth.game.room

import com.sk89q.worldedit.EditSession
import com.sk89q.worldedit.WorldEdit
import com.sk89q.worldedit.bukkit.BukkitWorld
import com.sk89q.worldedit.math.BlockVector3
import com.sk89q.worldedit.regions.CuboidRegion
import com.sk89q.worldedit.regions.Region
import com.sk89q.worldedit.world.block.BaseBlock
import com.sk89q.worldedit.world.block.BlockStateHolder
import com.sk89q.worldedit.world.block.BlockTypes
import me.zaksen.deathLabyrinth.config.RoomConfig
import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.game.GameController
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import org.bukkit.World
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
                BlockVector3.at(roomX, roomY, roomZ),
                BlockVector3.at(
                    roomX + roomConfig.roomSize.x,
                    roomY + roomConfig.roomSize.y,
                    roomZ + roomConfig.roomSize.z
                )
            )

            editSession.setCustomBlocks(region, BlockTypes.AIR!!.defaultState)
        }
    }

    private fun <T : BlockStateHolder<T>> EditSession.setCustomBlocks(region: Region, block: BlockStateHolder<T>) {
        setBlocks(region, block)
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