package me.zaksen.deathLabyrinth.game.room.logic.start

import kotlinx.serialization.Serializable
import me.zaksen.deathLabyrinth.game.room.Room
import me.zaksen.deathLabyrinth.game.room.exit.RoomExitController
import me.zaksen.deathLabyrinth.game.room.logic.tags.ChoiceContainerOffset
import org.bukkit.Location

@Serializable
class SpawnChoiceContainer: StartProcess {
    override fun process(room: Room) {
        val offset = room.roomConfig.getTag<ChoiceContainerOffset>() ?: return

        val location = Location(
            room.world,
            room.roomX + offset.offset.x,
            room.roomY + offset.offset.y,
            room.roomZ + offset.offset.z
        )
        location.yaw = 90f

        RoomExitController.startChoice(location)
    }
}