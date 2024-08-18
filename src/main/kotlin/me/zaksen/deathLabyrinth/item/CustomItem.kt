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

abstract class CustomItem(private val id: String, private val type: ItemType, private val settings: ItemSettings) {

    fun getItemType(): ItemType {
        return type
    }

    fun onHit(event: EntityDamageByEntityEvent) {

    }

    open fun onUse(event: PlayerInteractEvent) {

    }

    fun onConsume(event: PlayerItemConsumeEvent) {

    }

    fun asItemstack(): ItemStack {
        val stack = ItemStack(settings.material)
            .customModel(settings.customModel())
            .name(settings.displayName())
            .loreMap(settings.lore())

        val meta = stack.itemMeta
        val persistentData = meta.persistentDataContainer

        persistentData.set(PluginKeys.customItemKey, PersistentDataType.STRING, id)

        return stack
    }
}