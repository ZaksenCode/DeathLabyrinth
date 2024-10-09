package me.zaksen.deathLabyrinth.item.gear.armor

import me.zaksen.deathLabyrinth.item.CustomItem
import me.zaksen.deathLabyrinth.item.ItemType
import me.zaksen.deathLabyrinth.item.settings.ItemSettings
import me.zaksen.deathLabyrinth.keys.PluginKeys
import me.zaksen.deathLabyrinth.util.*
import net.kyori.adventure.key.Key
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

        if(name.endsWith("HELMET")) {
            equipmentSlotGroup = EquipmentSlotGroup.HEAD
        } else if(name.endsWith("CHESTPLATE")) {
            equipmentSlotGroup = EquipmentSlotGroup.CHEST
        } else if(name.endsWith("LEGGINGS")) {
            equipmentSlotGroup = EquipmentSlotGroup.LEGS
        }else {
            equipmentSlotGroup = EquipmentSlotGroup.FEET
        }

        // FIXME - Attributes didn't count. Applying only last item attributes
        meta.removeAttributeModifier(Attribute.GENERIC_ARMOR)
        if(settings.defence() != 0.0) {
            meta.addAttributeModifier(
                Attribute.GENERIC_ARMOR, AttributeModifier(
                    PluginKeys.customItemArmorKey,
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
                    PluginKeys.customItemArmorToughnessKey,
                    settings.thoroughness(),
                    AttributeModifier.Operation.ADD_NUMBER,
                    equipmentSlotGroup
                )
            )
        }
    }
}