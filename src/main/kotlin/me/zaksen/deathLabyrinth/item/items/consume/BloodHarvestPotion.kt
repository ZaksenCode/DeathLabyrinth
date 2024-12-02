package me.zaksen.deathLabyrinth.item.items.consume

import me.zaksen.deathLabyrinth.item.CustomItem
import me.zaksen.deathLabyrinth.item.ItemQuality
import me.zaksen.deathLabyrinth.item.ItemType
import me.zaksen.deathLabyrinth.item.settings.ItemSettings
import me.zaksen.deathLabyrinth.util.asTranslate
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material

class BloodHarvestPotion(id: String): CustomItem(id, ItemType.CONSUMABLE,
    ItemSettings(Material.HONEY_BOTTLE)
        .customModel(102)
        .displayName("item.blood_harvest_potion.name".asTranslate().color(TextColor.color(255,20,147)))
        .quality(ItemQuality.FANTASIC)
        .tradePrice(300)
        .ability("summon_artifacts")
)