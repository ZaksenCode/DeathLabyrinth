package me.zaksen.deathLabyrinth.event.item

import me.zaksen.deathLabyrinth.damage.DamageType
import me.zaksen.deathLabyrinth.item.CustomItem
import org.bukkit.entity.Entity
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import org.bukkit.inventory.ItemStack

class ItemKillEvent(val damager: Entity, val damaged: Entity, val stack: ItemStack, val item: CustomItem, var damage: Double, val damageType: DamageType = DamageType.GENERAL): Event(), Cancellable {

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