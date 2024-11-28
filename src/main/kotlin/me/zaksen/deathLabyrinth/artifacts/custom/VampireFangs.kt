package me.zaksen.deathLabyrinth.artifacts.custom

import me.zaksen.deathLabyrinth.artifacts.ability.Ability
import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import me.zaksen.deathLabyrinth.artifacts.api.ArtifactRarity
import me.zaksen.deathLabyrinth.artifacts.api.ArtifactsStates
import me.zaksen.deathLabyrinth.event.custom.game.PlayerDamageEntityEvent
import me.zaksen.deathLabyrinth.event.custom.game.PlayerKillEntityEvent
import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.util.*
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.event.Event
import org.bukkit.event.entity.EntityRegainHealthEvent
import org.bukkit.inventory.ItemStack

class VampireFangs: Artifact(
    "artifact.vampire_fangs.name".asTranslate().color(TextColor.color(50,205,50)),
    ArtifactRarity.EPIC
) {

    init {
        abilityContainer.add(object: Ability {
            override fun invoke(event: Event) {
                if(event !is PlayerKillEntityEvent) return
                if(event.player == null) return
                if(event.player.uniqueId != ownerUuid) return

                val player = event.player

                if(GameController.checkChance(25)) {
                    player.heal(6.0 * count, EntityRegainHealthEvent.RegainReason.REGEN)
                }
            }
        })
    }

    override fun asItemStack(): ItemStack {
        return ItemStack(Material.APPLE)
            .customModel(115)
            .name(name)
            .loreLine("artifact.vampire_fangs.lore.0".asTranslate(
                "${6.0 * count}".asText().color(TextColor.color(255,165,0))
            ))
    }
}