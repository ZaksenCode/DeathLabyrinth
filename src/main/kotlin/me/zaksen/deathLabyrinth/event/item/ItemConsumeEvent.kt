package me.zaksen.deathLabyrinth.event.item

import me.zaksen.deathLabyrinth.item.CustomItem
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.inventory.ItemStack

class ItemConsumeEvent (
    val player: Player,
    val stack: ItemStack?,
    val item: CustomItem?,
    val event: PlayerItemConsumeEvent
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