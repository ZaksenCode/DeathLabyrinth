package me.zaksen.deathLabyrinth.artifacts.custom

import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import me.zaksen.deathLabyrinth.artifacts.api.ArtifactRarity
import me.zaksen.deathLabyrinth.event.custom.game.PlayerSpellEntityDamageEvent
import me.zaksen.deathLabyrinth.util.*
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class Jewel: Artifact(
    "artifact.jewel.name".asTranslate().color(TextColor.color(50,205,50)),
    ArtifactRarity.RARE
) {

    init {
        abilityContainer.add {
            if(it !is PlayerSpellEntityDamageEvent) return@add
            if(it.player.uniqueId != ownerUuid) return@add
            it.damage += it.damage * (0.2 * count)
        }
    }

    override fun asItemStack(): ItemStack {
        return ItemStack(Material.APPLE)
            .customModel(104)
            .name(name)
            .loreLine("artifact.jewel.lore.0".asTranslate(
                "${20 * count}%".asText().color(TextColor.color(255,165,0))
            ))
    }
}