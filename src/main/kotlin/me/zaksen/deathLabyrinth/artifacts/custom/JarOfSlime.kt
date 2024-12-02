package me.zaksen.deathLabyrinth.artifacts.custom

import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import me.zaksen.deathLabyrinth.artifacts.api.ArtifactRarity
import me.zaksen.deathLabyrinth.entity.friendly.slime.BlobEntity
import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.event.custom.game.PlayerKillEntityEvent
import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.util.*
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class JarOfSlime: Artifact(
    "artifact.jar_of_slime.name".asTranslate().color(TextColor.color(50,205,50)),
    ArtifactRarity.RARE
) {

    init {
        abilityContainer.add {
            if(it !is PlayerKillEntityEvent) return@add
            if(it.player == null) return@add
            if(it.player.uniqueId != ownerUuid) return@add

            val player = it.player

            if(GameController.checkChance((2 * count).coerceAtMost(25))) {
                val skeleton = BlobEntity(it.entity.location.add(0.0, 0.5, 1.0), player)
                EventManager.callPlayerSummonFriendlyEntityEvent(player, skeleton)
            }
        }
    }

    override fun asItemStack(): ItemStack {
        return ItemStack(Material.APPLE)
            .customModel(120)
            .name(name)
            .loreLine("artifact.jar_of_slime.lore.0".asTranslate(
                "${(2 * count).coerceAtMost(25)}%".asText().color(TextColor.color(255,165,0))
            ))
    }
}