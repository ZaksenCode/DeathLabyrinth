package me.zaksen.deathLabyrinth.menu.item

import me.zaksen.deathLabyrinth.data.PlayerData
import me.zaksen.deathLabyrinth.util.toWrapper
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.AbstractItem

class AccessorySlotItem(private val slot: Int, val playerData: PlayerData): AbstractItem() {
    private var hasItem = false
    private var slotItem: ItemStack = ItemStack.of(Material.AIR)

    override fun getItemProvider(): ItemProvider {
         return if(hasItem) {
             ItemBuilder(slotItem).setLore(slotItem.lore()!!.map{ it.toWrapper() })
         } else {
             ItemBuilder(Material.AIR)
         }
    }

    override fun handleClick(click: ClickType, player: Player, event: InventoryClickEvent) {
        if(hasItem) {
            if(player.itemOnCursor.type != Material.AIR) {
                if(playerData.accessories.tryPutStack(slot, player.itemOnCursor)) {
                    val temp = slotItem.clone()
                    setItem(player.itemOnCursor.clone())
                    player.setItemOnCursor(temp)
                }
            } else {
                val temp = slotItem.clone()

                clearItem()
                player.setItemOnCursor(temp)

                playerData.accessories.removeStack(slot)
            }
        } else {
            if(player.itemOnCursor.type != Material.AIR) {
                if(playerData.accessories.tryPutStack(slot, player.itemOnCursor)) {
                    setItem(player.itemOnCursor.clone())
                    player.setItemOnCursor(null)
                }
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
        slotItem = ItemStack.of(Material.AIR)
        notifyWindows()
    }

    fun setItemAndCheck(stack: ItemStack?): AccessorySlotItem {
        if(stack != null && stack.type != Material.AIR) {
            setItem(stack)
        }

        return this
    }
}