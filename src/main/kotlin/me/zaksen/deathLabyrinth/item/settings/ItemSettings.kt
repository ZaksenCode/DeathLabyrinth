package me.zaksen.deathLabyrinth.item.settings

import net.kyori.adventure.text.Component
import org.bukkit.Material

open class ItemSettings(val material: Material) {

    private var customModelData: Int = 0
    private var displayName: Component = Component.empty()
    private var lore: MutableList<Component> = mutableListOf()
    private var abilityCooldown: Int = 0
    private var damage: Double = 0.0
    private var attackSpeed: Double = 0.0
    private var range: Double = 0.0

    fun customModel(model: Int): ItemSettings {
        this.customModelData = model
        return this
    }

    fun displayName(name: Component): ItemSettings {
        this.displayName = name
        return this
    }

    fun lore(lore: MutableList<Component>): ItemSettings {
        this.lore = lore
        return this
    }

    fun abilityCooldown(cooldown: Int): ItemSettings {
        this.abilityCooldown = cooldown
        return this
    }

    fun damage(damage: Double): ItemSettings {
        this.damage = damage
        return this
    }

    fun attackSpeed(attackSpeed: Double): ItemSettings {
        this.attackSpeed = attackSpeed
        return this
    }

    fun range(range: Double): ItemSettings {
        this.range = range
        return this
    }

    fun customModel(): Int {
        return customModelData
    }

    fun displayName(): Component {
        return displayName
    }

    fun lore(): MutableList<Component> {
        return lore
    }

    fun abilityCooldown(): Int {
        return abilityCooldown
    }

    fun damage(): Double {
        return damage
    }

    fun attackSpeed(): Double {
        return attackSpeed
    }

    fun range(): Double {
        return range
    }
}