package me.zaksen.deathLabyrinth.game.room.logic.start

import me.zaksen.deathLabyrinth.config.RoomConfig
import me.zaksen.deathLabyrinth.game.room.Room
import org.bukkit.World

interface StartProcess {
    fun process(room: Room)

    fun debugDisplay(world: World, x: Int, y: Int, z: Int, config: RoomConfig) {}
}