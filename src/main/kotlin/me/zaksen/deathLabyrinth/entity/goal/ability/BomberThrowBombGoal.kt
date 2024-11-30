package me.zaksen.deathLabyrinth.entity.goal.ability

import me.zaksen.deathLabyrinth.entity.goal.FastAbilityGoal
import me.zaksen.deathLabyrinth.util.tryAddEntity
import net.minecraft.commands.arguments.EntityAnchorArgument
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.item.PrimedTnt
import net.minecraft.world.phys.Vec3

class BomberThrowBombGoal(mob: Mob): FastAbilityGoal(mob, 30) {

    override fun useAbility() {
        if(mob.target == null) return
        val target = mob.target!!
        val world = mob.level().world

        val tntPrimed = PrimedTnt(mob.level(), mob.x, mob.y + 2, mob.z, mob)

        mob.lookAt(EntityAnchorArgument.Anchor.EYES, Vec3(target.position().x, target.position().y, target.position().z))
        val throwVelocity = mob.lookAngle.multiply(2.5, 2.5, 2.5).normalize()

        tntPrimed.deltaMovement = throwVelocity
        world.tryAddEntity(tntPrimed)
    }

}