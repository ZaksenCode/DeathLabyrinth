package me.zaksen.deathLabyrinth.event

import me.zaksen.deathLabyrinth.event.custom.PlayerReadyEvent
import me.zaksen.deathLabyrinth.game.GameController
import org.bukkit.entity.Player


object EventManager {

    fun callReadyEvent(player: Player) {
        val coolEvent = PlayerReadyEvent(player)
        coolEvent.callEvent()
        if (!coolEvent.isCancelled) {
            GameController.toggleReadyState(player)
        }
    }
}