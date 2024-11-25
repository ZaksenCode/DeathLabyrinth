package me.zaksen.deathLabyrinth.menu.item

import me.zaksen.deathLabyrinth.item.ability.ItemAbility
import me.zaksen.deathLabyrinth.util.asText
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.builder.addLoreLines
import xyz.xenondevs.invui.item.builder.setDisplayName
import xyz.xenondevs.invui.item.impl.AbstractItem

class SynergyItem(
    private val icon: Material,
    private val customModel: Int,
    private val source: ItemAbility,
    private val with: ItemAbility,
    private val output: ItemAbility
): AbstractItem() {

    override fun getItemProvider(): ItemProvider {
        return ItemBuilder(icon).setCustomModelData(customModel).setDisplayName(output.name.color(TextColor.color(180, 180, 180))).addLoreLines(
            "=".asText().color(TextColor.color(100, 100, 100)),
            source.name.color(TextColor.color(100, 100, 100)),
            "+".asText().color(TextColor.color(100, 100, 100)),
            with.name.color(TextColor.color(100, 100, 100))
        )
    }

    override fun handleClick(p0: ClickType, p1: Player, p2: InventoryClickEvent) {

    }
}