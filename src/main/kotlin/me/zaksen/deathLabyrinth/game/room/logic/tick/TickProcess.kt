package me.zaksen.deathLabyrinth.game.room.logic.tick

import me.zaksen.deathLabyrinth.game.room.Room

interface TickProcess {
    fun process(room: Room)

    fun debugDisplay(room: Room) {

    }
}