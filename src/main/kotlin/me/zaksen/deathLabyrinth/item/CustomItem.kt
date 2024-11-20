package me.zaksen.deathLabyrinth.item

import me.zaksen.deathLabyrinth.item.ability.ItemAbilityManager
import me.zaksen.deathLabyrinth.item.settings.ItemSettings
import me.zaksen.deathLabyrinth.keys.PluginKeys
import me.zaksen.deathLabyrinth.util.*
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

open class CustomItem(val id: String, val type: ItemType, val settings: ItemSettings) {

    open fun asItemStack(): ItemStack {
        val stack = ItemStack(settings.material)
            .customModel(settings.customModel())
            .name(settings.displayName())
            .loreLine(settings.quality().visualText.asText().font(Key.key("dl:icons")))
            .loreMap(settings.lore())

        val meta = stack.itemMeta
        meta.persistentDataContainer.set(PluginKeys.customItemKey, PersistentDataType.STRING, id)
        meta.persistentDataContainer.set(PluginKeys.customItemAbilitiesKey, PersistentDataType.STRING, settings.abilities().string())

        if(settings.abilityCooldown() != 0) {
            meta.persistentDataContainer.set(PluginKeys.customItemCooldownTimeKey, PersistentDataType.INTEGER, settings.abilityCooldown())
        }

        meta.isUnbreakable = true
        stack.itemMeta = meta

        if(settings.abilities().isNotEmpty()) stack.loreLine(Component.translatable("text.item.abilities").decoration(TextDecoration.ITALIC, false).color(
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
            stack.loreLine(Component.text(" - ").append(ability.description.decoration(TextDecoration.ITALIC, false).color(
                TextColor.color(
                    147, 63, 212
                )
            )))
        }

        if(settings.abilityCooldown() != 0) {
            stack.loreLine("item.lore.cooldown".asTranslate(
                (settings.abilityCooldown() / 1000.0).toString().asText()).color(TextColor.color(65,105,225))
            )
        }

        return stack
    }

    fun checkCooldown(item: ItemStack): Boolean {
        if(item.itemMeta.persistentDataContainer.has(PluginKeys.customItemCooldownKey)) {
            val cooldown = item.itemMeta.persistentDataContainer.get(PluginKeys.customItemCooldownKey, PersistentDataType.LONG)
            val maxCooldown = item.itemMeta.persistentDataContainer.get(PluginKeys.customItemCooldownTimeKey, PersistentDataType.INTEGER)

            return cooldown != null && maxCooldown != null && System.currentTimeMillis() - cooldown >= maxCooldown
        } else {
            val meta = item.itemMeta
            meta.persistentDataContainer.set(PluginKeys.customItemCooldownKey, PersistentDataType.LONG, System.currentTimeMillis())
            item.setItemMeta(meta)
            return true
        }
    }

    fun checkAndUpdateCooldown(item: ItemStack): Boolean {
        if(item.itemMeta.persistentDataContainer.has(PluginKeys.customItemCooldownKey)) {
            val cooldown = item.itemMeta.persistentDataContainer.get(PluginKeys.customItemCooldownKey, PersistentDataType.LONG)
            val maxCooldown = item.itemMeta.persistentDataContainer.get(PluginKeys.customItemCooldownTimeKey, PersistentDataType.INTEGER)!!

            if(cooldown != null && System.currentTimeMillis() - cooldown >= maxCooldown) {
                val meta = item.itemMeta
                meta.persistentDataContainer.set(PluginKeys.customItemCooldownKey, PersistentDataType.LONG, System.currentTimeMillis())
                item.setItemMeta(meta)
                return true
            } else {
                return false
            }
        } else {
            val meta = item.itemMeta
            meta.persistentDataContainer.set(PluginKeys.customItemCooldownKey, PersistentDataType.LONG, System.currentTimeMillis())
            item.setItemMeta(meta)
            return true
        }
    }
}