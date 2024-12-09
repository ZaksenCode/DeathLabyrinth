package me.zaksen.deathLabyrinth.game.room

import me.zaksen.deathLabyrinth.util.asTranslate
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

enum class LocationType(val floor: Int, val displayItem: ItemStack, val displayName: Component, val startLength: Int) {
    FOREST(1, ItemStack.of(Material.SPRUCE_SAPLING), "choice.location.forest.name".asTranslate(), 3),
    SHAFT(1, ItemStack.of(Material.IRON_PICKAXE), "choice.location.shaft.name".asTranslate(), 3),
    DUNGEON(2, ItemStack.of(Material.STONE_BRICKS), "choice.location.dungeon.name".asTranslate(), 4),
    END(3, ItemStack.of(Material.ENDER_PEARL), "choice.location.end.name".asTranslate(), 5),
    NETHER(3, ItemStack.of(Material.NETHER_WART), "choice.location.nether.name".asTranslate(), 5);

    companion object {
        fun getLocationFor(floor: Int): LocationType? {
            return entries.filter { it.floor == floor }.randomOrNull()
        }

        fun getLocationsFor(floor: Int): List<LocationType> {
            return entries.filter { it.floor == floor }
        }
    }
}