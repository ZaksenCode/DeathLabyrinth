package me.zaksen.deathLabyrinth.entity.goal

import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.ai.goal.Goal

abstract class AbilityGoal(val mob: Mob, val abilityPeriod: Int): Goal() {

    private var currentAbilityTick = 0

    override fun canUse(): Boolean {
        return true
    }

    override fun tick() {
        if(currentAbilityTick >= abilityPeriod) {
            useAbility()
            currentAbilityTick = 0
            return
        }
        currentAbilityTick++
    }

    abstract fun useAbility()
}