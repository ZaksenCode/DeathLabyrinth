package me.zaksen.deathLabyrinth.artifacts.custom

import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import me.zaksen.deathLabyrinth.artifacts.api.ArtifactRarity
import me.zaksen.deathLabyrinth.command.PlayerPickupArtifactEvent
import me.zaksen.deathLabyrinth.keys.PluginKeys.speedModifierKey
import me.zaksen.deathLabyrinth.util.asText
import me.zaksen.deathLabyrinth.util.customModel
import me.zaksen.deathLabyrinth.util.loreLine
import me.zaksen.deathLabyrinth.util.name
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.inventory.ItemStack

class MysticPotion: Artifact("Мистическая настойка", ArtifactRarity.COMMON) {

    init {
        abilityContainer.add {
            if(it !is PlayerPickupArtifactEvent) return@add
            val player = it.player

            player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)?.removeModifier(speedModifierKey)

            player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)?.addModifier(
                AttributeModifier(
                    speedModifierKey,
                    0.05 * count,
                    AttributeModifier.Operation.MULTIPLY_SCALAR_1
                )
            )
        }
    }

    override fun asItemStack(): ItemStack {
        return ItemStack(Material.APPLE)
            .customModel(101)
            .name("<green>$name</green>".asText())
            .loreLine("<gray>Увеличивает скорость на</gray> <gold>${5 * count}%</gold>".asText())
    }
}