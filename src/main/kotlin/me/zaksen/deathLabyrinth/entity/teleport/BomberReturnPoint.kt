package me.zaksen.deathLabyrinth.entity.teleport

import org.bukkit.Location

class BomberReturnPoint(location: Location): TeleportPoint(location) {

    override fun getTeleportId(): Int {
        return 2
    }
}