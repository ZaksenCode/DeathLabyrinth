package me.zaksen.deathLabyrinth.item.weapon

import me.zaksen.deathLabyrinth.item.CustomItem
import me.zaksen.deathLabyrinth.item.ItemType
import me.zaksen.deathLabyrinth.item.ability.ItemAbilityManager
import me.zaksen.deathLabyrinth.item.settings.ItemSettings
import me.zaksen.deathLabyrinth.item.showItemReadyCooldown
import me.zaksen.deathLabyrinth.keys.PluginKeys
import me.zaksen.deathLabyrinth.util.*
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

// TODO - Make items stats dynamic
open class WeaponItem(private val weaponType: WeaponType, id: String, settings: ItemSettings): CustomItem(id, ItemType.WEAPON, settings) {

    fun getWeaponType(): WeaponType {
        return weaponType
    }

    override fun asItemStack(): ItemStack {
        val stack = ItemStack(settings.material)
            .customModel(settings.customModel())
            .name(settings.displayName())
            .loreLine(settings.quality().visualText.asText().font(Key.key("dl:icons")))
            .loreLine("item.lore.weapon.type".asTranslate(getWeaponType().displayName).color(TextColor.color(128, 0, 128)).decoration(
                TextDecoration.ITALIC, false))
            .loreMap(settings.lore())

        if(settings.abilities().isNotEmpty()) stack.loreLine(Component.translatable("text.item.abilities").decoration(TextDecoration.ITALIC, false).color(
            TextColor.color(
                222, 146, 47
            )
        ))

        settings.abilities().forEach {
            val ability = ItemAbilityManager.abilityMap[it] ?: return@forEach
            ability.addAbilityDescription(stack)
        }

        val meta = stack.itemMeta
        meta.persistentDataContainer.set(PluginKeys.customItemKey, PersistentDataType.STRING, id)
        meta.persistentDataContainer.set(PluginKeys.customItemAbilitiesKey, PersistentDataType.STRING, settings.abilities().string())

        if(settings.abilityCooldown() != 0) {
            meta.persistentDataContainer.set(PluginKeys.customItemCooldownTimeKey, PersistentDataType.INTEGER, settings.abilityCooldown())
        }

        meta.isUnbreakable = true
        stack.itemMeta = meta

        if(settings.abilityCooldown() != 0) {
            stack.loreLine("item.lore.cooldown".asTranslate(
                (settings.abilityCooldown() / 1000.0).toString().asText()).color(TextColor.color(65,105,225))
            )
        }

        return stack
    }
}