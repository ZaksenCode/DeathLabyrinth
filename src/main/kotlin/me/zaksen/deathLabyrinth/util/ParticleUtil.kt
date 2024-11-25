package me.zaksen.deathLabyrinth.util

import io.papermc.paper.math.BlockPosition
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.SculkBlock
import net.minecraft.world.level.block.SculkSensorBlock
import net.minecraft.world.level.gameevent.BlockPositionSource
import net.minecraft.world.level.gameevent.vibrations.VibrationInfo
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.Vibration
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

fun launchVibration(ticks: Int = 20, firstPos: Location, secondPos: Location, count: Int = 1) {
    val endingPos = Vibration.Destination.BlockDestination(secondPos)

    firstPos.world.spawnParticle(
        Particle.VIBRATION,
        firstPos,
        count,
        Vibration(endingPos, ticks)
    )
}