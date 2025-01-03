package me.zaksen.deathLabyrinth.event.custom.game

import me.zaksen.deathLabyrinth.damage.DamageType
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/** An event that will be triggered when a friendly entity deals damage
 *  @param damager - The entity that deal the damage
 *  @param entity - The entity that took the damage
 *  @param damage - Damage amount
 *  @param damageType - Type of damage
 */
class FriendlyEntityDamageEntityEvent(val damager: Entity, val entity: LivingEntity, var damage: Double, val damageType: DamageType = DamageType.GENERAL): Event(), Cancellable {
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