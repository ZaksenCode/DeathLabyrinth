package me.zaksen.deathLabyrinth.item.ability.consume

import me.zaksen.deathLabyrinth.artifacts.ArtifactsController
import me.zaksen.deathLabyrinth.entity.goal.ability.toLocation
import me.zaksen.deathLabyrinth.event.item.ItemConsumeEvent
import me.zaksen.deathLabyrinth.item.ItemsController
import me.zaksen.deathLabyrinth.item.ability.ItemAbility
import net.kyori.adventure.text.Component
import org.bukkit.Particle
import org.bukkit.event.Event

class DuplicateArtifacts: ItemAbility(
    Component.translatable("ability.duplicate_artifacts.name"),
    Component.translatable("ability.duplicate_artifacts.description"),
    isDisplayDamageType = false
) {
    override fun invoke(event: Event) {
        if(event !is ItemConsumeEvent) return

        val player =  event.player
        val holders = ArtifactsController.getWorldArtifacts(player.location, 32)

        println("Duplicate ${holders.size} artifacts")

        holders.forEach {
            val location = it.artifactsCardHitbox.position().toLocation(player.world).add(0.0, 3.2, 0.0)
            location.yaw = it.artifactsCardName.bukkitYaw

            player.world.spawnParticle(
                Particle.EFFECT,
                location,
                30
            )

            ArtifactsController.summonArtifactCard(
                location,
                it.artifact::class.java.getDeclaredConstructor().newInstance()
            )
        }

        event.event.replacement = ItemsController.get("bottle")!!.asItemStack()
    }
}