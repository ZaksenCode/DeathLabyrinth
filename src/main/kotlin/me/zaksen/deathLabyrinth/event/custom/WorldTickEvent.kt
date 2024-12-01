package me.zaksen.deathLabyrinth.event.custom

import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class WorldTickEvent: Event() {
    override fun getHandlers(): HandlerList = HANDLER_LIST

    companion object {
        @JvmStatic
        private val HANDLER_LIST: HandlerList = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList = HANDLER_LIST
    }
}