package me.zaksen.deathLabyrinth.game.room.logic.start

import kotlinx.serialization.Serializable
import me.zaksen.deathLabyrinth.entity.EntityController
import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.game.room.Room
import me.zaksen.deathLabyrinth.game.room.logic.tags.NecromancerOffset
import org.bukkit.Location

@Serializable
class SpawnNecromancer: StartProcess {
    override fun process(room: Room) {
        if(GameController.hasDeadPlayers()) {
            val offset = room.roomConfig.getTag<NecromancerOffset>() ?: return

            val spawnX = room.roomX + offset.offset.x
            val spawnY = room.roomY + offset.offset.y
            val spawnZ = room.roomZ + offset.offset.z

            val entity = EntityController.entities["necromancer"]

            if(entity != null) {
                val toSpawn = entity.getDeclaredConstructor(Location::class.java).newInstance(Location(room.world,
                    spawnX,
                    spawnY,
                    spawnZ
                ))
                EventManager.callEntitySpawnEvent(room, toSpawn, false)
            }
        }
    }
}