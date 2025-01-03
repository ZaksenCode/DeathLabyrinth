package me.zaksen.deathLabyrinth.item.gear.armor

import me.zaksen.deathLabyrinth.item.CustomItem
import me.zaksen.deathLabyrinth.item.ItemType
import me.zaksen.deathLabyrinth.item.settings.ItemSettings
import me.zaksen.deathLabyrinth.keys.PluginKeys
import org.bukkit.NamespacedKey
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.inventory.EquipmentSlotGroup
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

open class ArmorItem(id: String, settings: ItemSettings): CustomItem(id, ItemType.ARMOR, settings) {

    override fun asItemStack(): ItemStack {
        val stack = super.asItemStack()

        val meta = stack.itemMeta
        applyAttributes(meta)
        stack.itemMeta = meta

        return stack
    }

    private fun applyAttributes(meta: ItemMeta) {
        val name = settings.material.toString()
        val equipmentSlotGroup: EquipmentSlotGroup
        val modifierKey: NamespacedKey
        val modifierToughnessKey: NamespacedKey
        val modifierKnockbackKey: NamespacedKey

        if(name.endsWith("HELMET")) {
            equipmentSlotGroup = EquipmentSlotGroup.HEAD
            modifierKey = PluginKeys.armorHelmetModifierKey
            modifierToughnessKey = PluginKeys.armorToughnessHelmetModifierKey
            modifierKnockbackKey = PluginKeys.armorKnockbackHelmetModifierKey
        } else if(name.endsWith("CHESTPLATE")) {
            equipmentSlotGroup = EquipmentSlotGroup.CHEST
            modifierKey = PluginKeys.armorChestplateModifierKey
            modifierToughnessKey = PluginKeys.armorToughnessChestplateModifierKey
            modifierKnockbackKey = PluginKeys.armorKnockbackChestplateModifierKey
        } else if(name.endsWith("LEGGINGS")) {
            equipmentSlotGroup = EquipmentSlotGroup.LEGS
            modifierKey = PluginKeys.armorLeggingsModifierKey
            modifierToughnessKey = PluginKeys.armorToughnessLeggingsModifierKey
            modifierKnockbackKey = PluginKeys.armorKnockbackLeggingsModifierKey
        }else {
            equipmentSlotGroup = EquipmentSlotGroup.FEET
            modifierKey = PluginKeys.armorBootsModifierKey
            modifierToughnessKey = PluginKeys.armorToughnessBootsModifierKey
            modifierKnockbackKey = PluginKeys.armorKnockbackBootsModifierKey
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

        meta.removeAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE)
        if(settings.knockbackResistance() != 0.0) {
            meta.addAttributeModifier(
                Attribute.GENERIC_KNOCKBACK_RESISTANCE, AttributeModifier(
                    modifierKnockbackKey,
                    settings.knockbackResistance(),
                    AttributeModifier.Operation.ADD_NUMBER,
                    equipmentSlotGroup
                )
            )
        }

        applyOtherAttributes(meta)
    }

    open fun applyOtherAttributes(meta: ItemMeta) { }
}