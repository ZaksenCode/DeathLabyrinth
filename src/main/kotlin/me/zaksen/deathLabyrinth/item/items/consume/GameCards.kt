package me.zaksen.deathLabyrinth.item.items.consume

import me.zaksen.deathLabyrinth.item.CustomItem
import me.zaksen.deathLabyrinth.item.ItemQuality
import me.zaksen.deathLabyrinth.item.ItemType
import me.zaksen.deathLabyrinth.item.settings.ItemSettings
import me.zaksen.deathLabyrinth.util.asTranslate
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material

class GameCards(id: String): CustomItem(id, ItemType.CONSUMABLE,
    ItemSettings(Material.PAPER)
        .customModel(1500)
        .displayName("item.game_cards.name".asTranslate().color(TextColor.color(255,20,147)))
        .quality(ItemQuality.EPIC)
        .tradePrice(150)
        .ability("packing")
)