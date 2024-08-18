package me.zaksen.deathLabyrinth.event

import me.zaksen.deathLabyrinth.item.ItemsController
import me.zaksen.deathLabyrinth.keys.PluginKeys
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.persistence.PersistentDataType

class CustomItemEvents: Listener {

    @EventHandler
    fun processCustomItemHit(event: EntityDamageByEntityEvent) {
        val damager = event.damager
        if(damager is Player && damager.activeItem.hasItemMeta() && damager.activeItem.itemMeta.persistentDataContainer.has(PluginKeys.customItemKey)) {
            val itemId = damager.activeItem.itemMeta.persistentDataContainer.get(PluginKeys.customItemKey, PersistentDataType.STRING)!!
            val customItem = ItemsController.get(itemId)
            customItem?.onHit(event)
        }
    }

    @EventHandler
    fun processCustomItemUse(event: PlayerInteractEvent) {
        if(event.hasItem() && event.item!!.hasItemMeta() && event.item!!.itemMeta.persistentDataContainer.has(PluginKeys.customItemKey)) {
            val itemId = event.item!!.itemMeta.persistentDataContainer.get(PluginKeys.customItemKey, PersistentDataType.STRING)!!
            val customItem = ItemsController.get(itemId)
            customItem?.onUse(event)
        }
    }

    @EventHandler
    fun processCustomItemConsume(event: PlayerItemConsumeEvent) {
        if(event.item.hasItemMeta() && event.item.itemMeta.persistentDataContainer.has(PluginKeys.customItemKey)) {
            val itemId = event.item.itemMeta.persistentDataContainer.get(PluginKeys.customItemKey, PersistentDataType.STRING)!!
            val customItem = ItemsController.get(itemId)
            customItem?.onConsume(event)
        }
    }
}