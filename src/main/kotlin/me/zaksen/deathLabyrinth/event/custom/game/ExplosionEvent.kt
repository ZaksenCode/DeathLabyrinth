package me.zaksen.deathLabyrinth.event.custom.game

import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/** The event that will be triggered when an explosion occurs without an owner
 *  @param position - Position where the explosion was caused
 *  @param range - Explosion range
 *  @param damage - Explosion damage
 */
class ExplosionEvent(
    val position: Location,
    var range: Double,
    var damage: Double
): Event(), Cancellable {
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