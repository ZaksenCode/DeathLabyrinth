package me.zaksen.deathLabyrinth.menu.custom

import me.zaksen.deathLabyrinth.item.CustomItem
import me.zaksen.deathLabyrinth.item.ItemsController
import me.zaksen.deathLabyrinth.menu.api.Menu
import me.zaksen.deathLabyrinth.menu.api.MenuType
import me.zaksen.deathLabyrinth.util.asText

class ItemTabMenu(page: Int): Menu(MenuType.BASE_54, "Список предметов".asText()) {

    init {
        val startIndex = 0
        val endIndex = 53

        val items: MutableList<CustomItem> = mutableListOf()

        for(item in ItemsController.itemsMap) {
            items.add(item.value)
        }

        for(i in startIndex..endIndex) {
            val index = i + (page * 54)

            if(index >= items.size) {
                break
            }

            val item = items[index]

            setItem(i, item.asItemStack()) {
                it.whoClicked.setItemOnCursor(item.asItemStack())
            }
        }

        updateInventory()
    }
}