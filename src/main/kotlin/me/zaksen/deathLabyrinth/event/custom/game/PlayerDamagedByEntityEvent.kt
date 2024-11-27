package me.zaksen.deathLabyrinth.event.custom.game

import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/** An event that will be triggered when a player takes damage from a entity
 *  @param damager - Damaging entity
 *  @param damaged - The player who took the damage
 *  @param damage - Damage amount
 */
class PlayerDamagedByEntityEvent(val damager: Entity, val damaged: Player, var damage: Double): Event(), Cancellable {

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