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
    lateinit var maxHealthModifierKey: NamespacedKey

    lateinit var armorHelmetModifierKey: NamespacedKey
    lateinit var armorChestplateModifierKey: NamespacedKey
    lateinit var armorLeggingsModifierKey: NamespacedKey
    lateinit var armorBootsModifierKey: NamespacedKey

    lateinit var armorToughnessHelmetModifierKey: NamespacedKey
    lateinit var armorToughnessChestplateModifierKey: NamespacedKey
    lateinit var armorToughnessLeggingsModifierKey: NamespacedKey
    lateinit var armorToughnessBootsModifierKey: NamespacedKey

    fun setup(plugin: Plugin) {
        customItemKey = NamespacedKey(plugin, "custom_item_id")
        customItemCooldownKey = NamespacedKey(plugin, "custom_item_cooldown")

        customItemDamageKey = NamespacedKey(plugin, "custom_item_damage")
        customItemAttackSpeedKey = NamespacedKey(plugin, "custom_item_attack_speed")
        customItemRangeBlockKey = NamespacedKey(plugin, "custom_item_block_range")
        customItemRangeEntityKey = NamespacedKey(plugin, "custom_item_entity_range")

        speedModifierKey = NamespacedKey(plugin, "speed_modifier")
        maxHealthModifierKey = NamespacedKey(plugin, "max_health_modifier")

        armorHelmetModifierKey = NamespacedKey(plugin, "armor_helmet_modifier")
        armorChestplateModifierKey = NamespacedKey(plugin, "armor_chestplate__modifier")
        armorLeggingsModifierKey = NamespacedKey(plugin, "armor_leggings_modifier")
        armorBootsModifierKey = NamespacedKey(plugin, "armor_boots_modifier")

        armorToughnessHelmetModifierKey = NamespacedKey(plugin, "armor_helmet_toughness_modifier")
        armorToughnessChestplateModifierKey = NamespacedKey(plugin, "armor_chestplate_toughness_modifier")
        armorToughnessLeggingsModifierKey = NamespacedKey(plugin, "armor_leggings_toughness_modifier")
        armorToughnessBootsModifierKey = NamespacedKey(plugin, "armor_boots_toughness_modifier")
    }
}