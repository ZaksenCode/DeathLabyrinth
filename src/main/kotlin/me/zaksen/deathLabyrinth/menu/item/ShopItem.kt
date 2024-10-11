package me.zaksen.deathLabyrinth.menu.item

import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.trading.TradeOffer
import me.zaksen.deathLabyrinth.util.asText
import me.zaksen.deathLabyrinth.util.asTranslate
import me.zaksen.deathLabyrinth.util.toWrapper
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.AbstractItem

// FIXME - Selling didn't work
class ShopItem(private val offer: TradeOffer): AbstractItem() {

    override fun getItemProvider(): ItemProvider {
        return if(offer.count >= 1) {
            if(offer.buy) {
                ItemBuilder(offer.stack).setLore(offer.stack.lore()!!.map{ it.toWrapper() }).addLoreLines(
                    "<white>\uE000${offer.price}</white>".asText().font(Key.key("dl:icons")).toWrapper(),
                    "ui.shop.item_count".asTranslate(offer.count.toString().asText()).color(TextColor.color(255,165,0)).toWrapper()
                )
            } else {
                ItemBuilder(offer.stack).setLore(offer.stack.lore()!!.map{ it.toWrapper() }).addLoreLines(
                    "<white>\uE000${offer.price}</white>".asText().font(Key.key("dl:icons")).toWrapper()
                )
            }
        } else {
            if(offer.buy) {
                ItemBuilder(Material.BARRIER).setDisplayName(offer.stack.itemMeta.itemName().toWrapper())
                    .addLoreLines(
                        "ui.shop.item_count.empty.buy".asTranslate().color(TextColor.color(220,20,60)).toWrapper()
                    )
            } else {
                ItemBuilder(Material.BARRIER).setDisplayName(offer.stack.itemMeta.itemName().toWrapper())
                    .addLoreLines(
                        "ui.shop.item_count.empty.sell".asTranslate().color(TextColor.color(220,20,60)).toWrapper()
                    )
            }
        }
    }

    override fun handleClick(click: ClickType, player: Player, event: InventoryClickEvent) {
        EventManager.callPlayerTradeEvent(player, offer)
        notifyWindows()
    }
}