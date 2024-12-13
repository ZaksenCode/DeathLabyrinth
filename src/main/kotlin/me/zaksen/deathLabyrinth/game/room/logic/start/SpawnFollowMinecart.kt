package me.zaksen.deathLabyrinth.game.room.logic.start

import kotlinx.serialization.Serializable
import me.zaksen.deathLabyrinth.config.data.Position
import me.zaksen.deathLabyrinth.entity.minecart.FollowMinecart
import me.zaksen.deathLabyrinth.game.room.Room
import me.zaksen.deathLabyrinth.util.tryAddEntity
import org.bukkit.Location

@Serializable
class SpawnFollowMinecart: StartProcess {
    val offset: Position = Position(16.0, 2.0, 16.0)

    override fun process(room: Room) {
        val toSpawn = FollowMinecart(
            Location(
                room.world,
                room.roomX + offset.x,
                room.roomY + offset.y,
                room.roomZ + offset.z
            )
        )

        room.otherEntities.add(toSpawn)
        room.world.tryAddEntity(toSpawn)
    }
}