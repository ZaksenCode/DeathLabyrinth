package me.zaksen.deathLabyrinth.util

import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.Particle.DustOptions
import org.bukkit.Vibration
import org.bukkit.World
import org.bukkit.util.Vector
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

val random = Random.Default

fun Location.particleLine(particle: Particle, to: Location, space: Double = 0.2, offset: Double = 0.0) {
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

fun particleLine(particle: Particle, from: Location, to: Location, space: Double = 0.2, offset: Double = 0.0) {
    val distance = from.distance(to)

    val p1 = from.toVector()
    val p2 = to.toVector()

    val vector: Vector = p2.clone().subtract(p1).normalize().multiply(space)

    var covered = 0.0

    while (covered < distance) {
        if(offset > 0) {
            from.world.spawnParticle(
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
            from.world.spawnParticle(
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

fun <T> particleLine(particle: Particle, world: World, from: Vector, to: Vector, space: Double = 0.2, offset: Double = 0.0, data: T? = null) {
    val distance = from.distance(to)

    val vector: Vector = to.clone().subtract(from).normalize().multiply(space)

    var covered = 0.0

    while (covered < distance) {
        if (offset > 0) {
            world.spawnParticle(
                particle,
                from.x + random.nextDouble(-offset, offset),
                from.y + random.nextDouble(-offset, offset),
                from.z + random.nextDouble(-offset, offset),
                0,
                0.0,
                0.0,
                0.0,
                data
            )
        } else {
            world.spawnParticle(
                particle,
                from.x,
                from.y,
                from.z,
                0,
                0.0,
                0.0,
                0.0,
                data
            )
        }
        covered += space
        from.add(vector)
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

fun drawCircle(
    particle: Particle = Particle.DUST,
    location: Location,
    size: Double,
    color: Color = Color.WHITE,
    particleSize: Float = 5f,
    incrementAmount: Double = 1.0
) {
    var d = 0.0
    while (d <= 90) {
        val particleLoc = Location(location.world, location.x, location.y, location.z)
        particleLoc.x = location.x + cos(d) * size
        particleLoc.z = location.z + sin(d) * size
        location.world.spawnParticle<DustOptions>(particle, particleLoc, 1, DustOptions(color, particleSize))
        d += incrementAmount
    }
}

fun drawCircle(
    particle: Particle = Particle.DUST,
    world: World,
    x: Double,
    y: Double,
    z: Double,
    size: Double,
    color: Color = Color.WHITE,
    particleSize: Float = 5f,
    incrementAmount: Double = 1.0
) {
    var drawX: Double
    var drawZ: Double

    var d = 0.0
    while (d <= 90) {
        drawX = x + cos(d) * size
        drawZ = z + sin(d) * size
        world.spawnParticle<DustOptions>(particle, drawX, y, drawZ, 1, DustOptions(color, particleSize))
        d += incrementAmount
    }
}


fun drawSquare(
    start: Location,
    end: Location,
    gap: Double = 0.3
) {
    particleLine(Particle.ELECTRIC_SPARK, end.world, Vector(start.x, start.y, start.z), Vector(end.x, start.y, start.z), space = gap, data = null)
    particleLine(Particle.ELECTRIC_SPARK, end.world, Vector(start.x, start.y, start.z), Vector(start.x, start.y, end.z), space = gap, data = null)
    particleLine(Particle.ELECTRIC_SPARK, end.world, Vector(start.x, start.y, start.z), Vector(start.x, end.y, start.z), space = gap, data = null)
    particleLine(Particle.ELECTRIC_SPARK, end.world, Vector(start.x, start.y, end.z), Vector(end.x, start.y, start.z), space = gap, data = null)

    particleLine(Particle.ELECTRIC_SPARK, end.world, Vector(start.x, start.y, start.z), Vector(start.x, end.y, start.z), space = gap, data = null)
    particleLine(Particle.ELECTRIC_SPARK, end.world, Vector(end.x, start.y, start.z), Vector(end.x, end.y, end.z), space = gap, data = null)
    particleLine(Particle.ELECTRIC_SPARK, end.world, Vector(start.x, start.y, end.z), Vector(start.x, end.y, end.z), space = gap, data = null)
    particleLine(Particle.ELECTRIC_SPARK, end.world, Vector(end.x, start.y, end.z), Vector(end.x, end.y, end.z), space = gap, data = null)

    particleLine(Particle.ELECTRIC_SPARK, end.world, Vector(start.x, end.y, start.z), Vector(end.x, end.y, start.z), space = gap, data = null)
    particleLine(Particle.ELECTRIC_SPARK, end.world, Vector(start.x, end.y, start.z), Vector(start.x, end.y, end.z), space = gap, data = null)
    particleLine(Particle.ELECTRIC_SPARK, end.world, Vector(end.x, end.y, start.z), Vector(start.x, end.y, end.z), space = gap, data = null)
    particleLine(Particle.ELECTRIC_SPARK, end.world, Vector(start.x, end.y, end.z), Vector(end.x, end.y, start.z), space = gap, data = null)
}

fun drawSquare(
    world: World,
    startX: Double,
    startY: Double,
    startZ: Double,
    endX: Double,
    endY: Double,
    endZ: Double,
    gap: Double = 0.3,
    particle: Particle = Particle.WAX_ON
) {
    // Down square
    particleLine(particle, world, Vector(startX, startY, startZ), Vector(endX, startY, startZ), space = gap, data = null)
    particleLine(particle, world, Vector(startX, startY, startZ), Vector(startX, startY, endZ), space = gap, data = null)
    particleLine(particle, world, Vector(endX, startY, startZ), Vector(endX, startY, endZ), space = gap, data = null)
    particleLine(particle, world, Vector(startX, startY, endZ), Vector(endX, startY, endZ), space = gap, data = null)
    // Up square
    particleLine(particle, world, Vector(startX, endY, startZ), Vector(endX, endY, startZ), space = gap, data = null)
    particleLine(particle, world, Vector(startX, endY, startZ), Vector(startX, endY, endZ), space = gap, data = null)
    particleLine(particle, world, Vector(endX, endY, startZ), Vector(endX, endY, endZ), space = gap, data = null)
    particleLine(particle, world, Vector(startX, endY, endZ), Vector(endX, endY, endZ), space = gap, data = null)
    // Side square (4)
    particleLine(particle, world, Vector(startX, startY, startZ), Vector(startX, endY, startZ), space = gap, data = null)
    particleLine(particle, world, Vector(endX, startY, startZ), Vector(endX, endY, startZ), space = gap, data = null)
    particleLine(particle, world, Vector(startX, startY, endZ), Vector(startX, endY, endZ), space = gap, data = null)
    particleLine(particle, world, Vector(endX, startY, endZ), Vector(endX, endY, endZ), space = gap, data = null)
}