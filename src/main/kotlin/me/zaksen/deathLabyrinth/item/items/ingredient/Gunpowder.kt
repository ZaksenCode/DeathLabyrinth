package me.zaksen.deathLabyrinth.item.items.ingredient

import me.zaksen.deathLabyrinth.entity.trader.TraderType
import me.zaksen.deathLabyrinth.item.CustomItem
import me.zaksen.deathLabyrinth.item.ItemType
import me.zaksen.deathLabyrinth.item.settings.ItemSettings
import me.zaksen.deathLabyrinth.util.asTranslate
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material

class Gunpowder(id: String): CustomItem(id, ItemType.MISC,
    ItemSettings(Material.GUNPOWDER)
        .displayName("item.gunpowder.name".asTranslate())
        .loreLine("item.gunpowder.lore.0".asTranslate().color(TextColor.color(128, 0, 128)))
        .tradePrice(30)
        .addAviableTrader(TraderType.ALCHEMIST)
) {
}