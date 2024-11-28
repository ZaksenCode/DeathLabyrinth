package me.zaksen.deathLabyrinth.artifacts.custom

import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import me.zaksen.deathLabyrinth.artifacts.api.ArtifactRarity
import me.zaksen.deathLabyrinth.event.custom.game.PlayerPostPickupArtifactEvent
import me.zaksen.deathLabyrinth.util.*
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class HolyMantle: Artifact(
    "artifact.holy_mantle.name".asTranslate().color(TextColor.color(50,205,50)),
    ArtifactRarity.COMMON
) {

    init {
        abilityContainer.add {
            if(it !is PlayerPostPickupArtifactEvent) return@add
            if(it.player.uniqueId != ownerUuid) return@add

            val player = it.player
            player.setCountingAbsorptionLevel(4 * count)
        }
    }

    override fun asItemStack(): ItemStack {
        return ItemStack(Material.APPLE)
            .customModel(114)
            .name(name)
            .loreLine("artifact.holy_mantle.lore.0".asTranslate(
                "${4 * count}".asText().color(TextColor.color(255,165,0))
            ))
    }
}