package me.zaksen.deathLabyrinth.data

import me.zaksen.deathLabyrinth.classes.PlayerClass

data class PlayerData(
    var isReady: Boolean = false,
    var isAlive: Boolean = true,
    var playerClass: PlayerClass? = null
)