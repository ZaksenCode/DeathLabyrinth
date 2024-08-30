package me.zaksen.deathLabyrinth.item.items.ingredient

import me.zaksen.deathLabyrinth.item.CustomItem
import me.zaksen.deathLabyrinth.item.ItemType
import me.zaksen.deathLabyrinth.item.settings.ItemSettings
import me.zaksen.deathLabyrinth.util.asText
import org.bukkit.Material

class Gunpowder(id: String): CustomItem(id, ItemType.MISC,
    ItemSettings(Material.GUNPOWDER).displayName("Порох".asText())
        .loreLine("Не подносите к нему спичку!".asText())
) {
}