package me.zaksen.deathLabyrinth.game.room.logic.start

import kotlinx.serialization.Serializable
import me.zaksen.deathLabyrinth.artifacts.ArtifactsController
import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.game.room.Room
import me.zaksen.deathLabyrinth.game.room.logic.tags.ArtifactsChainOffset
import org.bukkit.Location

@Serializable
class SpawnArtifactsChain: StartProcess {
    override fun process(room: Room) {
        val offset = room.roomConfig.getTag<ArtifactsChainOffset>() ?: return

        val location = Location(
            room.world,
            room.roomX + offset.offset.x,
            room.roomY + offset.offset.y,
            room.roomZ + offset.offset.z
        )
        location.yaw = 90f

        ArtifactsController.startArtifactsChain(location, GameController.players.size, 3, false)
    }
}