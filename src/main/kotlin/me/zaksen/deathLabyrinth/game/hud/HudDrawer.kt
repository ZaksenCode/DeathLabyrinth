package me.zaksen.deathLabyrinth.game.hud

import org.bukkit.entity.Player

abstract class HudDrawer(val viewer: Player) {

    abstract fun draw()

}