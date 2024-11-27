package me.zaksen.deathLabyrinth.event.custom.game

import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import org.bukkit.inventory.ItemStack

/** Calls out when a creature dies
 *  @param player - The player who killed the entity (can be null if the creature died without the player's participation)
 *  @param entity - The entity that died
 *  @param drops - Loot dropped from an entity
 */
class PlayerKillEntityEvent(val player: Player?, val entity: LivingEntity, val drops: List<ItemStack>): Event(), Cancellable {
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