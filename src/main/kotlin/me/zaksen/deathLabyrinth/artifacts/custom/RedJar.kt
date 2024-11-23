package me.zaksen.deathLabyrinth.artifacts.custom

import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import me.zaksen.deathLabyrinth.artifacts.api.ArtifactRarity
import me.zaksen.deathLabyrinth.command.PlayerPickupArtifactEvent
import me.zaksen.deathLabyrinth.util.*
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

// TODO - Made more soft health adding
class RedJar: Artifact(
    "artifact.red_jar.name".asTranslate().color(TextColor.color(50,205,50)),
    ArtifactRarity.COMMON
) {

    init {
        abilityContainer.add {
            if(it !is PlayerPickupArtifactEvent) return@add
            if(it.player.uniqueId != ownerUuid) return@add

            val player = it.player
            player.updateMaxHealth(40.0 + (8.0 * (1 + count)))
        }
    }

    override fun asItemStack(): ItemStack {
        return ItemStack(Material.APPLE)
            .customModel(104)
            .name(name)
            .loreLine("artifact.red_jar.lore.0".asTranslate(
                "${8 * count}".asText().color(TextColor.color(255,165,0))
            ))
    }
}