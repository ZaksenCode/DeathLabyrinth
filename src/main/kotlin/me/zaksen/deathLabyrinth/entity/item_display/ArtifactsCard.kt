package me.zaksen.deathLabyrinth.entity.item_display

import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import me.zaksen.deathLabyrinth.util.customModel
import net.minecraft.world.entity.Display.ItemDisplay
import net.minecraft.world.entity.EntityType
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.craftbukkit.CraftWorld
import org.bukkit.craftbukkit.inventory.CraftItemStack
import org.bukkit.inventory.ItemStack

class ArtifactsCard(location: Location, val artifact: Artifact): ItemDisplay(EntityType.ITEM_DISPLAY, (location.world as CraftWorld).handle) {

    init {
        deHighlight()

        this.absRotateTo(location.yaw, 0f)
        this.setPos(location.x, location.y, location.z)
    }

    fun highlight() {
        this.itemStack = CraftItemStack.asNMSCopy(ItemStack(Material.PAPER).customModel(1001))
    }

    fun deHighlight() {
        this.itemStack = CraftItemStack.asNMSCopy(ItemStack(Material.PAPER).customModel(1000))
    }
}