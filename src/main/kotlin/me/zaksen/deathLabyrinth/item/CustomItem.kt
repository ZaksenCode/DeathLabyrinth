package me.zaksen.deathLabyrinth.item

import me.zaksen.deathLabyrinth.item.settings.ItemSettings
import me.zaksen.deathLabyrinth.keys.PluginKeys
import me.zaksen.deathLabyrinth.util.*
import net.kyori.adventure.key.Key
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

open class CustomItem(val id: String, val type: ItemType, val settings: ItemSettings) {

    open fun asItemStack(): ItemStack {
        val stack = ItemStack(settings.material)
            .customModel(settings.customModel())
            .name(settings.displayName())
            .loreLine(settings.quality().visualText.asText().font(Key.key("dl:icons")))
            .loreMap(settings.lore())

        val meta = stack.itemMeta
        meta.persistentDataContainer.set(PluginKeys.customItemKey, PersistentDataType.STRING, id)
        meta.persistentDataContainer.set(PluginKeys.customItemAbilitiesKey, PersistentDataType.STRING, settings.abilities().string())
        meta.isUnbreakable = true
        stack.itemMeta = meta

        return stack
    }

    fun checkAndUpdateCooldown(item: ItemStack): Boolean {
        if(item.itemMeta.persistentDataContainer.has(PluginKeys.customItemCooldownKey)) {
            val cooldown = item.itemMeta.persistentDataContainer.get(PluginKeys.customItemCooldownKey, PersistentDataType.LONG)

            if(cooldown != null && System.currentTimeMillis() - cooldown >= settings.abilityCooldown()) {
                val meta = item.itemMeta
                meta.persistentDataContainer.set(PluginKeys.customItemCooldownKey, PersistentDataType.LONG, System.currentTimeMillis())
                item.setItemMeta(meta)
                return true
            } else {
                return false
            }
        } else {
            val meta = item.itemMeta
            meta.persistentDataContainer.set(PluginKeys.customItemCooldownKey, PersistentDataType.LONG, System.currentTimeMillis())
            item.setItemMeta(meta)
            return true
        }
    }
}