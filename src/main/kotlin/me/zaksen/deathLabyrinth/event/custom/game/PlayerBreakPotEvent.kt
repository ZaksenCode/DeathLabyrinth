package me.zaksen.deathLabyrinth.event.custom.game

import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import org.bukkit.inventory.ItemStack

/** The event that will be triggered when a player breaks a pot
 *  @param player - The player who broke the pot
 *  @param decoratedPot - Pot block
 *  @param output - The item that falls out of the pot
 */
class PlayerBreakPotEvent(val player: Player, val decoratedPot: Block, var output: ItemStack): Event() {
    override fun getHandlers(): HandlerList = HANDLER_LIST

    companion object {
        @JvmStatic
        private val HANDLER_LIST: HandlerList = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList {
            return HANDLER_LIST
        }
    }
}