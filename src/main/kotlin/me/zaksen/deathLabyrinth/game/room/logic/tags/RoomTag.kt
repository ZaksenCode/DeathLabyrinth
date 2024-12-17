package me.zaksen.deathLabyrinth.game.room.logic.tags

import kotlinx.serialization.Serializable
import me.zaksen.deathLabyrinth.config.RoomConfig
import org.bukkit.World

@Serializable
sealed interface RoomTag {
    fun debugDisplay(world: World, x: Int, y: Int, z: Int, config: RoomConfig) {}
    fun addOffset(x: Int, y: Int, z: Int)
}