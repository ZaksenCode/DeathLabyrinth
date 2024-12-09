package me.zaksen.deathLabyrinth.game.room.logic.tick

import kotlinx.serialization.Serializable
import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.game.room.Room
import org.bukkit.GameMode
import org.bukkit.util.Vector

@Serializable
class HeightMinLimit(private val height: Int): TickProcess {
    override fun process(room: Room) {
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
}