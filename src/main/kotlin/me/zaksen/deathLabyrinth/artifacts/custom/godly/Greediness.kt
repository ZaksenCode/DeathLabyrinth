package me.zaksen.deathLabyrinth.artifacts.custom.godly

import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import me.zaksen.deathLabyrinth.artifacts.api.ArtifactRarity
import me.zaksen.deathLabyrinth.entity.skeleton.NecromancerEntity
import me.zaksen.deathLabyrinth.entity.trader.Trader
import me.zaksen.deathLabyrinth.entity.villager.BlacksmithEntity
import me.zaksen.deathLabyrinth.event.custom.game.EntitySpawnEvent
import me.zaksen.deathLabyrinth.event.custom.game.PlayerRoomCompleteEvent
import me.zaksen.deathLabyrinth.game.room.RoomController
import me.zaksen.deathLabyrinth.util.*
import net.kyori.adventure.text.format.TextColor
import net.minecraft.world.entity.Entity
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class Greediness: Artifact(
    "artifact.greediness.name".asTranslate().color(TextColor.color(50,205,50)),
    ArtifactRarity.GODLY
) {

    init {
        abilityContainer.add {
            if(it !is PlayerRoomCompleteEvent) return@add
            it.reward *= 2 * count
        }
        abilityContainer.add {
            if(it !is EntitySpawnEvent) return@add
            for(i in 1..<2 * count) {
                val entity = it.entity

                if(entity is Trader || entity is NecromancerEntity || entity is BlacksmithEntity) {
                    return@add
                }

                // FIXME - Room controller now didn't operate this
                // RoomController.spawnEntityClone(entity, it.requireKill)
            }
        }
    }

    override fun asItemStack(): ItemStack {
        return ItemStack(Material.APPLE)
            .customModel(1000)
            .name(name)
            .loreLine("artifact.greediness.lore.0".asTranslate(
                "${2 * count}x".asText().color(TextColor.color(255,165,0)),
                "${2 * count}x".asText().color(TextColor.color(255,165,0))
            ))
    }
}