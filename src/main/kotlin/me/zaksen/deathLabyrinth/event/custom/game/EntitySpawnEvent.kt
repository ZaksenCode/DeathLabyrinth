package me.zaksen.deathLabyrinth.event.custom.game

import org.bukkit.World
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import org.bukkit.event.Cancellable

class EntitySpawnEvent(val world: World, var entity: net.minecraft.world.entity.Entity, var requireKill: Boolean, val debug: Boolean = false):
    Event(), Cancellable {

    private var cancelled = false

    override fun getHandlers(): HandlerList {
        return HANDLER_LIST
    }

    override fun isCancelled(): Boolean {
        return this.cancelled
    }

    override fun setCancelled(cancel: Boolean) {
        this.cancelled = cancel
    }

    companion object {
        private val HANDLER_LIST: HandlerList = HandlerList()

        fun getHandlersList(): HandlerList {
            return HANDLER_LIST
        }
    }
}