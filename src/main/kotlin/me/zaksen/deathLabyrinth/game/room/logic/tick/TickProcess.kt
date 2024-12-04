package me.zaksen.deathLabyrinth.game.room.logic.tick

import me.zaksen.deathLabyrinth.game.room.Room

fun interface TickProcess {
    fun process(room: Room)
}