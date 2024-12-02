package me.zaksen.deathLabyrinth.event

import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.item.ItemType
import me.zaksen.deathLabyrinth.item.ItemsController
import me.zaksen.deathLabyrinth.item.weapon.WeaponItem
import me.zaksen.deathLabyrinth.keys.PluginKeys
import me.zaksen.deathLabyrinth.util.asTranslate
import net.kyori.adventure.text.format.TextColor
import org.bukkit.entity.LivingEntity
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
        if(damager is Player && damager.inventory.itemInMainHand.hasItemMeta() &&
            damager.inventory.itemInMainHand.itemMeta.persistentDataContainer.has(PluginKeys.customItemKey)) {
            val itemId = damager.inventory.itemInMainHand.itemMeta.persistentDataContainer.get(PluginKeys.customItemKey, PersistentDataType.STRING)!!
            val customItem = ItemsController.get(itemId)

            if(customItem != null) {
                if(customItem.type == ItemType.WEAPON) {
                    val customWeapon = customItem as WeaponItem
                    val playerData = GameController.players[damager]

                    if(playerData?.playerClass != null && !playerData.playerClass!!.availableWeapons().contains(customWeapon.getWeaponType())) {
                        damager.sendActionBar("ui.item.wrong_class".asTranslate().color(TextColor.color(220,20,60)))
                        event.isCancelled = true
                        return
                    }
                }
                val entity = event.entity

                EventManager.callItemHitEvent(damager, entity, damager.inventory.itemInMainHand, customItem, event, customItem.settings.damageType())

                if(entity is LivingEntity && (entity.health - event.damage <= 0)) {
                       EventManager.callItemKillEvent(damager, entity, damager.inventory.itemInMainHand, customItem, event, customItem.settings.damageType())
                }
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
                    event.player.sendActionBar("ui.item.wrong_class".asTranslate().color(TextColor.color(220,20,60)))
                    event.isCancelled = true
                    return
                }
            }

            EventManager.callItemUseEvent(event.player, event.item, customItem, event)
        }
    }

    @EventHandler
    fun processCustomItemConsume(event: PlayerItemConsumeEvent) {
        if(event.item.hasItemMeta() && event.item.itemMeta.persistentDataContainer.has(PluginKeys.customItemKey)) {
            val itemId = event.item.itemMeta.persistentDataContainer.get(PluginKeys.customItemKey, PersistentDataType.STRING)!!
            val customItem = ItemsController.get(itemId)
            EventManager.callItemConsumeEvent(event.player, event.item, customItem, event)
        }
    }
}