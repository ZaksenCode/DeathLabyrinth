package me.zaksen.deathLabyrinth.menu.item

import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import me.zaksen.deathLabyrinth.util.asText
import me.zaksen.deathLabyrinth.util.toWrapper
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.builder.setDisplayName
import xyz.xenondevs.invui.item.impl.AbstractItem

class ArtifactItem(private val artifact: Artifact): AbstractItem() {
    override fun getItemProvider(): ItemProvider {
        val stack = artifact.asItemStack()
        return ItemBuilder(stack)
            .setDisplayName(artifact.asItemStack().displayName().append(" x${artifact.count}".asText()))
            .setLore(stack.lore()!!.map { it.toWrapper() })
    }

    override fun handleClick(p0: ClickType, p1: Player, p2: InventoryClickEvent) {

    }
}