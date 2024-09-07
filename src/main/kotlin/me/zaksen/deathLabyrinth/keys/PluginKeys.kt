package me.zaksen.deathLabyrinth.keys

import org.bukkit.NamespacedKey
import org.bukkit.plugin.Plugin

object PluginKeys {

    lateinit var customItemKey: NamespacedKey
    lateinit var customItemCooldownKey: NamespacedKey

    lateinit var customItemDamageKey: NamespacedKey
    lateinit var customItemAttackSpeedKey: NamespacedKey
    lateinit var customItemRangeBlockKey: NamespacedKey
    lateinit var customItemRangeEntityKey: NamespacedKey

    lateinit var speedModifierKey: NamespacedKey

    fun setup(plugin: Plugin) {
        customItemKey = NamespacedKey(plugin, "custom_item_id")
        customItemCooldownKey = NamespacedKey(plugin, "custom_item_cooldown")

        customItemDamageKey = NamespacedKey(plugin, "custom_item_damage")
        customItemAttackSpeedKey = NamespacedKey(plugin, "custom_item_attack_speed")
        customItemRangeBlockKey = NamespacedKey(plugin, "custom_item_block_range")
        customItemRangeEntityKey = NamespacedKey(plugin, "custom_item_entity_range")

        speedModifierKey = NamespacedKey(plugin, "speed_modifier")
    }
}