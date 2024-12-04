package me.zaksen.deathLabyrinth.game.room.logic.completion

import kotlinx.serialization.Serializable
import me.zaksen.deathLabyrinth.game.room.Room

@Serializable
class EntityCompletionCheck : CompletionCheck {
    override fun check(room: Room): Boolean {
        return room.livingEntities.isEmpty()
    }
}