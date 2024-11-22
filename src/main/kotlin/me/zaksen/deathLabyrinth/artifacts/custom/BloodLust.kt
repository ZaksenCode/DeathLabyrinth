package me.zaksen.deathLabyrinth.artifacts.custom

import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import me.zaksen.deathLabyrinth.artifacts.api.ArtifactRarity
import me.zaksen.deathLabyrinth.event.custom.game.PlayerDamageEntityEvent
import me.zaksen.deathLabyrinth.util.*
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class BloodLust: Artifact(
    "artifact.blood_lust.name".asTranslate().color(TextColor.color(50,205,50)),
    ArtifactRarity.EPIC
) {

    init {
        abilityContainer.add {
            if(it !is PlayerDamageEntityEvent) return@add
            if(it.player.uniqueId != ownerUuid) return@add
            it.damage += it.damage * (0.2 * count)
        }
    }

    override fun asItemStack(): ItemStack {
        return ItemStack(Material.APPLE)
            .customModel(102)
            .name(name)
            .loreLine("artifact.blood_lust.lore.0".asTranslate(
                "${20 * count}%".asText().color(TextColor.color(255,165,0))
            ))
    }
}