package me.zaksen.deathLabyrinth.game.room.logic.tick

import kotlinx.serialization.Serializable
import me.zaksen.deathLabyrinth.entity.EntityController
import me.zaksen.deathLabyrinth.entity.minecart.FollowMinecart
import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.game.room.Room
import me.zaksen.deathLabyrinth.game.room.logic.completion.FollowMinecartExcept
import me.zaksen.deathLabyrinth.game.room.logic.tags.EntitiesPool
import org.bukkit.Color
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.Particle.DustOptions
import org.bukkit.util.Vector
import kotlin.random.Random

@Serializable
class HeightMinLimit(private val height: Int): TickProcess {
    override fun process(room: Room) {
        debugDisplay(room)

        GameController.players.forEach {
            if(it.key.y < room.roomY + height && it.key.gameMode != GameMode.SPECTATOR) {
                it.key.velocity = Vector(0.0, 1.25, 0.0)
                it.key.damage(5.0)
            }
        }

        val toCheck = room.livingEntities.toSet()
        toCheck.forEach {
            if(it.y < room.roomY + height) {
                it.kill()
            }
        }
    }

    override fun debugDisplay(room: Room) {
        val drawHeight = room.roomY + height.toDouble()

        for(x in room.roomX..room.roomX + room.roomConfig.roomSize.x.toInt() step 2) {
            for(z in room.roomZ..room.roomZ + room.roomConfig.roomSize.z.toInt() step 2) {
                room.world.spawnParticle(
                    Particle.DUST,
                    x.toDouble(),
                    drawHeight,
                    z.toDouble(),
                    1,
                    DustOptions(Color.RED, 0.35f)
                )
            }
        }
    }
}

@Serializable
open class SpawnEntitiesByTime(
    private val maxSpawnTicks: Int = 100,
    private val maxSpawnEntities: Int = 5
): TickProcess {
    private var spawnTicks = 0

    override fun process(room: Room) {
        if(room.isCompleted) return

        spawnTicks++

        if(spawnTicks >= maxSpawnTicks && room.livingEntities.size < maxSpawnEntities) {
            spawnEntity(room)
            spawnTicks = 0
        }
    }

    protected open fun spawnEntity(room: Room) {
        val entitiesPools = room.roomConfig.getTag<EntitiesPool>() ?: return
        val pool = entitiesPools.roomEntities.randomOrNull() ?: return
        val toSpawn = pool.randomOrNull() ?: return
        val newEntity = EntityController.entities[toSpawn.entityName] ?: return

        val newWorldEntity = newEntity.getDeclaredConstructor(Location::class.java).newInstance(
            Location(
                room.world,
                room.roomX + toSpawn.spawnPosition.x,
                room.roomY + toSpawn.spawnPosition.y,
                room.roomZ + toSpawn.spawnPosition.z
            )
        )

        EventManager.callEntitySpawnEvent(room, newWorldEntity, true)
    }
}

@Serializable
class SpawnEntitiesByTimeNearMinecart(
    private val maxSpawnTicks: Int = 100,
    private val maxSpawnEntities: Int = 5,
    private val spawnRadius: Int = 3
): TickProcess {
    private var spawnTicks = 0

    override fun process(room: Room) {
        if(room.isCompleted) return

        spawnTicks++

        if(spawnTicks >= maxSpawnTicks && room.livingEntities.size < maxSpawnEntities) {
            spawnEntity(room)
            spawnTicks = 0
        }
    }

    fun spawnEntity(room: Room) {
        val entitiesPools = room.roomConfig.getTag<EntitiesPool>() ?: return
        val pool = entitiesPools.roomEntities.randomOrNull() ?: return
        val toSpawn = pool.randomOrNull() ?: return
        val newEntity = EntityController.entities[toSpawn.entityName] ?: return
        val minecart = room.otherEntities.filterIsInstance<FollowMinecart>().randomOrNull() ?: return

        val random = Random.Default
        val x = random.nextInt(-spawnRadius, spawnRadius)
        val z = random.nextInt(-spawnRadius, spawnRadius)

        val newWorldEntity = newEntity.getDeclaredConstructor(Location::class.java).newInstance(
            Location(
                room.world,
                minecart.x + x,
                minecart.y + 0.5,
                minecart.z + z
            )
        )

        EventManager.callEntitySpawnEvent(room, newWorldEntity, true)
    }
}