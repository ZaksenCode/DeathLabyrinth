package me.zaksen.deathLabyrinth.game.room.logic.tags

import kotlinx.serialization.Serializable
import me.zaksen.deathLabyrinth.config.data.Position
import me.zaksen.deathLabyrinth.game.room.Room
import me.zaksen.deathLabyrinth.util.drawCircle
import org.bukkit.Color
import org.bukkit.Particle

@Serializable
data class NecromancerOffset(
    val offset: Position = Position(16.0, 2.0, 16.0)
) : RoomTag {
    override fun debugDisplay(room: Room) {
        val spawnX = room.roomX + offset.x
        val spawnY = room.roomY + offset.y
        val spawnZ = room.roomZ + offset.z

        drawCircle(
            Particle.DUST,
            room.world,
            spawnX,
            spawnY,
            spawnZ,
            0.25,
            Color.BLUE,
            particleSize = 0.25f
        )
    }
}