package me.zaksen.deathLabyrinth.util

import me.zaksen.deathLabyrinth.config.data.Position
import org.bukkit.Bukkit
import org.bukkit.Location

fun locationOf(position: Position) = Location(Bukkit.getWorld(position.world), position.x, position.y, position.z)