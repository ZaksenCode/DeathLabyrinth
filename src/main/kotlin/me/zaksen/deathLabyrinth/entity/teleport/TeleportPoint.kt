package me.zaksen.deathLabyrinth.entity.teleport

import me.zaksen.deathLabyrinth.entity.room.RoomCycle
import net.minecraft.world.entity.Display.ItemDisplay
import net.minecraft.world.entity.EntityType
import org.bukkit.Location
import org.bukkit.craftbukkit.CraftWorld

open class TeleportPoint(location: Location): ItemDisplay(EntityType.ITEM_DISPLAY, (location.world as CraftWorld).handle), RoomCycle {

    init {
        this.setPos(location.x, location.y, location.z)
    }

    open fun getTeleportId(): Int {
        return 1
    }
}