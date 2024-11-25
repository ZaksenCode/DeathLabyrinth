package me.zaksen.deathLabyrinth.artifacts.custom

import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import me.zaksen.deathLabyrinth.artifacts.api.ArtifactRarity
import me.zaksen.deathLabyrinth.damage.DamageType
import me.zaksen.deathLabyrinth.event.custom.game.PlayerSpellEntityDamageEvent
import me.zaksen.deathLabyrinth.event.item.ItemHitEvent
import me.zaksen.deathLabyrinth.util.*
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class Gasoline: Artifact(
    "artifact.gasoline.name".asTranslate().color(TextColor.color(50,205,50)),
    ArtifactRarity.EPIC
) {

    init {
        abilityContainer.add {
            if(it !is ItemHitEvent) return@add
            if(it.damageType != DamageType.FIRE) return@add
            if(it.damager.uniqueId != ownerUuid) return@add
            it.damage += it.damage * (0.45 * count)
        }
        abilityContainer.add {
            if(it !is PlayerSpellEntityDamageEvent) return@add
            if(it.damageType != DamageType.FIRE) return@add
            if(it.player.uniqueId != ownerUuid) return@add
            it.damage += it.damage * (0.45 * count)
        }
    }

    override fun asItemStack(): ItemStack {
        return ItemStack(Material.APPLE)
            .customModel(108)
            .name(name)
            .loreLine("artifact.gasoline.lore.0".asTranslate(
                "${45 * count}%".asText().color(TextColor.color(255,165,0))
            ))
    }
}