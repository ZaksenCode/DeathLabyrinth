package me.zaksen.deathLabyrinth.item.items.consume

import me.zaksen.deathLabyrinth.item.CustomItem
import me.zaksen.deathLabyrinth.item.ItemQuality
import me.zaksen.deathLabyrinth.item.ItemType
import me.zaksen.deathLabyrinth.item.ability.ItemAbilityManager
import me.zaksen.deathLabyrinth.item.settings.ItemSettings
import me.zaksen.deathLabyrinth.keys.PluginKeys
import me.zaksen.deathLabyrinth.util.*
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.event.entity.EntityRegainHealthEvent
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

class HealPotion(id: String): CustomItem(id, ItemType.CONSUMABLE,
    ItemSettings(Material.POTION)
        .customModel(100)
        .displayName("item.heal_potion.name".asTranslate().color(TextColor.color(255,20,147)))
        .quality(ItemQuality.UNCOMMON)
        .tradePrice(40)
        .ability("heal_effect")
) {

    override fun asItemStack(): ItemStack {
        val stack = ItemStack(settings.material)
            .customModel(settings.customModel())
            .name(settings.displayName())
            .loreLine(settings.quality().visualText.asText().font(Key.key("dl:icons")))
            .loreMap(settings.lore())

        val meta = stack.itemMeta
        meta.persistentDataContainer.set(PluginKeys.customItemKey, PersistentDataType.STRING, id)
        meta.persistentDataContainer.set(PluginKeys.customItemAbilitiesKey, PersistentDataType.STRING, settings.abilities().string())
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
        stack.itemMeta = meta

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


        return stack
    }
}