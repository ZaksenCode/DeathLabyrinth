package me.zaksen.deathLabyrinth.item.items.consume

import me.zaksen.deathLabyrinth.entity.trader.TraderType
import me.zaksen.deathLabyrinth.item.CustomItem
import me.zaksen.deathLabyrinth.item.ItemType
import me.zaksen.deathLabyrinth.item.settings.ItemSettings
import me.zaksen.deathLabyrinth.keys.PluginKeys
import me.zaksen.deathLabyrinth.util.*
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.event.entity.EntityRegainHealthEvent
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

class SmallHealPotion(id: String): CustomItem(id, ItemType.CONSUMABLE,
    ItemSettings(Material.POTION)
        .customModel(101)
        .displayName("item.small_heal_potion.name".asTranslate().color(TextColor.color(255,20,147)))
        .lore(mutableListOf(
            "item.small_heal_potion.lore.0".asTranslate().color(TextColor.color(128, 0, 128)),
            "item.small_heal_potion.lore.1".asTranslate().color(TextColor.color(65,105,225))
        ))
        .tradePrice(25)
        .addAviableTrader(TraderType.NORMAL)
        .ability("small_heal_effect")
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

        return stack
    }
}