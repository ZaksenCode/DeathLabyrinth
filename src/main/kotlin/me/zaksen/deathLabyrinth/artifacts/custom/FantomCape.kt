package me.zaksen.deathLabyrinth.artifacts.custom

import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import me.zaksen.deathLabyrinth.artifacts.api.ArtifactRarity
import me.zaksen.deathLabyrinth.event.custom.game.PlayerDamagedByEntityEvent
import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.util.*
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import kotlin.random.Random

class FantomCape: Artifact(
    "artifact.fantom_cape.name".asTranslate().color(TextColor.color(50,205,50)),
    ArtifactRarity.RARE
) {
    val random = Random.Default

    init {
        abilityContainer.add {
            if(it !is PlayerDamagedByEntityEvent) return@add
            if(it.damaged.uniqueId != ownerUuid) return@add

            if(GameController.checkChance((5 * count).coerceAtMost(50))) {
                it.isCancelled = true

                it.damaged.addPotionEffect(PotionEffect(
                    PotionEffectType.SPEED,
                    50,
                    3
                ))

                it.damaged.playSound(
                    it.damaged.location,
                    Sound.ENTITY_EXPERIENCE_ORB_PICKUP,
                    1.0f,
                    1.0f
                )
            }
        }
    }

    override fun asItemStack(): ItemStack {
        return ItemStack(Material.APPLE)
            .customModel(113)
            .name(name)
            .loreLine("artifact.fantom_cape.lore.0".asTranslate(
                "${5 * count}%".asText().color(TextColor.color(255,165,0))
            ))
    }
}