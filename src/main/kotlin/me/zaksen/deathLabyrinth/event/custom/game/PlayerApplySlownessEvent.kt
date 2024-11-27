package me.zaksen.deathLabyrinth.event.custom.game

import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/** The event that will be triggered when the player applies a slowness effect
 *  @param player - The player who imposes the slowdown
 *  @param entity - The entity to which the slowness was applied
 *  @param duration - Slowness duration
 *  @param amplifier - Slowness level (Should be in range 0-255)
 */
class PlayerApplySlownessEvent(
    val player: Player,
    val entity: LivingEntity,
    var duration: Int,
    var amplifier: Int
): Event(),
    Cancellable {
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