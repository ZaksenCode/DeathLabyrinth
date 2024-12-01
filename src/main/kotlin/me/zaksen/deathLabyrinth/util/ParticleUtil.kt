package me.zaksen.deathLabyrinth.util

import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.Particle.DustOptions
import org.bukkit.Vibration
import org.bukkit.util.Vector
import kotlin.math.cos
import kotlin.math.sin
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

fun launchVibration(ticks: Int = 20, firstPos: Location, secondPos: Location, count: Int = 1) {
    val endingPos = Vibration.Destination.BlockDestination(secondPos)

    firstPos.world.spawnParticle(
        Particle.VIBRATION,
        firstPos,
        count,
        Vibration(endingPos, ticks)
    )
}

fun drawCircle(particle: Particle = Particle.DUST, location: Location, size: Double, color: Color = Color.WHITE, particleSize: Float = 5f) {
    var d = 0
    while (d <= 90) {
        val particleLoc = Location(location.world, location.x, location.y, location.z)
        particleLoc.x = location.x + cos(d.toDouble()) * size
        particleLoc.z = location.z + sin(d.toDouble()) * size
        location.world.spawnParticle<DustOptions>(particle, particleLoc, 1, DustOptions(color, particleSize))
        d += 1
    }
}