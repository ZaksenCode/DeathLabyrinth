package me.zaksen.deathLabyrinth.game.room.logic.start

import me.zaksen.deathLabyrinth.game.room.Room

interface StartProcess {
    fun process(room: Room)

    fun debugDisplay(room: Room) {

    }
}