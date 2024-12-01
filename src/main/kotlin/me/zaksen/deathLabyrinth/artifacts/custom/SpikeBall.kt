package me.zaksen.deathLabyrinth.artifacts.custom

import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import me.zaksen.deathLabyrinth.artifacts.api.ArtifactRarity
import me.zaksen.deathLabyrinth.event.custom.WorldTickEvent
import me.zaksen.deathLabyrinth.event.custom.game.PlayerDamageEntityEvent
import me.zaksen.deathLabyrinth.util.*
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class SpikeBall: Artifact(
    "artifact.spike_ball.name".asTranslate().color(TextColor.color(50,205,50)),
    ArtifactRarity.COMMON
) {

    init {
        abilityContainer.add {
            if(it !is PlayerDamageEntityEvent) return@add
            if(it.player.uniqueId != ownerUuid) return@add

            if(it.player.location.distance(it.damaged.location) < (6.0 + (0.25 * count))) {
                it.damage += it.damage * (0.05 * count)
            }
        }
        abilityContainer.add {
            if(it !is WorldTickEvent) return@add
            if(ownerUuid == null) return@add
            val player = Bukkit.getPlayer(ownerUuid!!) ?: return@add

            if(player.gameMode != GameMode.SPECTATOR) {
                drawCircle(
                    location = player.location.add(0.0, 0.75, 0.0),
                    size = (6.0 + (0.25 * count)),
                    color = Color.RED,
                    particleSize = 0.5f
                )
            }
        }
    }

    override fun asItemStack(): ItemStack {
        return ItemStack(Material.APPLE)
            .customModel(117)
            .name(name)
            .loreLine("artifact.spike_ball.lore.0".asTranslate(
                "${6 + (0.25 * count)}".asText().color(TextColor.color(255,165,0)),
                "${5 * count}%".asText().color(TextColor.color(255,165,0))
            ))
    }
}