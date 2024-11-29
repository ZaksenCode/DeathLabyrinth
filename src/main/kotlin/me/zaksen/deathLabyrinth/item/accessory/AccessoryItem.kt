package me.zaksen.deathLabyrinth.item.accessory

import me.zaksen.deathLabyrinth.item.CustomItem
import me.zaksen.deathLabyrinth.item.ItemType
import me.zaksen.deathLabyrinth.item.settings.ItemSettings
import me.zaksen.deathLabyrinth.keys.PluginKeys
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

abstract class AccessoryItem(
    id: String,
    val slot: AccessorySlot,
    settings: ItemSettings
) : CustomItem(id, ItemType.ACCESSORY, settings) {
    override fun asItemStack(): ItemStack {
        val stack = super.asItemStack()

        val meta = stack.itemMeta
        meta.persistentDataContainer.set(PluginKeys.accessorySlotKey, PersistentDataType.STRING, slot.toString())
        stack.itemMeta = meta

        return stack
    }
}