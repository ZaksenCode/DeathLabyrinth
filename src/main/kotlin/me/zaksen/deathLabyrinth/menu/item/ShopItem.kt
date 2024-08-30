package me.zaksen.deathLabyrinth.menu.item

import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.trading.TradeOffer
import me.zaksen.deathLabyrinth.util.asText
import me.zaksen.deathLabyrinth.util.toWrapper
import net.kyori.adventure.key.Key
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.AbstractItem

class ShopItem(private val offer: TradeOffer): AbstractItem() {

    override fun getItemProvider(): ItemProvider {
        return if(offer.count >= 1) {
            if(offer.buy) {
                ItemBuilder(offer.stack).setLore(offer.stack.lore()!!.map{ it.toWrapper() }).addLoreLines(
                    "<white>\uE000${offer.price}</white>".asText().font(Key.key("dl:icons")).toWrapper(),
                    "<gold>В наличии: ${offer.count}</gold>".toWrapper()
                )
            } else {
                ItemBuilder(offer.stack).setLore(offer.stack.lore()!!.map{ it.toWrapper() }).addLoreLines(
                    "<white>\uE000${offer.price}</white>".asText().font(Key.key("dl:icons")).toWrapper()
                )
            }
        } else {
            if(offer.buy) {
                ItemBuilder(Material.BARRIER).setDisplayName(offer.stack.itemMeta.itemName().toWrapper())
                    .addLoreLines("<red>Нет в наличии</red>".toWrapper())
            } else {
                ItemBuilder(Material.BARRIER).setDisplayName(offer.stack.itemMeta.itemName().toWrapper())
                    .addLoreLines("<red>Больше не принимается</red>".toWrapper())
            }
        }
    }

    override fun handleClick(click: ClickType, player: Player, event: InventoryClickEvent) {
        val playerData = GameController.players[player] ?: return

        if(offer.buy) {
            if(playerData.money >= offer.price && offer.count >= 1) {
                player.inventory.addItem(offer.stack)
                playerData.money -= offer.price
                GameController.players[player] = playerData
                offer.count--
            }
        } else {
            if(player.inventory.contains(offer.stack) && offer.count >= 1) {
                player.inventory.removeItem(offer.stack)
                playerData.money += offer.price
                GameController.players[player] = playerData
                offer.count--
            }
        }

        notifyWindows()
    }
}