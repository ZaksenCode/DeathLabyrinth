package me.zaksen.deathLabyrinth.game.room

import me.zaksen.deathLabyrinth.config.RoomConfig
import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.game.GameController
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import org.bukkit.Material
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

        for(y in roomY..(roomY + roomConfig.roomSize.y.toInt())) {
            for (x in roomX..(roomX + roomConfig.roomSize.x.toInt())) {
                for (z in roomZ..(roomZ + roomConfig.roomSize.z.toInt())) {
                    world.setType(x, y, z, Material.AIR)
                }
            }
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