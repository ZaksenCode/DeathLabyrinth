package me.zaksen.deathLabyrinth.menu.item

import me.zaksen.deathLabyrinth.item.CustomItem
import me.zaksen.deathLabyrinth.item.ItemsController
import me.zaksen.deathLabyrinth.keys.PluginKeys
import me.zaksen.deathLabyrinth.util.asTranslate
import me.zaksen.deathLabyrinth.util.toWrapper
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.AbstractItem

class SlotItem: AbstractItem() {
    var hasItem = false
    var slotItem: ItemStack = ItemStack.of(Material.GRAY_STAINED_GLASS_PANE)
    var outputSlot: OutputSlot? = null

    override fun getItemProvider(): ItemProvider {
         return if(hasItem) { ItemBuilder(slotItem).setLore(slotItem.lore()!!.map{ it.toWrapper() }) } else {
             ItemBuilder(slotItem).setDisplayName("ui.slot.empty.name".asTranslate().color(TextColor.color(128, 128, 128)).toWrapper()).addLoreLines(
                 "ui.slot.empty.description".asTranslate().color(TextColor.color(96, 96, 96)).toWrapper()
             )
         }
    }

    override fun handleClick(click: ClickType, player: Player, event: InventoryClickEvent) {
        if(hasItem) {
            if(player.itemOnCursor.type != Material.AIR) {
                val temp = slotItem.clone()

                slotItem = player.itemOnCursor.clone()
                player.setItemOnCursor(temp)
                hasItem = true
                outputSlot?.tryToMixItems()
            } else {
                val temp = slotItem.clone()

                slotItem = ItemStack.of(Material.GRAY_STAINED_GLASS_PANE)
                player.setItemOnCursor(temp)
                hasItem = false
                outputSlot?.clearRecipe()
            }
        } else {
            if(player.itemOnCursor.type != Material.AIR) {
                slotItem = player.itemOnCursor.clone()
                player.setItemOnCursor(ItemStack.of(Material.AIR))
                hasItem = true
                outputSlot?.tryToMixItems()
            }
        }
        notifyWindows()
    }

    fun setItem(stack: ItemStack) {
        slotItem = stack
        hasItem = true
        notifyWindows()
    }

    fun clearItem() {
        hasItem = false
        slotItem = ItemStack.of(Material.GRAY_STAINED_GLASS_PANE)
        notifyWindows()
    }

    private fun isCustomItem(): Boolean {
        return slotItem.hasItemMeta() && slotItem.itemMeta.persistentDataContainer.has(PluginKeys.customItemKey)
    }

    fun getCustomItem(): CustomItem? {
        if (!isCustomItem()) return null
        val itemId = slotItem.itemMeta.persistentDataContainer.get(PluginKeys.customItemKey, PersistentDataType.STRING)!!
        return ItemsController.get(itemId)
    }

    fun setupOutputSlot(slot: OutputSlot) {
        this.outputSlot = slot
    }
}