package me.zaksen.deathLabyrinth.util

import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.util.Vector

fun Location.particleLine(particle: Particle, to: Location, space: Double = 0.2) {
    val distance = this.distance(to)

    val p1 = this.toVector()
    val p2 = to.toVector()

    val vector: Vector = p2.clone().subtract(p1).normalize().multiply(space)

    var covered = 0.0

    while (covered < distance) {
        world.spawnParticle(particle, p1.x, p1.y, p1.z, 0, 0.1, 0.1, 0.1)
        covered += space
        p1.add(vector)
    }
}