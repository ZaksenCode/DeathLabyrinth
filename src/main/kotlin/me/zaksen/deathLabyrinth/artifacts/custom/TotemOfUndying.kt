package me.zaksen.deathLabyrinth.artifacts.custom

import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import me.zaksen.deathLabyrinth.artifacts.api.ArtifactRarity
import me.zaksen.deathLabyrinth.event.custom.game.PlayerDeathEvent
import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.util.*
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.attribute.Attribute
import org.bukkit.event.entity.EntityRegainHealthEvent
import org.bukkit.inventory.ItemStack

class TotemOfUndying: Artifact(
    "artifact.totem_of_undying.name".asTranslate().color(TextColor.color(50,205,50)),
    ArtifactRarity.EPIC
) {

    init {
        abilityContainer.add {
            if(it !is PlayerDeathEvent) return@add
            if(it.player.uniqueId != ownerUuid) return@add

            if(count > 0) {
                count--

                val player = it.player
                player.heal(
                    player.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.baseValue,
                    EntityRegainHealthEvent.RegainReason.REGEN
                )
                GameController.addPlayerShield(
                    player,
                    player.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.baseValue / 2
                )

                player.spawnParticle(
                    Particle.TOTEM_OF_UNDYING,
                    player.location,
                    50,
                    0.5,
                    0.5,
                    0.5
                )

                player.world.playSound(
                    player.location,
                    Sound.ITEM_TOTEM_USE,
                    SoundCategory.PLAYERS,
                    1.0f,
                    1.0f
                )

                it.isCancelled = true
            }

            if(count < 1) {
                val data = GameController.players[it.player] ?: return@add
                data.removeArtifact(this)
            }
        }
    }

    override fun asItemStack(): ItemStack {
        return ItemStack(Material.TOTEM_OF_UNDYING)
            .name(name)
            .loreLine("artifact.totem_of_undying.lore.0".asTranslate(
                "$count".asText().color(TextColor.color(255,165,0))
            ))
    }
}