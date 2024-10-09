package me.zaksen.deathLabyrinth.artifacts.custom

import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import me.zaksen.deathLabyrinth.artifacts.api.ArtifactRarity
import me.zaksen.deathLabyrinth.command.PlayerPickupArtifactEvent
import me.zaksen.deathLabyrinth.keys.PluginKeys.speedModifierKey
import me.zaksen.deathLabyrinth.util.*
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.inventory.ItemStack

class MysticPotion: Artifact(
    "artifact.mystic_potion.name".asTranslate().color(TextColor.color(50,205,50)),
    ArtifactRarity.COMMON
) {

    init {
        abilityContainer.add {
            if(it !is PlayerPickupArtifactEvent) return@add
            val player = it.player

            player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)?.removeModifier(speedModifierKey)

            player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)?.addModifier(
                AttributeModifier(
                    speedModifierKey,
                    0.1 * count,
                    AttributeModifier.Operation.MULTIPLY_SCALAR_1
                )
            )
        }
    }

    override fun asItemStack(): ItemStack {
        return ItemStack(Material.APPLE)
            .customModel(101)
            .name(name)
            .loreLine("artifact.mystic_potion.lore.0".asTranslate(
                "${10 * count}%".asText().color(TextColor.color(255,165,0))
            ))
    }
}