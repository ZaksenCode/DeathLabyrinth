package me.zaksen.deathLabyrinth.item.ability

import me.zaksen.deathLabyrinth.artifacts.ability.Ability
import me.zaksen.deathLabyrinth.util.asText
import me.zaksen.deathLabyrinth.util.asTranslate
import me.zaksen.deathLabyrinth.util.loreLine
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.event.Event
import org.bukkit.inventory.ItemStack

abstract class ItemAbility(
    val name: Component,
    val description: Component,
    val displayDamage: Double = 0.0,
    val displayRange: Double = 0.0
): Ability {
    abstract override fun invoke(event: Event)

    fun addAbilityDescription(stack: ItemStack) {
        stack.loreLine(name.decoration(TextDecoration.ITALIC, false).color(TextColor.color(178, 91, 245)))
        stack.loreLine(Component.text(" - ").append(description.decoration(TextDecoration.ITALIC, false).color(
            TextColor.color(
                147, 63, 212
            )
        )))
        if(displayDamage != 0.0) {
            stack.loreLine(Component.text(" - ").append("ability.damage".asTranslate(displayDamage.toString().asText()).color(
                TextColor.color(
                    147, 63, 212
                )
            )))
        }
        if(displayRange != 0.0) {
            stack.loreLine(Component.text(" - ").append("ability.range".asTranslate(displayRange.toString().asText()).color(
                TextColor.color(
                    147, 63, 212
                )
            )))
        }
    }
}