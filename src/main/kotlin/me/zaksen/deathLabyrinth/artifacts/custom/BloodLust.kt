package me.zaksen.deathLabyrinth.artifacts.custom

import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import me.zaksen.deathLabyrinth.artifacts.api.ArtifactRarity
import me.zaksen.deathLabyrinth.event.custom.game.PlayerDamageEntityEvent
import me.zaksen.deathLabyrinth.util.asText
import me.zaksen.deathLabyrinth.util.customModel
import me.zaksen.deathLabyrinth.util.loreLine
import me.zaksen.deathLabyrinth.util.name
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class BloodLust: Artifact("Жажда крови", ArtifactRarity.EPIC) {

    init {
        abilityContainer.add {
            if(it !is PlayerDamageEntityEvent) return@add
            it.damage += it.damage * (0.1 * count)
        }
    }

    override fun asItemStack(): ItemStack {
        return ItemStack(Material.APPLE)
            .customModel(102)
            .name("<green>$name</green>".asText())
            .loreLine("<gray>Увеличивает урон ближнего боя на</gray> <gold>${10 * count}%</gold>".asText())
    }
}