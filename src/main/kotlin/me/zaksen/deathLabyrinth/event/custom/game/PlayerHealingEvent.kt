package me.zaksen.deathLabyrinth.event.custom.game

import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/** An event that will be triggered when a player heals someone
 *  @param player - The player who caused the healing
 *  @param entity - A entity that has received healing (may be the player who summoned the healing)
 *  @param amount - Heal amount
 */
class PlayerHealingEvent(
    val player: Player,
    val entity: LivingEntity,
    var amount: Double
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