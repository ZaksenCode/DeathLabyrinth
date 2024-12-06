package me.zaksen.deathLabyrinth.game.room.logic.start

import kotlinx.serialization.Serializable
import me.zaksen.deathLabyrinth.entity.EntityController
import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.game.room.Room
import me.zaksen.deathLabyrinth.game.room.logic.tags.EntitiesPool
import org.bukkit.Location

@Serializable
class SpawnEntitiesProcess: StartProcess {
    override fun process(room: Room) {
        val entitiesPool = room.roomConfig.getTag<EntitiesPool>() ?: return

        for(entityEntry in entitiesPool.roomEntities.random()) {
            val entity = EntityController.entities[entityEntry.entityName]

            if(entity != null) {
                val toSpawn = entity.getDeclaredConstructor(Location::class.java).newInstance(
                    Location(
                    room.world,
                    room.roomX + entityEntry.spawnPosition.x,
                    room.roomY + entityEntry.spawnPosition.y,
                    room.roomZ + entityEntry.spawnPosition.z
                ))

                EventManager.callEntitySpawnEvent(room, toSpawn, entityEntry.requireKill)
            } else {
                println("Unable to found entity with id ${entityEntry.entityName}")
            }
        }
    }
}