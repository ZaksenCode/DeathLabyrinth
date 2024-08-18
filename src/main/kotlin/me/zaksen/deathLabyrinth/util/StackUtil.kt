package me.zaksen.deathLabyrinth.util

import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

fun ItemStack.name(name: Component): ItemStack {
    val meta: ItemMeta = this.itemMeta
    meta.itemName(name)
    itemMeta = meta
    return this
}

fun ItemStack.loreMap(lore: List<Component>): ItemStack {
    val meta: ItemMeta = this.itemMeta
    meta.lore(lore)
    itemMeta = meta
    return this
}

fun ItemStack.customModel(model: Int): ItemStack {
    val meta: ItemMeta = this.itemMeta
    meta.setCustomModelData(model)
    itemMeta = meta
    return this
}