package me.zaksen.deathLabyrinth.artifacts.custom

import me.zaksen.deathLabyrinth.artifacts.ability.Ability
import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import me.zaksen.deathLabyrinth.artifacts.api.ArtifactRarity
import me.zaksen.deathLabyrinth.event.custom.game.PlayerBreakPotEvent
import me.zaksen.deathLabyrinth.util.*
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.event.Event
import org.bukkit.inventory.ItemStack

class MagnifyingGlass: Artifact(
    "artifact.magnifying_glass.name".asTranslate().color(TextColor.color(50,205,50)),
    ArtifactRarity.RARE
) {

    init {
        abilityContainer.add(object: Ability {
            override fun invoke(event: Event) {
                if(event !is PlayerBreakPotEvent) return
                if(event.player.uniqueId != ownerUuid) return

                if(event.output.isStackable) {
                    event.output.stack.add(count)
                }
            }
        })
    }

    override fun asItemStack(): ItemStack {
        return ItemStack(Material.APPLE)
            .customModel(116)
            .name(name)
            .loreLine("artifact.magnifying_glass.lore.0".asTranslate(
                "${1 * count}".asText().color(TextColor.color(255,165,0))
            ))
    }
}