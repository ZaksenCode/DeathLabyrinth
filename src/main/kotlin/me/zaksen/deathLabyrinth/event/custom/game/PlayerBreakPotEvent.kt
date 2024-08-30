package me.zaksen.deathLabyrinth.event.custom.game

import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.block.BlockBreakEvent

class PlayerBreakPotEvent(player: Player, decoratedPot: Block): BlockBreakEvent(decoratedPot, player) {
    override fun getHandlers(): HandlerList {
        return HANDLER_LIST
    }

    companion object {
        private val HANDLER_LIST: HandlerList = HandlerList()

        fun getHandlersList(): HandlerList {
            return HANDLER_LIST
        }
    }
}