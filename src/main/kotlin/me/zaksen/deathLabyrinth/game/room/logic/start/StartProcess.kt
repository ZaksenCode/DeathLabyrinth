package me.zaksen.deathLabyrinth.game.room.logic.start

import me.zaksen.deathLabyrinth.game.room.Room

fun interface StartProcess {
    fun process(room: Room)
}