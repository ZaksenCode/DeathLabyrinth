package me.zaksen.deathLabyrinth.game.room.logic.completion

import me.zaksen.deathLabyrinth.game.room.Room

interface CompletionCheck {
    fun check(room: Room): Boolean

    fun debugDisplay(room: Room) {

    }
}