package me.zaksen.deathLabyrinth.entity.goal.ability

import me.zaksen.deathLabyrinth.entity.goal.AbilityGoal
import me.zaksen.deathLabyrinth.entity.projectile.EnemyWindBallEntity
import me.zaksen.deathLabyrinth.util.tryAddEntity
import net.minecraft.commands.arguments.EntityAnchorArgument
import net.minecraft.world.entity.Mob
import net.minecraft.world.phys.Vec3

class LeapAbility(mob: Mob): AbilityGoal(mob,4) {
    override fun useAbility() {
        if(mob.target == null) return
        val target = mob.target!!

        mob.lookAt(EntityAnchorArgument.Anchor.EYES, Vec3(target.position().x, target.position().y + 1.0, target.position().z))
        val leapVelocity = mob.lookAngle.multiply(3.0, 3.0, 3.0).normalize()

        mob.deltaMovement = leapVelocity
    }
}