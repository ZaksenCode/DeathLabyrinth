package me.zaksen.deathLabyrinth.game.room.exit.choice

import me.zaksen.deathLabyrinth.game.room.RoomType
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemStack

data class Choice(
    val name: Component,
    val length: Int = 3,
    val roomTypes: Set<RoomType> = setOf(RoomType.NORMAL),
    val displayStack: ItemStack
)
