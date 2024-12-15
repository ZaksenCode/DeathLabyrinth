package me.zaksen.deathLabyrinth.game.room.logic.completion

import kotlinx.serialization.Serializable
import me.zaksen.deathLabyrinth.config.RoomConfig
import me.zaksen.deathLabyrinth.config.data.Position
import me.zaksen.deathLabyrinth.entity.minecart.FollowMinecart
import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.game.room.Room
import me.zaksen.deathLabyrinth.util.drawSquare
import org.bukkit.World
import org.bukkit.craftbukkit.entity.CraftEntity
import org.bukkit.util.BoundingBox
import org.bukkit.util.Vector

@Serializable
class EntityCompletionCheck : CompletionCheck {
    override fun check(room: Room): Boolean {
        return room.livingEntities.isEmpty()
    }
}

@Serializable
class AlwaysCompletion : CompletionCheck {
    override fun check(room: Room): Boolean {
        return true
    }
}

@Serializable
class FollowMinecartExcept: CompletionCheck {
    val offset: Position = Position()
    val zoneSize: Position = Position()

    override fun check(room: Room): Boolean {
        val x = room.roomX + offset.x
        val y = room.roomY + offset.y
        val z = room.roomZ + offset.z

        debugDisplay(room.world, room.roomX, room.roomY, room.roomZ, room.roomConfig)

        return room.world.getNearbyEntities(BoundingBox(
            x,
            y,
            z,
            x + zoneSize.x,
            y + zoneSize.y,
            z + zoneSize.z
        )) {
            (it as CraftEntity).handle is FollowMinecart
        }.isNotEmpty()
    }

    override fun debugDisplay(world: World, x: Int, y: Int, z: Int, config: RoomConfig) {
        val drawX = x + offset.x
        val drawY = y + offset.y
        val drawZ = z + offset.z

        drawSquare(world, drawX, drawY, drawZ, drawX + zoneSize.x, drawY + zoneSize.y, drawZ + zoneSize.z)
    }
}