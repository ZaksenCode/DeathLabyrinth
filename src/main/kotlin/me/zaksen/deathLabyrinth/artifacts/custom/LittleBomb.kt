package me.zaksen.deathLabyrinth.artifacts.custom

import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import me.zaksen.deathLabyrinth.artifacts.api.ArtifactRarity
import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.event.custom.game.PlayerKillEntityEvent
import me.zaksen.deathLabyrinth.util.*
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import kotlin.random.Random

class LittleBomb: Artifact(
    "artifact.little_bomb.name".asTranslate().color(TextColor.color(50,205,50)),
    ArtifactRarity.EPIC
) {

    val random = Random.Default

    init {
        abilityContainer.add {
            if(it !is PlayerKillEntityEvent) return@add
            if(it.player == null) return@add
            if(it.player.uniqueId != ownerUuid) return@add

            val explodeChance = (10 + (5 * count)).coerceAtMost(100)

            if(random.nextInt(0, 100) <= explodeChance) {
                EventManager.callPlayerSummonExplosionEvent(it.player, it.entity.location, 3.5, 15.0)
            }
        }
    }

    override fun asItemStack(): ItemStack {
        return ItemStack(Material.APPLE)
            .customModel(110)
            .name(name)
            .loreLine("artifact.little_bomb.lore.0".asTranslate(
                "${10 + (5 * count)}%".asText().color(TextColor.color(255,165,0))
            ))
    }
}