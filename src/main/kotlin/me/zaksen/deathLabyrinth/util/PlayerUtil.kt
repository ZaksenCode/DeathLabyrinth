package me.zaksen.deathLabyrinth.util

import me.zaksen.deathLabyrinth.keys.PluginKeys
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType

fun Player.updateMaxHealth(amount: Double) {
    this.getAttribute(Attribute.GENERIC_MAX_HEALTH)?.baseValue = amount
    this.updateAbsorptionLevel()
}

fun Player.updateAbsorptionLevel() {
    val maxHealth = this.getAttribute(Attribute.GENERIC_MAX_HEALTH) ?: return
    this.getAttribute(Attribute.GENERIC_MAX_ABSORPTION)?.baseValue = maxHealth.value / 2
}

fun Player.setCountingAbsorptionLevel(amount: Int) {
    val maxHealth = this.getAttribute(Attribute.GENERIC_MAX_HEALTH) ?: return
    this.persistentDataContainer.set(PluginKeys.playerAbsorptionAmountKey, PersistentDataType.INTEGER, amount.coerceAtMost((maxHealth.value / 2).toInt()))
}