package me.zaksen.deathLabyrinth.util

import org.bukkit.Location
import org.bukkit.util.Vector
import kotlin.math.atan2

fun getAngle(point1: Vector, point2: Vector): Float {
    val dx: Double = point2.x - point1.x
    val dz: Double = point2.z - point1.z
    var angle = Math.toDegrees(atan2(dz, dx)).toFloat() - 90
    if (angle < 0) {
        angle += 360.0f
    }
    return angle
}


fun Location.getAngle(to: Location): Float {
    val dx: Double = to.x - this.x
    val dz: Double = to.z - this.z
    var angle = Math.toDegrees(atan2(dz, dx)).toFloat() - 90
    if (angle < 0) {
        angle += 360.0f
    }
    return angle
}