package me.zaksen.deathLabyrinth.trading

import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import me.zaksen.deathLabyrinth.artifacts.custom.GreenHeart
import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.util.asText
import me.zaksen.deathLabyrinth.util.asTranslate
import me.zaksen.deathLabyrinth.util.toWrapper
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.entity.Player
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder

/**
 * @param artifact should be unique instance
 */
class ArtifactOffer(
    count: Int = 1,
    price: Int = 10,
    val artifact: Artifact = GreenHeart()
): TradeOffer(count, price, true) {
    override fun buy(player: Player) {
        val playerData = GameController.players[player] ?: return

        if(playerData.money >= this.price && this.count >= 1) {
            playerData.addArtifact(artifact)
        }
    }

    override fun sell(player: Player) {
        println("WARN - Artifacts selling didn't support!")
    }

    override fun displayItem(): ItemProvider {
        val artifactItem = artifact.asItemStack()
        return if(this.count >= 1) {
            if(this.buy) {
                ItemBuilder(artifactItem).setLore(artifactItem.lore()!!.map { it.toWrapper() }).addLoreLines(
                    "<white>\uE000${this.price}</white>".asText().font(Key.key("dl:icons")).toWrapper(),
                    "ui.shop.item_count".asTranslate(this.count.toString().asText()).color(TextColor.color(255,165,0)).toWrapper()
                )
            } else {
                ItemBuilder(artifactItem).setLore(artifactItem.lore()!!.map { it.toWrapper() }).addLoreLines(
                    "<white>\uE000${this.price}</white>".asText().font(Key.key("dl:icons")).toWrapper()
                )
            }
        } else {
            if(this.buy) {
                ItemBuilder(Material.BARRIER).setDisplayName(artifactItem.itemMeta.itemName().toWrapper())
                    .addLoreLines(
                        "ui.shop.item_count.empty.buy".asTranslate().color(TextColor.color(220,20,60)).toWrapper()
                    )
            } else {
                ItemBuilder(Material.BARRIER).setDisplayName(artifactItem.itemMeta.itemName().toWrapper())
                    .addLoreLines(
                        "ui.shop.item_count.empty.sell".asTranslate().color(TextColor.color(220,20,60)).toWrapper()
                    )
            }
        }
    }
}