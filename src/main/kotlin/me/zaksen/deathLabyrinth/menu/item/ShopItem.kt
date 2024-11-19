package me.zaksen.deathLabyrinth.menu.item

import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.trading.TradeOffer
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.impl.AbstractItem

class ShopItem(private val offer: TradeOffer): AbstractItem() {

    override fun getItemProvider(): ItemProvider {
        return offer.displayItem()
    }

    override fun handleClick(click: ClickType, player: Player, event: InventoryClickEvent) {
        EventManager.callPlayerTradeEvent(player, offer)
        notifyWindows()
    }
}