package me.zaksen.deathLabyrinth.entity.goal.ability

import me.zaksen.deathLabyrinth.entity.goal.AbilityGoal
import me.zaksen.deathLabyrinth.entity.projectile.EnemyWindBallEntity
import me.zaksen.deathLabyrinth.util.tryAddEntity
import net.minecraft.commands.arguments.EntityAnchorArgument
import net.minecraft.core.Direction
import net.minecraft.world.entity.Mob
import net.minecraft.world.phys.Vec3
import org.bukkit.Location
import org.bukkit.World
import org.joml.Quaternionf

class CastWindBallGoal(mob: Mob, private val damage: Double, private val range: Double): AbilityGoal(mob, 1) {

    override fun useAbility() {
        if(mob.target == null) return

        val world = mob.level().world

        val players = world.getNearbyPlayers(mob.position().toLocation(world), 24.0).sortedBy {
            it.location.distance(mob.position().toLocation(world))
        }

        if(players.isEmpty()) {
            return
        }

        val player = players.first()
        mob.lookAt(EntityAnchorArgument.Anchor.EYES, Vec3(player.x, player.y, player.z))

        val shotVelocity = mob.lookAngle.multiply(2.0, 2.0, 2.0).normalize()
        val spawnPosition = mob.eyePosition.add(shotVelocity.x, shotVelocity.y, shotVelocity.z)

        val projectile = EnemyWindBallEntity(spawnPosition.toLocation(world), damage, range)
        projectile.deltaMovement = Vec3(shotVelocity.x, shotVelocity.y, shotVelocity.z)
        projectile.setOwner(mob)
        world.tryAddEntity(projectile)
    }

}

fun Direction.multiply(amount: Float): Direction {
    this.rotation.mul(amount)
    return this
}

fun Direction.normalize(): Quaternionf {
    return this.rotation.normalize()
}

fun Vec3.toLocation(world: World): Location {
    return Location(world, x, y, z)
}