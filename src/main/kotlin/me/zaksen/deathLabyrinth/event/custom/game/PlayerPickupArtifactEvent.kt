package me.zaksen.deathLabyrinth.event.custom.game

import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/** The event that will be played during artifact pickup (before it is added to the inventory)
 *  @param player - Player that pickup artifact
 *  @param artifact - An artifact to be picked up
 */
class PlayerPickupArtifactEvent(val player: Player, val artifact: Artifact): Event(), Cancellable {
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