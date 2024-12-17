package me.zaksen.deathLabyrinth.game.room.logic.tags

import kotlinx.serialization.Serializable
import me.zaksen.deathLabyrinth.config.RoomConfig
import me.zaksen.deathLabyrinth.config.data.Position
import me.zaksen.deathLabyrinth.game.room.Room
import me.zaksen.deathLabyrinth.util.drawCircle
import org.bukkit.Color
import org.bukkit.Particle
import org.bukkit.World

@Serializable
data class NecromancerOffset(
    val offset: Position = Position(16.0, 2.0, 16.0)
) : RoomTag {
    override fun debugDisplay(world: World, x: Int, y: Int, z: Int, config: RoomConfig) {
        val spawnX = x + offset.x
        val spawnY = y + offset.y
        val spawnZ = z + offset.z

        drawCircle(
            Particle.DUST,
            world,
            spawnX,
            spawnY,
            spawnZ,
            0.25,
            Color.BLUE,
            particleSize = 0.25f
        )
    }

    override fun addOffset(x: Int, y: Int, z: Int) {
        offset.x += x
        offset.y += y
        offset.z += z
    }
}