package me.zaksen.deathLabyrinth.menu.item

import me.zaksen.deathLabyrinth.item.CustomItem
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.AbstractItem

class TabItem(private val customItem: CustomItem): AbstractItem() {
    override fun getItemProvider(): ItemProvider {
        return ItemBuilder(customItem.asItemStack())
    }

    override fun handleClick(click: ClickType, player: Player, event: InventoryClickEvent) {
        player.inventory.addItem(customItem.asItemStack())
    }
}