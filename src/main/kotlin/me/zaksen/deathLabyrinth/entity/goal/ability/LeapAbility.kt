package me.zaksen.deathLabyrinth.entity.goal.ability

import me.zaksen.deathLabyrinth.entity.goal.AbilityGoal
import net.minecraft.world.entity.Mob

class LeapAbility(mob: Mob): AbilityGoal(mob,4) {
    override fun useAbility() {
        mob.deltaMovement = mob.deltaMovement.scale(7.0)
    }
}