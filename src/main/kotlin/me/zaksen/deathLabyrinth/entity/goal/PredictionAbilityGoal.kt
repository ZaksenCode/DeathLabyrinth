package me.zaksen.deathLabyrinth.entity.goal

import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.ai.goal.Goal

abstract class PredictionAbilityGoal(
    val mob: Mob,
    val abilityPeriodTicks: Int,
    val predictionTicks: Int,
    val singlePrediction: Boolean = false
): Goal() {

    protected var lastCheck = 0

    override fun canUse(): Boolean {
        abilityTick()
        lastCheck++

        if(singlePrediction) {
            if(lastCheck > predictionTicks && lastCheck <= predictionTicks + 1) {
                predictionAbility()
            }
        } else {
            if (lastCheck > predictionTicks) {
                predictionAbility()
            }
        }

        if(lastCheck > abilityPeriodTicks) {
            useAbility()
            lastCheck = 0
        }

        return lastCheck >= abilityPeriodTicks
    }

    abstract fun predictionAbility()
    abstract fun useAbility()
    open fun abilityTick() { }
}