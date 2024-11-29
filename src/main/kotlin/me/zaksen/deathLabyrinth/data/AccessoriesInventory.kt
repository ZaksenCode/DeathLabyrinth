package me.zaksen.deathLabyrinth.data

import me.zaksen.deathLabyrinth.item.accessory.AccessoriesAbilities
import me.zaksen.deathLabyrinth.item.accessory.AccessorySlot
import me.zaksen.deathLabyrinth.keys.PluginKeys
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

// TODO - Add puting into accessory slots
class AccessoriesInventory {
    private val slots = mapOf(
        Pair(10, AccessorySlot.AMULET),
        Pair(12, AccessorySlot.BELT),
        Pair(14, AccessorySlot.RING),
        Pair(15, AccessorySlot.RING),
        Pair(16, AccessorySlot.RING)
    )

    val items = mutableMapOf<Int, ItemStack>(

    )

    fun tryPutStack(slot: Int, stack: ItemStack): Boolean {
        if(!stack.hasItemMeta()) return false
        if(!stack.itemMeta.persistentDataContainer.has(PluginKeys.accessorySlotKey)) return false
        val stackSlot = stack.itemMeta.persistentDataContainer.get(PluginKeys.accessorySlotKey, PersistentDataType.STRING)!!
        val puttingSlot = slots[slot] ?: return false
        if(stackSlot != puttingSlot.toString()) { return false }

        setStack(slot, stack)

        return true
    }

    fun setStack(slot: Int, stack: ItemStack) {
        items[slot] = stack
    }

    fun removeStack(slot: Int) {
        items.remove(slot)
    }

    fun processAccessories(event: Event, player: Player) {
        items.forEach {
            val stack = it.value

            if(!stack.hasItemMeta()) return
            if(!stack.itemMeta.persistentDataContainer.has(PluginKeys.customItemKey)) return

            AccessoriesAbilities.invokeAccessoryAbilities(event, it.value, player)
        }
    }
}