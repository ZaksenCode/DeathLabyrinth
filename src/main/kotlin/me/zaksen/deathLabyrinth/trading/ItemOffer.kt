package me.zaksen.deathLabyrinth.trading

import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.util.asText
import me.zaksen.deathLabyrinth.util.asTranslate
import me.zaksen.deathLabyrinth.util.toWrapper
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder

class ItemOffer(
    count: Int = 1,
    price: Int = 10,
    val stack: ItemStack = ItemStack(Material.AIR),
    buy: Boolean = true
): TradeOffer(count, price, buy) {
    override fun buy(player: Player) {
        val playerData = GameController.players[player] ?: return

        if(playerData.money >= this.price && this.count >= 1) {
            player.inventory.addItem(this.stack)
            playerData.money -= this.price
            GameController.players[player] = playerData
            this.count--
        }
    }

    override fun sell(player: Player) {
        val playerData = GameController.players[player] ?: return

        if(player.inventory.contains(this.stack) && this.count >= 1) {
            player.inventory.removeItem(this.stack)
            playerData.money += this.price
            GameController.players[player] = playerData
            this.count--
        }
    }

    override fun displayItem(): ItemProvider {
        return if(this.count >= 1) {
            if(this.buy) {
                ItemBuilder(this.stack).setLore(this.stack.lore()!!.map{ it.toWrapper() }).addLoreLines(
                    "<white>\uE000${this.price}</white>".asText().font(Key.key("dl:icons")).toWrapper(),
                    "ui.shop.item_count".asTranslate(this.count.toString().asText()).color(TextColor.color(255,165,0)).toWrapper()
                )
            } else {
                ItemBuilder(this.stack).setLore(this.stack.lore()!!.map{ it.toWrapper() }).addLoreLines(
                    "<white>\uE000${this.price}</white>".asText().font(Key.key("dl:icons")).toWrapper()
                )
            }
        } else {
            if(this.buy) {
                ItemBuilder(Material.BARRIER).setDisplayName(this.stack.itemMeta.itemName().toWrapper())
                    .addLoreLines(
                        "ui.shop.item_count.empty.buy".asTranslate().color(TextColor.color(220,20,60)).toWrapper()
                    )
            } else {
                ItemBuilder(Material.BARRIER).setDisplayName(this.stack.itemMeta.itemName().toWrapper())
                    .addLoreLines(
                        "ui.shop.item_count.empty.sell".asTranslate().color(TextColor.color(220,20,60)).toWrapper()
                    )
            }
        }
    }
}