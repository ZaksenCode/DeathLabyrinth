package me.zaksen.deathLabyrinth.game.room.logic.tags

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.zaksen.deathLabyrinth.config.data.Entity
import me.zaksen.deathLabyrinth.game.room.Room
import me.zaksen.deathLabyrinth.util.drawCircle
import org.bukkit.Color
import org.bukkit.Particle

@Serializable
data class EntitiesPool(
    @SerialName("room_entities")
    val roomEntities: List<List<Entity>> = listOf(
        listOf(
            Entity(),
            Entity("big_bone_wolf")
        )
    ),
): RoomTag {
    override fun debugDisplay(room: Room) {
        for(pool in roomEntities) {
            for(entity in pool) {
                drawCircle(
                    Particle.DUST,
                    room.world,
                    room.roomX + entity.spawnPosition.x,
                    room.roomY + entity.spawnPosition.y,
                    room.roomZ + entity.spawnPosition.z,
                    0.25,
                    Color.RED,
                    particleSize = 0.25f,
                    incrementAmount = 10.0
                )
            }
        }
    }
}