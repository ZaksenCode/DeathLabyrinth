package me.zaksen.deathLabyrinth.artifacts.custom

import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import me.zaksen.deathLabyrinth.artifacts.api.ArtifactRarity
import me.zaksen.deathLabyrinth.event.custom.game.PlayerDamagedByEntityEvent
import me.zaksen.deathLabyrinth.event.custom.game.PlayerPostPickupArtifactEvent
import me.zaksen.deathLabyrinth.keys.PluginKeys.speedModifierShacklesKey
import me.zaksen.deathLabyrinth.util.*
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.inventory.ItemStack

class Shackles: Artifact(
    "artifact.shackles.name".asTranslate().color(TextColor.color(50,205,50)),
    ArtifactRarity.COMMON
) {

    init {
        abilityContainer.add {
            if(it !is PlayerPostPickupArtifactEvent) return@add
            if(it.player.uniqueId != ownerUuid) return@add

            val player = it.player

            player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)?.removeModifier(speedModifierShacklesKey)

            player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)?.addModifier(
                AttributeModifier(
                    speedModifierShacklesKey,
                    (-0.05 * (count).coerceAtMost(18)),
                    AttributeModifier.Operation.ADD_SCALAR
                )
            )
        }
        abilityContainer.add {
            if(it !is PlayerDamagedByEntityEvent) return@add
            if(it.damaged.uniqueId != ownerUuid) return@add

            it.damage -= it.damage * (0.05 * count.coerceAtMost(18))
        }
    }

    override fun asItemStack(): ItemStack {
        return ItemStack(Material.APPLE)
            .customModel(119)
            .name(name)
            .loreLine("artifact.shackles.lore.0".asTranslate(
                "${(5 * count).coerceAtMost(90)}%".asText().color(TextColor.color(255,165,0))
            ))
    }
}