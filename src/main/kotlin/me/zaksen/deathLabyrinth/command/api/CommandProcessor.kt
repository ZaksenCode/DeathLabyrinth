package me.zaksen.deathLabyrinth.command.api

import org.bukkit.entity.Player

interface CommandProcessor {
    fun process(sender: Player, args:  Array<out String>)
    fun processTab(sender: Player, args:  Array<out String>): MutableList<String> {
        return mutableListOf("")
    }
}