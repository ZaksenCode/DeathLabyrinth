package me.zaksen.deathLabyrinth.util

import net.minecraft.world.entity.Entity
import org.bukkit.World
import org.bukkit.craftbukkit.CraftWorld

fun World.tryAddEntity(entity: Entity) {
    (this as CraftWorld).handle.tryAddFreshEntityWithPassengers(entity)
}