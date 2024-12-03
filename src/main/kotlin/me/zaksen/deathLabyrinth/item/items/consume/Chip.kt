package me.zaksen.deathLabyrinth.item.items.consume

import me.zaksen.deathLabyrinth.item.CustomItem
import me.zaksen.deathLabyrinth.item.ItemQuality
import me.zaksen.deathLabyrinth.item.ItemType
import me.zaksen.deathLabyrinth.item.settings.ItemSettings
import me.zaksen.deathLabyrinth.util.asTranslate
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material

class Chip(id: String): CustomItem(id, ItemType.CONSUMABLE,
    ItemSettings(Material.PAPER)
        .customModel(1501)
        .displayName("item.chip.name".asTranslate().color(TextColor.color(255,20,147)))
        .quality(ItemQuality.LEGENDARY)
        .tradePrice(150)
        .ability("gold_rush")
)