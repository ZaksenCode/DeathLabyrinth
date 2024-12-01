package me.zaksen.deathLabyrinth.artifacts.custom

import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import me.zaksen.deathLabyrinth.artifacts.api.ArtifactRarity
import me.zaksen.deathLabyrinth.damage.DamageType
import me.zaksen.deathLabyrinth.entity.friendly.FriendlyEntity
import me.zaksen.deathLabyrinth.entity.trader.Trader
import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.event.custom.WorldTickEvent
import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.util.*
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.craftbukkit.entity.CraftEntity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable

class DivineOrb: Artifact(
    "artifact.divine_orb.name".asTranslate().color(TextColor.color(50,205,50)),
    ArtifactRarity.EPIC
) {
    var time: Int = 0
    val maxTime: Int = 40

    init {
        abilityContainer.add {
            if(it !is WorldTickEvent) return@add
            if(ownerUuid == null) return@add
            val player = Bukkit.getPlayer(ownerUuid!!) ?: return@add

            if(player.gameMode != GameMode.SPECTATOR) {
                drawCircle(
                    location = player.location.add(0.0, 0.75, 0.0),
                    size = (5.0 + (0.125 * count)),
                    color = Color.WHITE,
                    particleSize = 0.5f
                )
            }

            time++

            if(time >= maxTime - Math.round(0.25 * count)) {
                time = 0

                for (entity in player.world.entities) {
                    if (player.location.distance(entity.location) < (5.0 + (0.125 * count)) && entity is LivingEntity && entity !is Player && (entity as CraftEntity).handle !is FriendlyEntity&& (entity as CraftEntity).handle !is Trader) {
                        EventManager.callPlayerSpellEntityDamageEvent(player, entity, 8.0 + (2 * count), DamageType.GENERAL)
                    }
                }
            }
        }
    }

    override fun asItemStack(): ItemStack {
        return ItemStack(Material.APPLE)
            .customModel(118)
            .name(name)
            .loreLine("artifact.divine_orb.lore.0".asTranslate(
                "${8 + (2 * count)}".asText().color(TextColor.color(255,165,0)),
                "${5 + (0.125 * count)}".asText().color(TextColor.color(255,165,0)),
                "${((maxTime - Math.round(0.25 * count)) * 50) / 1000.0}".asText().color(TextColor.color(255,165,0))
            ))
    }
}