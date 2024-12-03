package me.zaksen.deathLabyrinth.menu.item

import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.trading.pricing.PricingStrategies
import me.zaksen.deathLabyrinth.util.asText
import me.zaksen.deathLabyrinth.util.asTranslate
import me.zaksen.deathLabyrinth.util.toWrapper
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import xyz.xenondevs.inventoryaccess.component.ComponentWrapper
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.AbstractItem

class ReviewItem: AbstractItem() {
    override fun getItemProvider(): ItemProvider {
        val price = PricingStrategies.DEFAULT.strategy.scale(50)
        return ItemBuilder(Material.END_CRYSTAL).setDisplayName("text.review_player.name".asTranslate().color(TextColor.color(244, 247, 141)).toWrapper())
            .addLoreLines("text.review_player.description".asTranslate("$price".asText()).color(TextColor.color(216, 219, 112)).toWrapper())
            .addLoreLines("text.review_player.description.players".asTranslate().color(TextColor.color(216, 219, 112)).toWrapper())
            .addLoreLines(playersList())
    }

    private fun playersList(): List<ComponentWrapper> {
        val result = mutableListOf<ComponentWrapper>()

        GameController.getDeadPlayers().forEach {
            result.add(" - ".asText().color(TextColor.color(128, 187, 80)).append(it.key.displayName().color(TextColor.color(160, 219, 112))).toWrapper())
        }

        return result
    }

    override fun handleClick(click: ClickType, player: Player, event: InventoryClickEvent) {
        val data = GameController.players[player]
        val halfHealth = player.health / 2
        val price = PricingStrategies.DEFAULT.strategy.scale(50)

        if(data == null) return

        if(GameController.hasDeadPlayers() && data.money >= price) {
            val revivePlayer = GameController.getDeadPlayers().keys.first()

            player.damage(halfHealth)
            revivePlayer.health = halfHealth

            GameController.removeMoney(player, price)

            GameController.revivePlayer(revivePlayer, player)
        }
    }

}