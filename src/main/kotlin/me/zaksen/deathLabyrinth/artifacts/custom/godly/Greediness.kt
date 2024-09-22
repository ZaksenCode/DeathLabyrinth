package me.zaksen.deathLabyrinth.artifacts.custom.godly

import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import me.zaksen.deathLabyrinth.artifacts.api.ArtifactRarity
import me.zaksen.deathLabyrinth.entity.trader.Trader
import me.zaksen.deathLabyrinth.event.custom.game.EntitySpawnEvent
import me.zaksen.deathLabyrinth.event.custom.game.RoomCompleteEvent
import me.zaksen.deathLabyrinth.game.room.RoomController
import me.zaksen.deathLabyrinth.util.*
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class Greediness: Artifact("Алчность", ArtifactRarity.GODLY) {

    init {
        abilityContainer.add {
            if(it !is RoomCompleteEvent) return@add
            it.reward *= 1 * (count + 1)
        }
        abilityContainer.add {
            if(it !is EntitySpawnEvent) return@add
            for(i in 1..<1 * (count + 1)) {
                val entity = it.entity

                if(entity is Trader) {
                    return@add
                }

                RoomController.spawnEntityClone(entity, it.requireKill)
            }
        }
    }

    override fun asItemStack(): ItemStack {
        return ItemStack(Material.APPLE)
            .customModel(1000)
            .name("<green>$name</green>".asText())
            .loreLine("<gray>Вы получаете в <gold>${1 * (count + 1)}x <gray>больше золота за прохождение комнат, но в комнатах появляется в <gold>${1 * (count + 1)}x <gray>больше мобов".asText())
    }
}