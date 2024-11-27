package me.zaksen.deathLabyrinth.event.custom.game

import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/** The event that will be lost after an artifact pickup event (PlayerArtifactPickupEvent)
 *  is best used for example to apply modifiers to player attributes (See RedJar or MysticPotion)
 *  @param player - Player that pickup artifact
 *  @param artifact - The artifact that was picked up
 */
class PlayerPostPickupArtifactEvent(val player: Player, val artifact: Artifact): Event(), Cancellable {
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