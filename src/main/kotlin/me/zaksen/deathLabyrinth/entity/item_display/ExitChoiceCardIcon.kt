package me.zaksen.deathLabyrinth.entity.item_display

import me.zaksen.deathLabyrinth.game.room.exit.choice.Choice
import net.minecraft.world.entity.Display.ItemDisplay
import net.minecraft.world.entity.EntityType
import org.bukkit.Location
import org.bukkit.craftbukkit.CraftWorld
import org.bukkit.craftbukkit.inventory.CraftItemStack

class ExitChoiceCardIcon(location: Location, choice: Choice): ItemDisplay(EntityType.ITEM_DISPLAY, (location.world as CraftWorld).handle) {

    init {
        this.itemStack = CraftItemStack.asNMSCopy(choice.displayStack)

        this.absRotateTo(location.yaw + 180f, 0f)
        this.setPos(location.x, location.y, location.z)
    }

}