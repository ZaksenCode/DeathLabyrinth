package me.zaksen.deathLabyrinth.game.room.logic.completion

import me.zaksen.deathLabyrinth.config.RoomConfig
import me.zaksen.deathLabyrinth.game.room.Room
import org.bukkit.World

interface CompletionCheck {
    fun check(room: Room): Boolean

    fun debugDisplay(world: World, x: Int, y: Int, z: Int, config: RoomConfig) {}
}