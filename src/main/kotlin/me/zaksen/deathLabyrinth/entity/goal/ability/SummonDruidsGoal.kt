package me.zaksen.deathLabyrinth.entity.goal.ability

import me.zaksen.deathLabyrinth.entity.goal.AbilityGoal
import me.zaksen.deathLabyrinth.entity.skeleton.DespawningDruidEntity
import me.zaksen.deathLabyrinth.util.tryAddEntity
import net.minecraft.world.entity.Mob
import org.bukkit.Location

class SummonDruidsGoal(mob: Mob): AbilityGoal(mob, 30) {

    override fun useAbility() {
        val world = mob.level().world

        val entity = DespawningDruidEntity(Location(world, mob.x + 6, mob.y + 1, mob.z))
        world.tryAddEntity(entity)
        val entity2 = DespawningDruidEntity(Location(world, mob.x - 6, mob.y + 1, mob.z))
        world.tryAddEntity(entity2)

        val entity3 = DespawningDruidEntity(Location(world, mob.x, mob.y + 1, mob.z + 6))
        world.tryAddEntity(entity3)
        val entity4 = DespawningDruidEntity(Location(world, mob.x, mob.y + 1, mob.z - 6))
        world.tryAddEntity(entity4)
    }
}
