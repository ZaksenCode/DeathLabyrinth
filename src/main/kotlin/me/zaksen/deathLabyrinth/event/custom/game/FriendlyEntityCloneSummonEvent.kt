package me.zaksen.deathLabyrinth.event.custom.game

import net.minecraft.world.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/** Event that will be triggered when a player's entity is cloned
 *  @param player - The player whose entity was cloned
 *  @param entity - A cloned entity.
 */
class FriendlyEntityCloneSummonEvent(var player: Player, var entity: LivingEntity): Event(), Cancellable {
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