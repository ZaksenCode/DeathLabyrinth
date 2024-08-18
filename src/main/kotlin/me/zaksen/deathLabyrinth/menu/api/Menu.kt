package me.zaksen.deathLabyrinth.menu.api

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.function.Consumer

abstract class Menu(menuType: MenuType, title: Component) {

    val inventory: Inventory = Bukkit.createInventory(null, menuType.size(), title)
    private val itemMap: MutableMap<Int, ItemStack> = mutableMapOf()
    private val actionMap: MutableMap<Int, Consumer<InventoryClickEvent>> = mutableMapOf()

    open fun fill(stack: ItemStack) {
        for(i in 0..<inventory.size) {
            setItem(i, stack)
        }
    }

    open fun setItem(index: Int, stack: ItemStack, function: Consumer<InventoryClickEvent>) {
        setItem(index, stack)
        setAction(index, function)
    }

    open fun setItem(index: Int, stack: ItemStack) {
        if(index >= inventory.size || index < 0) {
            return
        }

        itemMap[index] = stack
    }

    open fun setAction(index: Int, function: Consumer<InventoryClickEvent>) {
        actionMap[index] = function
    }

    open fun updateInventory() {
        itemMap.forEach{(index, stack) -> inventory.setItem(index, stack)}
    }

    open fun onOpen(event: InventoryOpenEvent) {

    }

    open fun onClose(event: InventoryCloseEvent) {

    }

    open fun onClick(event: InventoryClickEvent) {
        actionMap[event.slot]?.accept(event)
        event.isCancelled = true
    }
}