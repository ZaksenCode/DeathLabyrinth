package me.zaksen.deathLabyrinth.game.room.logic.tags

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.zaksen.deathLabyrinth.config.RoomConfig
import me.zaksen.deathLabyrinth.config.data.Entity
import me.zaksen.deathLabyrinth.game.room.Room
import me.zaksen.deathLabyrinth.util.drawCircle
import org.bukkit.Color
import org.bukkit.Particle
import org.bukkit.World

@Serializable
data class EntitiesPool(
    @SerialName("room_entities")
    val roomEntities: MutableList<MutableList<Entity>> = mutableListOf(
        mutableListOf(
            Entity(),
            Entity("big_bone_wolf")
        )
    ),
): RoomTag {
    override fun debugDisplay(world: World, x: Int, y: Int, z: Int, config: RoomConfig) {
        for(pool in roomEntities) {
            for(entity in pool) {
                drawCircle(
                    Particle.DUST,
                    world,
                    x + entity.spawnPosition.x,
                    y + entity.spawnPosition.y,
                    z + entity.spawnPosition.z,
                    0.25,
                    Color.RED,
                    particleSize = 0.25f,
                    incrementAmount = 3.0
                )
            }
        }
    }

    override fun addOffset(x: Int, y: Int, z: Int) {
        for(pool in roomEntities) {
            for(entity in pool) {
                entity.spawnPosition.x += x
                entity.spawnPosition.y += y
                entity.spawnPosition.z += z
            }
        }
    }
}