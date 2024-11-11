package me.zaksen.deathLabyrinth.artifacts.custom

import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import me.zaksen.deathLabyrinth.artifacts.api.ArtifactRarity
import me.zaksen.deathLabyrinth.event.custom.game.PlayerRoomCompleteEvent
import me.zaksen.deathLabyrinth.util.*
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.event.entity.EntityRegainHealthEvent
import org.bukkit.inventory.ItemStack

class GreenHeart: Artifact(
    "artifact.green_heart.name".asTranslate().color(TextColor.color(50,205,50)),
    ArtifactRarity.RARE
) {

    init {
        abilityContainer.add {
            if(it !is PlayerRoomCompleteEvent) return@add
            it.player.heal(8.0 * count, EntityRegainHealthEvent.RegainReason.REGEN)
        }
    }

    override fun asItemStack(): ItemStack {
        return ItemStack(Material.APPLE)
            .customModel(100)
            .name(name)
            .loreLine("artifact.green_heart.lore.0".asTranslate(
                "${8.0 * count}".asText().color(TextColor.color(255,165,0))
            ))
    }
}