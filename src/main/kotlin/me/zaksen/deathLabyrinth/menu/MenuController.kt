package me.zaksen.deathLabyrinth.menu

import me.zaksen.deathLabyrinth.menu.api.Menu
import org.bukkit.entity.HumanEntity
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import java.util.*

object MenuController {
    private var menus: MutableMap<UUID, Menu> = mutableMapOf()

    fun openMenu(player: HumanEntity, menu: Menu) {
        menus[player.uniqueId] = menu
        player.openInventory(menu.inventory)
    }

    fun closeMenu(player: HumanEntity) {
        player.closeInventory(InventoryCloseEvent.Reason.PLUGIN)
        forgetMenu(player)
    }

    fun forgetMenu(player: HumanEntity) {
        menus.remove(player.uniqueId)
    }

    fun getMenu(player: HumanEntity): Menu? {
        return menus[player.uniqueId]
    }

    fun processMenuOpen(event: InventoryOpenEvent) {
        menus[event.player.uniqueId]?.onOpen(event)
    }

    fun processMenuClose(event: InventoryCloseEvent) {
        menus[event.player.uniqueId]?.onClose(event)
    }

    fun processMenuClick(event: InventoryClickEvent) {
        menus[event.whoClicked.uniqueId]?.onClick(event)
    }
}