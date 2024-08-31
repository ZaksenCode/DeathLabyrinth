package me.zaksen.deathLabyrinth.util

import fr.skytasul.guardianbeam.Laser
import org.bukkit.Location
import org.bukkit.plugin.Plugin

// TODO Guardian lib not updated for 1.21. Do not use until update
object LaserManager {

    private lateinit var plugin: Plugin

    fun setup(plugin: Plugin) {
        this.plugin = plugin
    }

    fun spawnLazer(from: Location, to: Location, duration: Int, distance: Int): Laser {
        val laser: Laser = Laser.GuardianLaser(from, to, duration, distance)
        laser.start(plugin)
        return laser
    }
}