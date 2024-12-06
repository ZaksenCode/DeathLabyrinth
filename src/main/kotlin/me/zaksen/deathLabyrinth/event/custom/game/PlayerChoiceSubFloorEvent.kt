package me.zaksen.deathLabyrinth.event.custom.game

import me.zaksen.deathLabyrinth.game.room.exit.choice.Choice
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/** The event that will be played during exit from floor was selected
 *  @param player - Player that pickup artifact
 *  @param choiceContainer - Choice container of selected exit
 */
class PlayerChoiceSubFloorEvent(val player: Player, val choice: Choice): Event(), Cancellable {
    private var cancelled = false

    override fun isCancelled(): Boolean {
        return this.cancelled
    }

    override fun setCancelled(cancel: Boolean) {
        this.cancelled = cancel
    }

    override fun getHandlers(): HandlerList = HANDLER_LIST

    companion object {
        @JvmStatic
        private val HANDLER_LIST: HandlerList = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList = HANDLER_LIST
    }
}