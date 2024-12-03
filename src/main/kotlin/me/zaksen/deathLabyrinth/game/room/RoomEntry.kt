package me.zaksen.deathLabyrinth.game.room

import me.zaksen.deathLabyrinth.config.RoomConfig
import java.io.File

data class RoomEntry(
    val roomConfig: RoomConfig,
    val schematic: File,
)
