package me.zaksen.deathLabyrinth.item

import me.zaksen.deathLabyrinth.item.settings.ItemSettings
import me.zaksen.deathLabyrinth.keys.PluginKeys
import me.zaksen.deathLabyrinth.util.customModel
import me.zaksen.deathLabyrinth.util.loreMap
import me.zaksen.deathLabyrinth.util.name
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

abstract class CustomItem(val id: String, val type: ItemType, val settings: ItemSettings) {

    open fun onHit(event: EntityDamageByEntityEvent) {

    }

    open fun onUse(event: PlayerInteractEvent) {

    }

    open fun onConsume(event: PlayerItemConsumeEvent) {

    }

    open fun asItemStack(): ItemStack {
        val stack = ItemStack(settings.material)
            .customModel(settings.customModel())
            .name(settings.displayName())
            .loreMap(settings.lore())

        val meta = stack.itemMeta
        meta.persistentDataContainer.set(PluginKeys.customItemKey, PersistentDataType.STRING, id)
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