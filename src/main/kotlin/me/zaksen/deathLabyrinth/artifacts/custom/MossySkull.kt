package me.zaksen.deathLabyrinth.artifacts.custom

import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import me.zaksen.deathLabyrinth.artifacts.api.ArtifactRarity
import me.zaksen.deathLabyrinth.event.custom.game.FriendlyEntityDamageEntityEvent
import me.zaksen.deathLabyrinth.event.custom.game.PlayerSummonFriendlyEntityEvent
import me.zaksen.deathLabyrinth.game.room.RoomController
import me.zaksen.deathLabyrinth.util.*
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class MossySkull: Artifact(
    "artifact.mossy_skull.name".asTranslate().color(TextColor.color(50,205,50)),
    ArtifactRarity.EPIC
) {

    init {
        abilityContainer.add {
            if(it !is PlayerSummonFriendlyEntityEvent) return@add

            for(i in 1..<1 + (1 * count)) {
                RoomController.spawnFriendlyEntityClone(it.player, it.entity)
            }
        }
        abilityContainer.add {
            if(it !is FriendlyEntityDamageEntityEvent) return@add
            it.damage *= 1 + (1 * count)
        }
    }

    override fun asItemStack(): ItemStack {
        return ItemStack(Material.APPLE)
            .customModel(106)
            .name(name)
            .loreLine("artifact.mossy_skull.lore.0".asTranslate(
                "${1 + (1 * count)}x".asText().color(TextColor.color(255,165,0)),
                "${1 + (1 * count)}x".asText().color(TextColor.color(255,165,0))
            ))
    }

}