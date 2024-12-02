package me.zaksen.deathLabyrinth.item.ability.consume

import me.zaksen.deathLabyrinth.artifacts.ArtifactsController
import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.event.item.ItemConsumeEvent
import me.zaksen.deathLabyrinth.item.ItemsController
import me.zaksen.deathLabyrinth.item.ability.ItemAbility
import me.zaksen.deathLabyrinth.util.getAngle
import net.kyori.adventure.text.Component
import org.bukkit.Location
import org.bukkit.event.Event

class SummonArtifacts: ItemAbility(
    Component.translatable("ability.summon_artifacts.name"),
    Component.translatable("ability.summon_artifacts.description"),
    isDisplayDamageType = false
) {
    override fun invoke(event: Event) {
        if(event !is ItemConsumeEvent) return

        val player =  event.player
        summonArtifact(player.location, 3.0, 1.5, 0.0)
        summonArtifact(player.location, -3.0, 1.5, 0.0)

        summonArtifact(player.location, 0.0, 1.5, 3.0)
        summonArtifact(player.location, 0.0, 1.5, -3.0)

        summonArtifact(player.location, -3.0, 1.5, 3.0)
        summonArtifact(player.location, -3.0, 1.5, -3.0)

        summonArtifact(player.location, 3.0, 1.5, 3.0)
        summonArtifact(player.location, 3.0, 1.5, -3.0)

        event.event.replacement = ItemsController.get("bottle")!!.asItemStack()

        // Do damage different way
        EventManager.callPlayerDeathEvent(player, 99999999.0)
    }

    private fun summonArtifact(playerLoc: Location, offsetX: Double, offsetY: Double, offsetZ: Double) {
        val summonLoc = playerLoc.clone().add(offsetX, offsetY, offsetZ)
        summonLoc.yaw = summonLoc.getAngle(playerLoc)
        ArtifactsController.summonArtifactCard(summonLoc, ArtifactsController.getRandomArtifact())
    }
}