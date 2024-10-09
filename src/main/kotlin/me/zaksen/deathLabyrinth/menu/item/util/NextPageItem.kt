package me.zaksen.deathLabyrinth.menu.item.util

import me.zaksen.deathLabyrinth.util.asText
import me.zaksen.deathLabyrinth.util.asTranslate
import me.zaksen.deathLabyrinth.util.toWrapper
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import xyz.xenondevs.invui.gui.PagedGui
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.controlitem.PageItem

class NextPageItem: PageItem(true) {
    override fun getItemProvider(gui: PagedGui<*>): ItemProvider {
        val builder = ItemBuilder(Material.PAPER)
        builder.setDisplayName("ui.button.page.next_page"
            .asTranslate()
            .color(TextColor.color(TextColor.color(50,205,50)))
            .toWrapper()
        )

        if(gui.hasNextPage()) {
            builder.setCustomModelData(252)
            builder.addLoreLines("ui.button.page.go_to"
                .asTranslate("${gui.currentPage + 2}/${gui.pageAmount}".asText())
                .color(TextColor.color(TextColor.color(34,139,34)))
                .toWrapper()
            )
        } else {
            builder.setCustomModelData(253)
            builder.addLoreLines("ui.button.page.last_page"
                .asTranslate()
                .color(TextColor.color(TextColor.color(34,139,34)))
                .toWrapper()
            )
        }

        return builder
    }
}