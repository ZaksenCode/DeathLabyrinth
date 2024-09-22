package me.zaksen.deathLabyrinth.artifacts.custom

import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import me.zaksen.deathLabyrinth.artifacts.api.ArtifactRarity
import me.zaksen.deathLabyrinth.event.custom.game.PlayerSpellEntityDamageEvent
import me.zaksen.deathLabyrinth.util.asText
import me.zaksen.deathLabyrinth.util.customModel
import me.zaksen.deathLabyrinth.util.loreLine
import me.zaksen.deathLabyrinth.util.name
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class Jewel: Artifact("Самоцвет", ArtifactRarity.EPIC) {

    init {
        abilityContainer.add {
            if(it !is PlayerSpellEntityDamageEvent) return@add
            it.damage += it.damage * (0.15 * count)
        }
    }

    override fun asItemStack(): ItemStack {
        return ItemStack(Material.APPLE)
            .customModel(104)
            .name("<green>$name</green>".asText())
            .loreLine("<gray>Увеличивает урон заклинаний на</gray> <gold>${15 * count}%</gold>".asText())
    }
}