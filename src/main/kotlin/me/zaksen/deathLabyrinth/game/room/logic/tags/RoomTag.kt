package me.zaksen.deathLabyrinth.game.room.logic.tags

import kotlinx.serialization.Serializable
import me.zaksen.deathLabyrinth.game.room.Room

@Serializable
sealed interface RoomTag {
    fun debugDisplay(room: Room) {

    }
}