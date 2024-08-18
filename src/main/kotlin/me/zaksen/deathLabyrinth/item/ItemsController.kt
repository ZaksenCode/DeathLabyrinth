package me.zaksen.deathLabyrinth.item

import me.zaksen.deathLabyrinth.item.weapon.weapons.HealStuff

object ItemsController {

    val itemsMap: MutableMap<String, CustomItem> = mutableMapOf()

    init {
        register("heal_stuff", HealStuff("heal_stuff"))
    }

    private fun register(id: String, item: CustomItem) {
        itemsMap[id] = item
    }

    fun get(id: String): CustomItem? {
        return itemsMap[id]
    }
}