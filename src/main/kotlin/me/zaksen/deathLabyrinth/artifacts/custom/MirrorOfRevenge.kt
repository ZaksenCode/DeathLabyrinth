package me.zaksen.deathLabyrinth.artifacts.custom

import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import me.zaksen.deathLabyrinth.artifacts.api.ArtifactRarity
import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.event.custom.game.PlayerDamagedByEntityEvent
import me.zaksen.deathLabyrinth.util.*
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.entity.LivingEntity
import org.bukkit.inventory.ItemStack

class MirrorOfRevenge: Artifact(
    "artifact.mirror_of_revenge.name".asTranslate().color(TextColor.color(50,205,50)),
    ArtifactRarity.RARE
) {
    init {
        abilityContainer.add {
            if(it !is PlayerDamagedByEntityEvent) return@add
            val damager = it.damager
            if(damager !is LivingEntity) return@add

            EventManager.callPlayerDamageEntityEvent(it.damaged, it.damager as LivingEntity, it.damage * (count * 2))
        }
    }

    override fun asItemStack(): ItemStack {
        return ItemStack(Material.APPLE)
            .customModel(105)
            .name(name)
            .loreLine("artifact.mirror_of_revenge.lore.0".asTranslate(
                "${count * 2}x".asText().color(TextColor.color(255,165,0))
            ))
    }
}