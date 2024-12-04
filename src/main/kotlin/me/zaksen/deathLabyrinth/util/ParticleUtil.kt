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

fun particleLine(particle: Particle, from: Location, to: Location, space: Double = 0.2, offset: Double = 0.0) {
    val random = Random.Default
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
    location: Location, size: Double,
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

fun drawLine(
    start: Location,
    end: Location,
    gap: Double = 0.2,
    particle: Particle = Particle.DUST,
    color: Color = Color.WHITE,
    particleSize: Float = 1.0f,
) {
    drawLine(start.world, start.x, start.y, start.z, end.x, end.y, end.z, gap, particle, color, particleSize)
}

fun drawLine(
    world: World,
    startX: Double,
    startY: Double,
    startZ: Double,
    endX: Double,
    endY: Double,
    endZ: Double,
    gap: Double = 0.2,
    particle: Particle = Particle.DUST,
    color: Color = Color.WHITE,
    particleSize: Float = 1.0f,
) {
    val dir = Vector(endX - startX, endY - startY, endZ - startZ)
    var i = 0.0

    while(i < 10) {
        dir.multiply(i)
        world.spawnParticle(
            particle,
            startX + dir.x,
            startY + dir.y,
            startZ + dir.z,
            1,
            DustOptions(color, particleSize)
        )
        dir.normalize()
        i += gap
    }
}

fun drawSquare(
    start: Location,
    end: Location,
    gap: Double = 0.3
) {
    start.particleLine(Particle.ELECTRIC_SPARK, Location(end.world, end.x, start.y, start.z))
    start.particleLine(Particle.ELECTRIC_SPARK, Location(end.world, start.x, start.y, end.z))
    start.particleLine(Particle.ELECTRIC_SPARK, Location(end.world, start.x, end.y, start.z))

//    drawLine(start.world, start.x, start.y, start.z, end.x, start.y, start.z, gap)
//    drawLine(start.world, start.x, start.y, start.z, start.x, start.y, end.z, gap)
//    drawLine(start.world, end.x, start.y, start.z, start.x, start.y, end.z, gap)
//    drawLine(start.world, start.x, start.y, end.z, end.x, start.y, start.z, gap)
//
//    drawLine(start.world, start.x, start.y, start.z, start.x, end.y, start.z, gap)
//    drawLine(start.world, end.x, start.y, start.z, end.x, end.y, start.z, gap)
//    drawLine(start.world, start.x, start.y, end.z, start.x, end.y, end.z, gap)
//    drawLine(start.world, end.x, start.y, end.z, end.x, end.y, end.z, gap)
//
//    drawLine(start.world, start.x, end.y, start.z, end.x, end.y, start.z, gap)
//    drawLine(start.world, start.x, end.y, start.z, start.x, end.y, end.z, gap)
//    drawLine(start.world, end.x, end.y, start.z, start.x, end.y, end.z, gap)
//    drawLine(start.world, start.x, end.y, end.z, end.x, end.y, start.z, gap)
}

// TODO - Check if drawLine function is working
fun drawSquare(
    world: World,
    startX: Double,
    startY: Double,
    startZ: Double,
    endX: Double,
    endY: Double,
    endZ: Double,
    gap: Double = 0.3
) {
    drawLine(world, startX, startY, startZ, endX, startY, startZ, gap)
    drawLine(world, startX, startY, startZ, startX, startY, endZ, gap)
    drawLine(world, endX, startY, startZ, startX, startY, endZ, gap)
    drawLine(world, startX, startY, endZ, endX, startY, startZ, gap)

    drawLine(world, startX, startY, startZ, startX, endY, startZ, gap)
    drawLine(world, endX, startY, startZ, endX, endY, startZ, gap)
    drawLine(world, startX, startY, endZ, startX, endY, endZ, gap)
    drawLine(world, endX, startY, endZ, endX, endY, endZ, gap)

    drawLine(world, startX, endY, startZ, endX, endY, startZ, gap)
    drawLine(world, startX, endY, startZ, startX, endY, endZ, gap)
    drawLine(world, endX, endY, startZ, startX, endY, endZ, gap)
    drawLine(world, startX, endY, endZ, endX, endY, startZ, gap)
}