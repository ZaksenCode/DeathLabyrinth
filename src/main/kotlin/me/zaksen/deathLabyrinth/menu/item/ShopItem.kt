package me.zaksen.deathLabyrinth.menu.item

import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.trading.TradeOffer
import me.zaksen.deathLabyrinth.util.toWrapper
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.AbstractItem

class ShopItem(private val offer: TradeOffer): AbstractItem() {

    private var price = offer.pricingStrategy.scale(offer.price)
    private var count = offer.count

    override fun getItemProvider(): ItemProvider {
        return if(count >= 1) {
            ItemBuilder(offer.stack).addLoreLines(
                "<gold>Цена: ${price}</gold>".toWrapper(),
                "<gold>Осталось: ${count}</gold>".toWrapper()
            )
        } else {
            ItemBuilder(offer.stack).setMaterial(Material.BARRIER).addLoreLines("<red>Нет в наличии</red>".toWrapper())
        }
    }

    override fun handleClick(click: ClickType, player: Player, event: InventoryClickEvent) {
        val playerData = GameController.players[player] ?: return

        if(playerData.money >= price && count >= 1) {
            player.inventory.addItem(offer.stack)
            playerData.money -= price
            count--
        }
    }
}