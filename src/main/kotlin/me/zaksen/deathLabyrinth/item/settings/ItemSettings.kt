package me.zaksen.deathLabyrinth.item.settings

import net.kyori.adventure.text.Component
import org.bukkit.Material

class ItemSettings(val material: Material) {

    private var customModelData: Int = 0
    private var displayName: Component = Component.empty()
    private var lore: MutableList<Component> = mutableListOf()

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

    fun customModel(): Int {
        return customModelData
    }

    fun displayName(): Component {
        return displayName
    }

    fun lore(): MutableList<Component> {
        return lore
    }

}