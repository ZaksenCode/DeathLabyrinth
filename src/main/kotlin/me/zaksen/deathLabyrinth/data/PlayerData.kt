package me.zaksen.deathLabyrinth.data

import me.zaksen.deathLabyrinth.classes.PlayerClass

data class PlayerData(
    var isReady: Boolean = false,
    var playerClass: PlayerClass? = null
)