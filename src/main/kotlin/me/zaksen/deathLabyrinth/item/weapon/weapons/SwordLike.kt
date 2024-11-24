package me.zaksen.deathLabyrinth.item.weapon.weapons

import me.zaksen.deathLabyrinth.item.settings.ItemSettings
import me.zaksen.deathLabyrinth.item.weapon.WeaponItem
import me.zaksen.deathLabyrinth.item.weapon.WeaponType
import me.zaksen.deathLabyrinth.keys.PluginKeys
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.inventory.EquipmentSlotGroup
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

open class SwordLike(weaponType: WeaponType, id: String, settings: ItemSettings): WeaponItem(weaponType, id, settings) {

    override fun asItemStack(): ItemStack {
        val stack = super.asItemStack()

        val meta = stack.itemMeta

        meta.persistentDataContainer.set(PluginKeys.customItemDamageKey, PersistentDataType.DOUBLE, settings.damage())
        meta.persistentDataContainer.set(PluginKeys.customItemAttackSpeedKey, PersistentDataType.DOUBLE, settings.attackSpeed())

        if(settings.range() != 0.0) {
            meta.persistentDataContainer.set(PluginKeys.customItemRangeBlockKey, PersistentDataType.DOUBLE, settings.range())
            meta.persistentDataContainer.set(PluginKeys.customItemRangeEntityKey, PersistentDataType.DOUBLE, settings.range())
        }

        stack.itemMeta = meta

        stack.updateWeaponStats()

        return stack
    }
}

fun ItemStack.updateWeaponStats() {
    if(!hasItemMeta()) return

    val meta = itemMeta

    if(meta.persistentDataContainer.has(PluginKeys.customItemDamageKey)) {
        meta.removeAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE)

        meta.addAttributeModifier(
            Attribute.GENERIC_ATTACK_DAMAGE, AttributeModifier(
                PluginKeys.customItemDamageKey,
                meta.persistentDataContainer.get(PluginKeys.customItemDamageKey, PersistentDataType.DOUBLE)!!,
                AttributeModifier.Operation.ADD_NUMBER,
                EquipmentSlotGroup.MAINHAND
            )
        )
    }

    if(meta.persistentDataContainer.has(PluginKeys.customItemAttackSpeedKey)) {
        meta.removeAttributeModifier(Attribute.GENERIC_ATTACK_SPEED)

        meta.addAttributeModifier(
            Attribute.GENERIC_ATTACK_SPEED, AttributeModifier(
                PluginKeys.customItemAttackSpeedKey,
                meta.persistentDataContainer.get(PluginKeys.customItemAttackSpeedKey, PersistentDataType.DOUBLE)!!,
                AttributeModifier.Operation.ADD_NUMBER,
                EquipmentSlotGroup.MAINHAND
            )
        )
    }

    if(meta.persistentDataContainer.has(PluginKeys.customItemRangeBlockKey)) {
        meta.removeAttributeModifier(Attribute.PLAYER_BLOCK_INTERACTION_RANGE)

        meta.addAttributeModifier(
            Attribute.PLAYER_BLOCK_INTERACTION_RANGE, AttributeModifier(
                PluginKeys.customItemRangeBlockKey,
                meta.persistentDataContainer.get(PluginKeys.customItemRangeBlockKey, PersistentDataType.DOUBLE)!!,
                AttributeModifier.Operation.ADD_NUMBER,
                EquipmentSlotGroup.MAINHAND
            )
        )
    }

    if(meta.persistentDataContainer.has(PluginKeys.customItemRangeEntityKey)) {
        meta.removeAttributeModifier(Attribute.PLAYER_ENTITY_INTERACTION_RANGE)

        meta.addAttributeModifier(
            Attribute.PLAYER_ENTITY_INTERACTION_RANGE, AttributeModifier(
                PluginKeys.customItemRangeEntityKey,
                meta.persistentDataContainer.get(PluginKeys.customItemRangeEntityKey, PersistentDataType.DOUBLE)!!,
                AttributeModifier.Operation.ADD_NUMBER,
                EquipmentSlotGroup.MAINHAND
            )
        )
    }

    itemMeta = meta
}