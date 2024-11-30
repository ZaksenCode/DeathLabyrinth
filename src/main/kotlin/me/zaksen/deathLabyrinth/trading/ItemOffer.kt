package me.zaksen.deathLabyrinth.trading

import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.keys.PluginKeys
import me.zaksen.deathLabyrinth.util.asText
import me.zaksen.deathLabyrinth.util.asTranslate
import me.zaksen.deathLabyrinth.util.toWrapper
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
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

        if(this.count >= 1 && player.removeItem(this.stack)) {
            playerData.money += this.price
            GameController.players[player] = playerData
            this.count--
        }
    }

    private fun Player.removeItem(stack: ItemStack): Boolean {
        val itemSlot = getItemSlot(stack)

        if(itemSlot < 0) {
            return false
        }

        val item = inventory.getItem(itemSlot) ?: return false
        item.subtract()
        return true
    }

    private fun Player.getItemSlot(stack: ItemStack): Int {
        this.inventory.contents.forEachIndexed { slot, item ->
            if(item != null && item.itemMeta.customModelData == stack.itemMeta.customModelData && item.type == stack.type) {
                if(item.itemMeta.persistentDataContainer.has(PluginKeys.customItemKey) || stack.itemMeta.persistentDataContainer.has(PluginKeys.customItemKey)) {
                    val itemPers = item.itemMeta.persistentDataContainer
                    val stackPers = stack.itemMeta.persistentDataContainer
                    if(itemPers.get(PluginKeys.customItemKey, PersistentDataType.STRING)!! == stackPers.get(PluginKeys.customItemKey, PersistentDataType.STRING)!!) {
                        return slot
                    }
                }
            }

        }

        return -1
    }

    override fun displayItem(): ItemProvider {
        return if(this.count >= 1) {
            if(this.buy) {
                ItemBuilder(this.stack).setLore(this.stack.lore()!!.map{ it.toWrapper() }).addLoreLines(
                    "<white>\uE000${this.price}</white>".asText().font(Key.key("dl:icons")).toWrapper(),
                    "ui.shop.item_count.buy".asTranslate(this.count.toString().asText()).color(TextColor.color(255,165,0)).toWrapper()
                )
            } else {
                ItemBuilder(this.stack).setLore(this.stack.lore()!!.map{ it.toWrapper() }).addLoreLines(
                    "<white>\uE000${this.price}</white>".asText().font(Key.key("dl:icons")).toWrapper(),
                    "ui.shop.item_count.sell".asTranslate(this.count.toString().asText()).color(TextColor.color(255,165,0)).toWrapper()
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