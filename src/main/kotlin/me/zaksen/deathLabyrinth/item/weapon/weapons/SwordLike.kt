package me.zaksen.deathLabyrinth.item.weapon.weapons

import me.zaksen.deathLabyrinth.item.ability.ItemAbilityManager
import me.zaksen.deathLabyrinth.item.settings.ItemSettings
import me.zaksen.deathLabyrinth.item.weapon.WeaponItem
import me.zaksen.deathLabyrinth.item.weapon.WeaponType
import me.zaksen.deathLabyrinth.keys.PluginKeys
import me.zaksen.deathLabyrinth.util.*
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
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
            .loreLine(settings.quality().visualText.asText().font(Key.key("dl:icons")))
            .loreLine("item.lore.weapon.type".asTranslate(getWeaponType().displayName).color(TextColor.color(128, 0, 128)).decoration(
                TextDecoration.ITALIC, false))
            .loreMap(settings.lore())

        if(settings.abilities().isNotEmpty()) stack.loreLine(Component.translatable("text.item.abilities").decoration(
            TextDecoration.ITALIC, false).color(
            TextColor.color(
                222, 146, 47
            )
        ))

        settings.abilities().forEach {
            val ability = ItemAbilityManager.abilityMap[it] ?: return@forEach
            stack.loreLine(ability.name.decoration(TextDecoration.ITALIC, false).color(
                TextColor.color(
                    178, 91, 245
                )
            ))
            stack.loreLine(ability.description.decoration(TextDecoration.ITALIC, false).color(
                TextColor.color(
                    147, 63, 212
                )
            ))
        }

        val meta = stack.itemMeta
        meta.persistentDataContainer.set(PluginKeys.customItemKey, PersistentDataType.STRING, id)
        meta.persistentDataContainer.set(PluginKeys.customItemAbilitiesKey, PersistentDataType.STRING, settings.abilities().string())

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