package me.zaksen.deathLabyrinth.artifacts.custom

import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import me.zaksen.deathLabyrinth.artifacts.api.ArtifactRarity
import me.zaksen.deathLabyrinth.event.custom.game.PlayerKillEntityEvent
import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.util.*
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class Token: Artifact(
    "artifact.token.name".asTranslate().color(TextColor.color(50,205,50)),
    ArtifactRarity.RARE
) {

    init {
        abilityContainer.add {
            if(it !is PlayerKillEntityEvent) return@add
            val player = it.player

            if (player != null) {
                val playerData = GameController.players[player] ?: return@add

                playerData.money += 3 * count
                GameController.players[player] = playerData
            }
        }
    }

    override fun asItemStack(): ItemStack {
        return ItemStack(Material.APPLE)
            .customModel(107)
            .name(name)
            .loreLine("artifact.token.lore.0".asTranslate(
                "${3 * count}".asText().color(TextColor.color(255,165,0))
            ))
    }
}