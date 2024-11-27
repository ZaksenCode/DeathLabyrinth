package me.zaksen.deathLabyrinth.event.custom.game

import org.bukkit.World
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import org.bukkit.event.Cancellable

/** Event that will be triggered when the entity is created
 *  @param world - A world where an entity has been created
 *  @param entity - The entity to be created
 *  @param requireKill - Is it required to kill the entity in order to pass the room
 *  @param debug - Value that indicates if the mode is running in test mode
 */
class EntitySpawnEvent(val world: World, var entity: net.minecraft.world.entity.Entity, var requireKill: Boolean, val debug: Boolean = false):
    Event(), Cancellable {

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