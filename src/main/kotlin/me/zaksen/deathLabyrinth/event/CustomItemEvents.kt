package me.zaksen.deathLabyrinth.event

import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.item.ItemType
import me.zaksen.deathLabyrinth.item.ItemsController
import me.zaksen.deathLabyrinth.item.weapon.WeaponItem
import me.zaksen.deathLabyrinth.keys.PluginKeys
import me.zaksen.deathLabyrinth.util.ChatUtil.actionBar
import me.zaksen.deathLabyrinth.util.ChatUtil.message
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.persistence.PersistentDataType

class CustomItemEvents: Listener {

    // FIXME - Любой класс может использовать любое оружие ( так не должно быть !)
    @EventHandler
    fun processCustomItemHit(event: EntityDamageByEntityEvent) {
        val damager = event.damager
        if(damager is Player && damager.activeItem.hasItemMeta() && damager.activeItem.itemMeta.persistentDataContainer.has(PluginKeys.customItemKey)) {
            val itemId = damager.activeItem.itemMeta.persistentDataContainer.get(PluginKeys.customItemKey, PersistentDataType.STRING)!!
            val customItem = ItemsController.get(itemId)

            if(customItem != null) {
                if(customItem.type == ItemType.WEAPON) {
                    val customWeapon = customItem as WeaponItem
                    val playerData = GameController.players[damager]

                    if(playerData?.playerClass != null && !playerData.playerClass!!.availableWeapons().contains(customWeapon.getWeaponType())) {
                        damager.actionBar("<red>Вы не можете пользоваться данным предметом!</red>")
                        event.isCancelled = true
                        return
                    }
                }

                customItem.onHit(event)
            }
        }
    }

    @EventHandler
    fun processCustomItemUse(event: PlayerInteractEvent) {
        if(event.hasItem() && event.item!!.hasItemMeta() && event.item!!.itemMeta.persistentDataContainer.has(PluginKeys.customItemKey)) {
            val itemId = event.item!!.itemMeta.persistentDataContainer.get(PluginKeys.customItemKey, PersistentDataType.STRING)!!
            val customItem = ItemsController.get(itemId)

            if(customItem?.type == ItemType.WEAPON) {
                val customWeapon = customItem as WeaponItem
                val playerData = GameController.players[event.player]

                if(playerData?.playerClass != null && !playerData.playerClass!!.availableWeapons().contains(customWeapon.getWeaponType())) {
                    event.player.actionBar("<red>Вы не можете пользоваться данным предметом!</red>")
                    event.isCancelled = true
                    return
                }
            }

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