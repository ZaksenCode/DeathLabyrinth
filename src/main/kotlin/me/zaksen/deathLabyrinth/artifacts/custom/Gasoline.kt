package me.zaksen.deathLabyrinth.artifacts.custom

import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import me.zaksen.deathLabyrinth.artifacts.api.ArtifactRarity
import me.zaksen.deathLabyrinth.damage.DamageType
import me.zaksen.deathLabyrinth.event.custom.game.PlayerDamageEntityEvent
import me.zaksen.deathLabyrinth.util.*
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

// TODO - Didn't working - damage type always general
class Gasoline: Artifact(
    "artifact.gasoline.name".asTranslate().color(TextColor.color(50,205,50)),
    ArtifactRarity.EPIC
) {

    init {
        abilityContainer.add {
            if(it !is PlayerDamageEntityEvent) return@add
            println("Damage type: ${it.damageType}")
            if(it.damageType != DamageType.FIRE) return@add
            if(it.player.uniqueId != ownerUuid) return@add
            it.damage += it.damage * (0.35 * count)
        }
    }

    override fun asItemStack(): ItemStack {
        return ItemStack(Material.APPLE)
            .customModel(108)
            .name(name)
            .loreLine("artifact.gasoline.lore.0".asTranslate(
                "${35 * count}%".asText().color(TextColor.color(255,165,0))
            ))
    }
}