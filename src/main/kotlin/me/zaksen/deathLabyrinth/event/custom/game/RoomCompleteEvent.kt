package me.zaksen.deathLabyrinth.event.custom.game

import me.zaksen.deathLabyrinth.game.room.Room
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/** @param players - List of alive players
 *  @param room - Completed room instance
 */
class RoomCompleteEvent(val players: List<Player>, val room: Room): Event() {

    override fun getHandlers(): HandlerList = HANDLER_LIST

    companion object {
        @JvmStatic
        private val HANDLER_LIST: HandlerList = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList = HANDLER_LIST
    }

}