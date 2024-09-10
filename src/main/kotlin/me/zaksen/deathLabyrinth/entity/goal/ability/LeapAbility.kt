package me.zaksen.deathLabyrinth.entity.goal.ability

import me.zaksen.deathLabyrinth.entity.goal.AbilityGoal
import net.minecraft.world.entity.Mob

class LeapAbility(mob: Mob): AbilityGoal(mob,6) {
    override fun useAbility() {
        mob.deltaMovement = mob.deltaMovement.scale(5.0)
    }
}