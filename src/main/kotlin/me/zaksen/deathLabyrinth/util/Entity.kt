package me.zaksen.deathLabyrinth.util

import me.zaksen.deathLabyrinth.entity.friendly.FriendlyEntity
import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.game.room.Room
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import org.bukkit.Location
import org.bukkit.entity.Player

fun Entity.spawnClone(room: Room, requireKill: Boolean = false) {
    val newEntity = this::class.java.getDeclaredConstructor(Location::class.java).newInstance(Location(
        this.level().world, this.x, this.y, this.z
    ))

    EventManager.callEntityCloneSpawnEvent(room, newEntity, requireKill)
}

fun LivingEntity.spawnFriendlyClone(player: Player) {
    val newEntity = this::class.java.getDeclaredConstructor(Location::class.java).newInstance(Location(
        this.level().world, this.x, this.y, this.z
    ))

    if(newEntity is FriendlyEntity) {
        EventManager.callPlayerSummonFriendlyEntityCloneEvent(player, newEntity)
    } else {
        throw IllegalStateException("Entity is not friendly entity")
    }
}