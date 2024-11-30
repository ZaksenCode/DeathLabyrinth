package me.zaksen.deathLabyrinth.entity.goal

import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.ai.goal.Goal

abstract class FastAbilityGoal(val mob: Mob, val abilityPeriodTicks: Int): Goal() {

    private var lastCheck = 0

    override fun canUse(): Boolean {
        lastCheck++

        if(lastCheck > abilityPeriodTicks) {
            useAbility()
            lastCheck = 0
        }

        return lastCheck >= abilityPeriodTicks - 1
    }

    abstract fun useAbility()
}