package me.zaksen.deathLabyrinth.item.gear.armor

import me.zaksen.deathLabyrinth.item.CustomItem
import me.zaksen.deathLabyrinth.item.ItemType
import me.zaksen.deathLabyrinth.item.settings.ItemSettings
import me.zaksen.deathLabyrinth.keys.PluginKeys
import me.zaksen.deathLabyrinth.util.*
import net.kyori.adventure.key.Key
import org.bukkit.NamespacedKey
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.inventory.EquipmentSlotGroup
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType

open class ArmorItem(id: String, settings: ItemSettings): CustomItem(id, ItemType.ARMOR, settings) {

    override fun asItemStack(): ItemStack {
        val stack = ItemStack(settings.material)
            .customModel(settings.customModel())
            .name(settings.displayName())
            .loreLine(settings.quality().visualText.asText().font(Key.key("dl:icons")))
            .loreMap(settings.lore())

        val meta = stack.itemMeta
        meta.persistentDataContainer.set(PluginKeys.customItemKey, PersistentDataType.STRING, id)
        meta.isUnbreakable = true

        applyAttributes(meta)

        stack.itemMeta = meta

        return stack
    }

    fun applyAttributes(meta: ItemMeta) {
        val name = settings.material.toString()
        val equipmentSlotGroup: EquipmentSlotGroup
        val modifierKey: NamespacedKey
        val modifierToughnessKey: NamespacedKey

        if(name.endsWith("HELMET")) {
            equipmentSlotGroup = EquipmentSlotGroup.HEAD
            modifierKey = PluginKeys.armorHelmetModifierKey
            modifierToughnessKey = PluginKeys.armorToughnessHelmetModifierKey
        } else if(name.endsWith("CHESTPLATE")) {
            equipmentSlotGroup = EquipmentSlotGroup.CHEST
            modifierKey = PluginKeys.armorChestplateModifierKey
            modifierToughnessKey = PluginKeys.armorToughnessChestplateModifierKey
        } else if(name.endsWith("LEGGINGS")) {
            equipmentSlotGroup = EquipmentSlotGroup.LEGS
            modifierKey = PluginKeys.armorLeggingsModifierKey
            modifierToughnessKey = PluginKeys.armorToughnessLeggingsModifierKey
        }else {
            equipmentSlotGroup = EquipmentSlotGroup.FEET
            modifierKey = PluginKeys.armorBootsModifierKey
            modifierToughnessKey = PluginKeys.armorToughnessBootsModifierKey
        }

        meta.removeAttributeModifier(Attribute.GENERIC_ARMOR)
        if(settings.defence() != 0.0) {
            meta.addAttributeModifier(
                Attribute.GENERIC_ARMOR, AttributeModifier(
                    modifierKey,
                    settings.defence(),
                    AttributeModifier.Operation.ADD_NUMBER,
                    equipmentSlotGroup
                )
            )
        }

        meta.removeAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS)
        if(settings.thoroughness() != 0.0) {
            meta.addAttributeModifier(
                Attribute.GENERIC_ARMOR_TOUGHNESS, AttributeModifier(
                    modifierToughnessKey,
                    settings.thoroughness(),
                    AttributeModifier.Operation.ADD_NUMBER,
                    equipmentSlotGroup
                )
            )
        }
    }
}