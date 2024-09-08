package me.zaksen.deathLabyrinth.entity.goal.ability

import me.zaksen.deathLabyrinth.entity.goal.AbilityGoal
import net.minecraft.world.entity.Mob
import org.bukkit.Location
import org.bukkit.entity.EntityType

class BomberAbilityGoal(mob: Mob): AbilityGoal(mob, 400) {

    override fun useAbility() {
        val world = mob.level().world

        world.spawnEntity(Location(world, mob.x + 5, mob.y + 1, mob.z), EntityType.TNT)
        world.spawnEntity(Location(world, mob.x - 5, mob.y + 1, mob.z), EntityType.TNT)
        world.spawnEntity(Location(world, mob.x, mob.y + 1, mob.z + 5), EntityType.TNT)
        world.spawnEntity(Location(world, mob.x, mob.y + 1, mob.z - 5), EntityType.TNT)
    }

}