package me.zaksen.deathLabyrinth.entity.goal.ability

import me.zaksen.deathLabyrinth.entity.goal.AbilityGoal
import me.zaksen.deathLabyrinth.entity.teleport.BomberReturnPoint
import me.zaksen.deathLabyrinth.entity.teleport.TeleportPoint
import net.minecraft.world.entity.Mob
import net.minecraft.world.phys.AABB
import org.bukkit.Location
import org.bukkit.entity.EntityType
import kotlin.random.Random

class BomberTeleportAbilityGoal(mob: Mob): AbilityGoal(mob, 16) {

    private var returning = false
    private var returningSec = 0

    override fun useAbility() {
        spawnTNT()
        val teleports = mob.level().getEntitiesOfClass(TeleportPoint::class.java, AABB(
            mob.x - 50.0,
            mob.y - 50.0,
            mob.z - 50.0,
            mob.x + 50.0,
            mob.y + 50.0,
            mob.z + 50.0
        ))

        if(teleports.isEmpty()) {
            return
        }

        val teleport = teleports.random()

        mob.setPos(teleport.x, teleport.y, teleport.z)
        returning = true
    }

    override fun abilitySecond() {
        if(returning) {
            if (returningSec >= MAX_RETURN_SEC) {
                val teleports = mob.level().getEntitiesOfClass(
                    BomberReturnPoint::class.java, AABB(
                        mob.x - 50.0,
                        mob.y - 50.0,
                        mob.z - 50.0,
                        mob.x + 50.0,
                        mob.y + 50.0,
                        mob.z + 50.0
                    )
                )

                if (teleports.isEmpty()) {
                    return
                }

                val teleport = teleports.first()

                mob.setPos(teleport.x, teleport.y, teleport.z)
                returning = false
            } else {
                returningSec++
            }
        }

        super.abilitySecond()
    }

    fun spawnTNT() {
        val world = mob.level().world

        val minX = mob.x - 12
        val maxX = mob.x + 12
        val y = mob.y + 12
        val minZ = mob.z - 12
        val maxZ = mob.z + 12

        for(i in 0..20) {
            world.spawnEntity(Location(
                world,
                Random.Default.nextDouble(minX, maxX),
                y,
                Random.Default.nextDouble(minZ, maxZ)),
                EntityType.TNT
            )
        }
    }

    companion object {
        private const val MAX_RETURN_SEC = 7
    }
}