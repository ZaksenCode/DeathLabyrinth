package me.zaksen.deathLabyrinth.game.room.logic.completion

import kotlinx.serialization.Serializable
import me.zaksen.deathLabyrinth.config.data.Position
import me.zaksen.deathLabyrinth.entity.minecart.FollowMinecart
import me.zaksen.deathLabyrinth.game.room.Room
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
}