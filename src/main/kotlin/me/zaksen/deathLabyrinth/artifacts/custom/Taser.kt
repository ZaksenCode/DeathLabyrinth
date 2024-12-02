package me.zaksen.deathLabyrinth.artifacts.custom

import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import me.zaksen.deathLabyrinth.artifacts.api.ArtifactRarity
import me.zaksen.deathLabyrinth.entity.friendly.FriendlyEntity
import me.zaksen.deathLabyrinth.entity.trader.Trader
import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.event.custom.WorldTickEvent
import me.zaksen.deathLabyrinth.event.custom.game.PlayerDamageEntityEvent
import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.util.*
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.craftbukkit.entity.CraftEntity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class Taser: Artifact(
    "artifact.taser.name".asTranslate().color(TextColor.color(50,205,50)),
    ArtifactRarity.RARE
) {

    var time: Int = 0
    val maxTime: Int = 10

    init {
        abilityContainer.add {
            if (it !is PlayerDamageEntityEvent) return@add
            if (it.player.uniqueId != ownerUuid) return@add

            if (GameController.checkChance((10 + (5 * count)).coerceAtMost(100)) && time >= maxTime) {
                time = 0

                val entity = it.damaged

                val entities = entity.getNearbyEntities(16.0, 16.0, 16.0).filter {
                    entity !is Player && (entity as CraftEntity).handle !is FriendlyEntity && (entity as CraftEntity).handle !is Trader
                }.toMutableSet()

                val damageList: MutableList<LivingEntity> = mutableListOf()

                for(i in 0..(1 + Math.round(0.25 * count).toInt()).coerceAtMost(entities.size)) {
                    val toDamage = entities.randomOrNull()
                    entities.remove(toDamage)

                    if(toDamage is LivingEntity) {
                        damageList.add(toDamage)
                    }
                }

                damageList.forEach { toDamage ->
                    EventManager.callPlayerDamageEntityEvent(it.player, toDamage, it.damage * (0.5 + (0.05 * count)))
                }

                drawParticles(damageList)
            }
        }

        abilityContainer.add {
            if (it !is WorldTickEvent) return@add

            if(time < maxTime) {
                time++
            }
        }
    }

    private fun drawParticles(between: MutableList<LivingEntity>) {
        if(between.isEmpty()) return
        var firstLocation = between.removeFirst().location.add(0.0, 1.0, 0.0)
        if(between.isEmpty()) {
            return
        }
        var secondLocation = between.removeFirst().location.add(0.0, 1.0, 0.0)
        firstLocation.particleLine(Particle.ENCHANTED_HIT, secondLocation)

        while(between.isNotEmpty()) {
            firstLocation = secondLocation
            secondLocation = between.removeFirst().location.add(0.0, 1.0, 0.0)
            firstLocation.particleLine(Particle.ENCHANTED_HIT, secondLocation)
        }
    }

    override fun asItemStack(): ItemStack {
        return ItemStack(Material.APPLE)
            .customModel(121)
            .name(name)
            .loreLine(
                "artifact.taser.lore.0".asTranslate(
                    "${10 + (5 * count)}%".asText().color(TextColor.color(255, 165, 0)),
                    "${1 + Math.round(0.25 * count)}".asText().color(TextColor.color(255, 165, 0)),
                    "${50 + (5 * count)}%".asText().color(TextColor.color(255, 165, 0))
                )
            )
            .loreLine("artifact.taser.lore.1".asTranslate())
    }
}