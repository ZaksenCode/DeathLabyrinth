package me.zaksen.deathLabyrinth.artifacts.custom

import me.zaksen.deathLabyrinth.artifacts.ability.Ability
import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import me.zaksen.deathLabyrinth.artifacts.api.ArtifactRarity
import me.zaksen.deathLabyrinth.artifacts.api.ArtifactsStates
import me.zaksen.deathLabyrinth.event.custom.game.PlayerDamageEntityEvent
import me.zaksen.deathLabyrinth.util.asText
import me.zaksen.deathLabyrinth.util.customModel
import me.zaksen.deathLabyrinth.util.loreLine
import me.zaksen.deathLabyrinth.util.name
import org.bukkit.Material
import org.bukkit.event.Event
import org.bukkit.inventory.ItemStack

class RustyDagger: Artifact("Ржавый клинок", ArtifactRarity.RARE) {

    init {
        abilityContainer.add(object: Ability {
            override fun invoke(event: Event) {
                if(event !is PlayerDamageEntityEvent) return
                val player = event.player
                val state = ArtifactsStates.get(player.uniqueId, this)

                if(state == null) {
                    ArtifactsStates.put(player.uniqueId, this, 1)
                    return
                }

                if(state !is Int) {
                    return
                }

                if(state < 3) {
                    ArtifactsStates.put(player.uniqueId, this, state + 1)
                } else {
                    event.damage *= (1 + (0.5 * count))
                    ArtifactsStates.put(player.uniqueId, this, 1)
                }
            }
        })
    }

    override fun asItemStack(): ItemStack {
        return ItemStack(Material.APPLE)
            .customModel(103)
            .name("<green>$name</green>".asText())
            .loreLine("<gray>Увеличивает урон каждого третьего удара в ближнем бою на <gold>${1 + (0.5 * count)}x</gold>".asText())
    }
}