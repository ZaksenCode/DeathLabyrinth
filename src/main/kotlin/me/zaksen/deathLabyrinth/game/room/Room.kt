package me.zaksen.deathLabyrinth.game.room

import me.zaksen.deathLabyrinth.config.RoomConfig
import me.zaksen.deathLabyrinth.entity.EntityController
import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.game.room.logic.CompletionCheckResolver
import me.zaksen.deathLabyrinth.game.room.logic.TickProcessResolver
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World

class Room(
    val roomConfig: RoomConfig,
    val world: World,
    val roomX: Int,
    val roomY: Int,
    val roomZ: Int,
) {
    // Resolvers
    val completionCheckResolver = CompletionCheckResolver(this)
    val tickProcessResolver = TickProcessResolver(this)

    // Data
    val livingEntities: MutableSet<LivingEntity> = mutableSetOf()
    val otherEntities: MutableSet<Entity> = mutableSetOf()

    init {

    }

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
        spawnEntities()
    }

    fun checkRoomCompletion(): Boolean {
        return completionCheckResolver.doCheck()
    }

    fun completeRoom() {

    }

    fun processRoomTick() {
        tickProcessResolver.doTick()
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