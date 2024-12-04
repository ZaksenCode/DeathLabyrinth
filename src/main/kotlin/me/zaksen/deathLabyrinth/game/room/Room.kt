package me.zaksen.deathLabyrinth.game.room
import me.zaksen.deathLabyrinth.config.RoomConfig
import me.zaksen.deathLabyrinth.entity.EntityController
import me.zaksen.deathLabyrinth.event.EventManager
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import org.bukkit.Location
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
            isStarted = true
            spawnEntities()
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

    // TODO - Add remove completion logic (in generation)
    fun completeRoom() {
        isCompleted = true
    }

    fun processRoomEntityDeath(entity: Entity) {
        livingEntities.remove(entity)
    }

    fun processRoomTick() {
        roomConfig.tickProcesses.forEach {
            it.process(this)
        }
    }

    private fun spawnEntities() {
        for(entityEntry in roomConfig.roomEntities.random()) {
            val entity = EntityController.entities[entityEntry.entityName]

            if(entity != null) {
                val toSpawn = entity.getDeclaredConstructor(Location::class.java).newInstance(Location(
                    world,
                    roomX + entityEntry.spawnPosition.x,
                    roomY + entityEntry.spawnPosition.y,
                    roomZ + entityEntry.spawnPosition.z
                ))

                EventManager.callEntitySpawnEvent(this, toSpawn, entityEntry.requireKill)
            } else {
                println("Unable to found entity with id ${entityEntry.entityName}")
            }
        }
    }
}