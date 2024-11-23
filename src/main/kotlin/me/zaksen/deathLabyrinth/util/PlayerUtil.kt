package me.zaksen.deathLabyrinth.util

import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player

fun Player.updateMaxHealth(amount: Double) {
    this.getAttribute(Attribute.GENERIC_MAX_HEALTH)?.baseValue = amount
    this.getAttribute(Attribute.GENERIC_MAX_ABSORPTION)?.baseValue = amount / 2
}