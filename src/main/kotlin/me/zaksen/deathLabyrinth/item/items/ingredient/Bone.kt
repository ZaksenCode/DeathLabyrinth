package me.zaksen.deathLabyrinth.item.items.ingredient

import me.zaksen.deathLabyrinth.item.CustomItem
import me.zaksen.deathLabyrinth.item.ItemType
import me.zaksen.deathLabyrinth.item.settings.ItemSettings
import me.zaksen.deathLabyrinth.util.asText
import org.bukkit.Material

class Bone(id: String): CustomItem(id, ItemType.DRINK_INGREDIENT,
    ItemSettings(Material.BONE).displayName("Кость".asText())
        .loreLine("Жаль что скелеты не умеют шутить...".asText())
) {
}