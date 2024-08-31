package me.zaksen.deathLabyrinth.event.custom.game

import net.minecraft.world.entity.LivingEntity
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class SpellEntityDamageEvent(var entity: LivingEntity, var damage: Double, async: Boolean = false): Event(async), Cancellable {
    private var cancelled = false

    override fun getHandlers(): HandlerList {
        return HANDLER_LIST
    }

    companion object {
        private val HANDLER_LIST: HandlerList = HandlerList()

        fun getHandlersList(): HandlerList {
            return HANDLER_LIST
        }
    }

    override fun isCancelled(): Boolean {
        return this.cancelled
    }

    override fun setCancelled(cancel: Boolean) {
        this.cancelled = cancel
    }
}