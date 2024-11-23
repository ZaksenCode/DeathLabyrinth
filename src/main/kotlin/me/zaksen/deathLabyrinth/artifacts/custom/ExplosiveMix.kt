package me.zaksen.deathLabyrinth.artifacts.custom

import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import me.zaksen.deathLabyrinth.artifacts.api.ArtifactRarity
import me.zaksen.deathLabyrinth.event.custom.game.PlayerSummonExplosionEvent
import me.zaksen.deathLabyrinth.util.*
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import kotlin.random.Random

class ExplosiveMix: Artifact(
    "artifact.explosive_mix.name".asTranslate().color(TextColor.color(50,205,50)),
    ArtifactRarity.RARE
) {

    val random = Random.Default

    init {
        abilityContainer.add {
            if(it !is PlayerSummonExplosionEvent) return@add
            if(it.player.uniqueId != ownerUuid) return@add

            it.range += it.range * (0.07 + (0.03 * count))
            it.damage += it.damage * (0.1 + (0.05 * count))
        }
    }

    override fun asItemStack(): ItemStack {
        return ItemStack(Material.APPLE)
            .customModel(111)
            .name(name)
            .loreLine("artifact.explosive_mix.lore.0".asTranslate(
                "${7 + (3 * count)}%".asText().color(TextColor.color(255,165,0)),
                "${10 + (5 * count)}%".asText().color(TextColor.color(255,165,0))
            ))
    }
}