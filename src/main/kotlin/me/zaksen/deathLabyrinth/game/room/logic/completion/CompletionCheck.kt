package me.zaksen.deathLabyrinth.game.room.logic.completion

import me.zaksen.deathLabyrinth.game.room.Room

fun interface CompletionCheck {
    fun check(room: Room): Boolean
}