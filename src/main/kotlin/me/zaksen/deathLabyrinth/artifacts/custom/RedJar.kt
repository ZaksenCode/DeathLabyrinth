package me.zaksen.deathLabyrinth.artifacts.custom

import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import me.zaksen.deathLabyrinth.artifacts.api.ArtifactRarity
import me.zaksen.deathLabyrinth.event.custom.game.PlayerPickupArtifactEvent
import me.zaksen.deathLabyrinth.event.custom.game.PlayerPostPickupArtifactEvent
import me.zaksen.deathLabyrinth.keys.PluginKeys
import me.zaksen.deathLabyrinth.keys.PluginKeys.maxHealthModifierKey
import me.zaksen.deathLabyrinth.keys.PluginKeys.speedModifierKey
import me.zaksen.deathLabyrinth.util.*
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

class RedJar: Artifact(
    "artifact.red_jar.name".asTranslate().color(TextColor.color(50,205,50)),
    ArtifactRarity.COMMON
) {

    init {
        abilityContainer.add {
            if(it !is PlayerPostPickupArtifactEvent) return@add
            if(it.player.uniqueId != ownerUuid) return@add

            val player = it.player

            player.getAttribute(Attribute.GENERIC_MAX_HEALTH)?.removeModifier(maxHealthModifierKey)

            player.getAttribute(Attribute.GENERIC_MAX_HEALTH)?.addModifier(
                AttributeModifier(
                    maxHealthModifierKey,
                    8.0 * count,
                    AttributeModifier.Operation.ADD_NUMBER
                )
            )

            player.updateAbsorptionLevel()
        }
    }

    override fun asItemStack(): ItemStack {
        return ItemStack(Material.APPLE)
            .customModel(104)
            .name(name)
            .loreLine("artifact.red_jar.lore.0".asTranslate(
                "${8 * count}".asText().color(TextColor.color(255,165,0))
            ))
    }
}