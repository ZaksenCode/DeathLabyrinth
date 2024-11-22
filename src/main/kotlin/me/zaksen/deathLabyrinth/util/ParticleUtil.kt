package me.zaksen.deathLabyrinth.util

import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.util.Vector
import kotlin.random.Random

fun Location.particleLine(particle: Particle, to: Location, space: Double = 0.2, offset: Double = 0.0) {
    val random = Random.Default
    val distance = this.distance(to)

    val p1 = this.toVector()
    val p2 = to.toVector()

    val vector: Vector = p2.clone().subtract(p1).normalize().multiply(space)

    var covered = 0.0

    while (covered < distance) {
        if(offset > 0) {
            world.spawnParticle(
                particle,
                p1.x + random.nextDouble(-offset, offset),
                p1.y + random.nextDouble(-offset, offset),
                p1.z + random.nextDouble(-offset, offset),
                0,
                0.0,
                0.0,
                0.0
            )
        } else {
            world.spawnParticle(
                particle,
                p1.x,
                p1.y,
                p1.z,
                0,
                0.0,
                0.0,
                0.0
            )
        }
        covered += space
        p1.add(vector)
    }
}