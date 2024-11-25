package me.zaksen.deathLabyrinth.artifacts.custom

import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import me.zaksen.deathLabyrinth.artifacts.api.ArtifactRarity
import me.zaksen.deathLabyrinth.event.custom.game.PlayerApplySlownessEvent
import me.zaksen.deathLabyrinth.util.*
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class Snowflake: Artifact(
    "artifact.snowflake.name".asTranslate().color(TextColor.color(50,205,50)),
    ArtifactRarity.COMMON
) {

    init {
        abilityContainer.add {
            if(it !is PlayerApplySlownessEvent) return@add
            if(it.player.uniqueId != ownerUuid) return@add

            it.duration += (it.duration * (0.2 * count)).toInt()
            it.amplifier +=  1 * count
        }
    }

    override fun asItemStack(): ItemStack {
        return ItemStack(Material.APPLE)
            .customModel(112)
            .name(name)
            .loreLine("artifact.snowflake.lore.0".asTranslate(
                "${20 * count}%".asText().color(TextColor.color(255,165,0)),
                "${1 * count}".asText().color(TextColor.color(255,165,0))
            ))
    }
}