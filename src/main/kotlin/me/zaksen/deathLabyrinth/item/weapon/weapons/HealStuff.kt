package me.zaksen.deathLabyrinth.item.weapon.weapons

import me.zaksen.deathLabyrinth.item.settings.ItemSettings
import me.zaksen.deathLabyrinth.item.weapon.WeaponItem
import me.zaksen.deathLabyrinth.item.weapon.WeaponType
import me.zaksen.deathLabyrinth.keys.PluginKeys
import me.zaksen.deathLabyrinth.util.ChatUtil
import org.bukkit.Material
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.persistence.PersistentDataType

class HealStuff(id: String): WeaponItem(
    WeaponType.STAFF,
    id,
    ItemSettings(Material.STICK).customModel(100).displayName(ChatUtil.format("<green>Посох исцеления</green>")))
{
    override fun onUse(event: PlayerInteractEvent) {
        val item = event.item!!


        if(item.itemMeta.persistentDataContainer.has(PluginKeys.customItemCooldownKey)) {
            val cooldown = item.itemMeta.persistentDataContainer.get(PluginKeys.customItemCooldownKey, PersistentDataType.INTEGER)
        } else {
            // TODO - USE
        }
    }
}