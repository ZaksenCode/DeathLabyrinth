package me.zaksen.deathLabyrinth.event.custom.game

import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/** An event that triggers when a creature takes damage from a player
 *  @param player - Damaging player
 *  @param damaged - The entity who took the damage
 *  @param damage - Damage amount
 */
class PlayerDamageEntityEvent(
    val player: Player,
    val damaged: LivingEntity,
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