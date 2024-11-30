package me.zaksen.deathLabyrinth.item.ability.consume

import me.zaksen.deathLabyrinth.artifacts.ArtifactsController
import me.zaksen.deathLabyrinth.entity.goal.ability.toLocation
import me.zaksen.deathLabyrinth.event.item.ItemConsumeEvent
import me.zaksen.deathLabyrinth.item.ItemsController
import me.zaksen.deathLabyrinth.item.ability.ItemAbility
import net.kyori.adventure.text.Component
import org.bukkit.Particle
import org.bukkit.event.Event

class ChangeArtifacts: ItemAbility(
    Component.translatable("ability.change_artifacts.name"),
    Component.translatable("ability.change_artifacts.description"),
    isDisplayDamageType = false
) {
    override fun invoke(event: Event) {
        if(event !is ItemConsumeEvent) return

        val player =  event.player
        val holders = ArtifactsController.getWorldArtifacts(player.location, 32)

        holders.forEach {
            it.changeArtifact(ArtifactsController.getRandomArtifact())

            val location = it.artifactsCardHitbox.position().toLocation(player.world)

            player.world.spawnParticle(
                Particle.EFFECT,
                location,
                30
            )
        }

        event.event.replacement = ItemsController.get("bottle")!!.asItemStack()
    }
}