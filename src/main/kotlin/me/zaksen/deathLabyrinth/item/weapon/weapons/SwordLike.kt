package me.zaksen.deathLabyrinth.item.weapon.weapons

import me.zaksen.deathLabyrinth.item.settings.ItemSettings
import me.zaksen.deathLabyrinth.item.weapon.WeaponItem
import me.zaksen.deathLabyrinth.item.weapon.WeaponType
import me.zaksen.deathLabyrinth.keys.PluginKeys
import me.zaksen.deathLabyrinth.util.*
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.inventory.EquipmentSlotGroup
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

open class SwordLike(weaponType: WeaponType, id: String, settings: ItemSettings): WeaponItem(weaponType, id, settings) {

    override fun asItemStack(): ItemStack {
        val stack = ItemStack(settings.material)
            .customModel(settings.customModel())
            .name(settings.displayName())
            .loreMap(settings.lore())
            .loreLine("<dark_purple>Тип:</dark_purple> <light_purple>${getWeaponType().displayName}</light_purple>".asText())

        val meta = stack.itemMeta
        meta.persistentDataContainer.set(PluginKeys.customItemKey, PersistentDataType.STRING, id)

        meta.addAttributeModifier(
            Attribute.GENERIC_ATTACK_DAMAGE, AttributeModifier(
                PluginKeys.customItemDamageKey,
                settings.damage(),
                AttributeModifier.Operation.ADD_NUMBER,
                EquipmentSlotGroup.MAINHAND
            )
        )

        meta.addAttributeModifier(
            Attribute.GENERIC_ATTACK_SPEED, AttributeModifier(
                PluginKeys.customItemAttackSpeedKey,
                settings.attackSpeed(),
                AttributeModifier.Operation.ADD_NUMBER,
                EquipmentSlotGroup.MAINHAND
            )
        )

        if(settings.range() != 0.0) {
            meta.addAttributeModifier(
                Attribute.PLAYER_BLOCK_INTERACTION_RANGE, AttributeModifier(
                    PluginKeys.customItemRangeBlockKey,
                    settings.range(),
                    AttributeModifier.Operation.ADD_NUMBER,
                    EquipmentSlotGroup.MAINHAND
                )
            )

            meta.addAttributeModifier(
                Attribute.PLAYER_ENTITY_INTERACTION_RANGE, AttributeModifier(
                    PluginKeys.customItemRangeEntityKey,
                    settings.range(),
                    AttributeModifier.Operation.ADD_NUMBER,
                    EquipmentSlotGroup.MAINHAND
                )
            )
        }

        meta.isUnbreakable = true
        stack.itemMeta = meta

        return stack
    }
}