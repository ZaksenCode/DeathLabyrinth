package me.zaksen.deathLabyrinth.event.custom.game

import me.zaksen.deathLabyrinth.game.room.Room
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/** @param player - Player that will receive reward
 *  @param room - Completed room instance
 *  @param reward - Reward to grant player
 */
class PlayerRoomCompleteEvent(val player: Player, val room: Room, var reward: Int): Event() {

    override fun getHandlers(): HandlerList = HANDLER_LIST

    companion object {
        @JvmStatic
        private val HANDLER_LIST: HandlerList = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList = HANDLER_LIST
    }

}