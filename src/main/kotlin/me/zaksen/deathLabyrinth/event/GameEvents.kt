package me.zaksen.deathLabyrinth.event

import me.zaksen.deathLabyrinth.config.MainConfig
import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.game.GameStatus
import me.zaksen.deathLabyrinth.game.room.RoomController
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.ItemStack

class GameEvents(private val config: MainConfig): Listener {

    @EventHandler
    fun preventBlockBreaking(event: BlockBreakEvent) {
        if(!config.debug) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun preventBlockPlacing(event: BlockPlaceEvent) {
        if(!config.debug) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun processRoomEntityDeath(event: EntityDeathEvent) {
        RoomController.processEntityRoomDeath(event)
    }

    @EventHandler
    fun preventItemDrop(event: PlayerDropItemEvent) {
        if(!config.debug) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun useItem(event: PlayerInteractEvent) {
        val item: ItemStack = event.player.inventory.itemInMainHand
        val offHandItem: ItemStack = event.player.inventory.itemInOffHand

        itemCheck(item, event)
        itemCheck(offHandItem, event)
    }

    private fun itemCheck(stack: ItemStack, event: PlayerInteractEvent) {
        if(!stack.hasItemMeta()) {
            return
        }

        if(stack.type == Material.PAPER) {
            when(stack.itemMeta.customModelData) {
                200, 201 -> {
                    EventManager.callReadyEvent(event.player)
                    event.isCancelled = true
                }
            }
        }
    }

    @EventHandler
    fun setupPlayer(event: PlayerJoinEvent) {
        if(!config.debug) {
            GameController.join(event.player)
        }
    }

    @EventHandler
    fun discardPlayer(event: PlayerQuitEvent) {
        if(!config.debug) {
            GameController.leave(event.player)
        }
    }
}