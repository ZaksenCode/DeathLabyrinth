package me.zaksen.deathLabyrinth.util

import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player

fun Player.updateMaxHealth(amount: Double) {
    this.getAttribute(Attribute.GENERIC_MAX_HEALTH)?.baseValue = amount
    this.updateAbsorptionLevel()
}

fun Player.updateAbsorptionLevel() {
    val maxHealth = this.getAttribute(Attribute.GENERIC_MAX_HEALTH) ?: return
    this.getAttribute(Attribute.GENERIC_MAX_ABSORPTION)?.baseValue = maxHealth.value / 2
}