package me.zaksen.deathLabyrinth.keys

import org.bukkit.NamespacedKey
import org.bukkit.plugin.Plugin

object PluginKeys {

    lateinit var customItemKey: NamespacedKey
    lateinit var customItemCooldownKey: NamespacedKey

    fun setup(plugin: Plugin) {
        customItemKey = NamespacedKey(plugin, "custom_item_id")
        customItemCooldownKey = NamespacedKey(plugin, "custom_item_cooldown")
    }
}