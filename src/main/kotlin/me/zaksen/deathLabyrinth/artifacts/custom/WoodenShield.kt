package me.zaksen.deathLabyrinth.artifacts.custom

import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import me.zaksen.deathLabyrinth.artifacts.api.ArtifactRarity
import me.zaksen.deathLabyrinth.event.custom.game.PlayerDamageEntityEvent
import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.util.*
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.inventory.ItemStack

class WoodenShield: Artifact(
    "artifact.wooden_shield.name".asTranslate().color(TextColor.color(50,205,50)),
    ArtifactRarity.RARE
) {

    init {
        abilityContainer.add {
            if(it !is PlayerDamageEntityEvent) return@add
            if(it.player.uniqueId != ownerUuid) return@add
            val maxHealth = it.player.getAttribute(Attribute.GENERIC_MAX_HEALTH) ?: return@add
            GameController.addPlayerShield(it.player, maxHealth.baseValue * (0.1 * count))
        }
    }

    override fun asItemStack(): ItemStack {
        return ItemStack(Material.APPLE)
            .customModel(109)
            .name(name)
            .loreLine("artifact.wooden_shield.lore.0".asTranslate(
                "${10 * count}%".asText().color(TextColor.color(255,165,0))
            ))
    }
}