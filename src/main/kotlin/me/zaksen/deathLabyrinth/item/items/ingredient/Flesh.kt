package me.zaksen.deathLabyrinth.item.items.ingredient

import me.zaksen.deathLabyrinth.item.CustomItem
import me.zaksen.deathLabyrinth.item.ItemType
import me.zaksen.deathLabyrinth.item.settings.ItemSettings
import me.zaksen.deathLabyrinth.util.asText
import me.zaksen.deathLabyrinth.util.asTranslate
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material

class Flesh(id: String): CustomItem(id, ItemType.DRINK_INGREDIENT,
    ItemSettings(Material.ROTTEN_FLESH)
        .displayName("item.flesh.name".asTranslate())
        .loreLine("item.flesh.lore.0".asTranslate().color(TextColor.color(128, 0, 128)))
) {
}