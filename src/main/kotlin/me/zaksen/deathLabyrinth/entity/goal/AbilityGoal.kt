package me.zaksen.deathLabyrinth.entity.goal

import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.ai.goal.Goal

abstract class AbilityGoal(val mob: Mob, val abilityPeriod: Int): Goal() {

    private var lastCheck = 0
    private var currentAbilitySec = 0

    override fun canUse(): Boolean {
        lastCheck++
        if(lastCheck > 20) {
            abilitySecond()
            lastCheck = 0
        }
        return lastCheck >= 20
    }

    open fun abilitySecond() {
        if(currentAbilitySec >= abilityPeriod) {
            useAbility()
            currentAbilitySec = 0
            return
        }
        currentAbilitySec++
    }

    abstract fun useAbility()
}