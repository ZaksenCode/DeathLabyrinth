package me.zaksen.deathLabyrinth.entity.goal.ability

import me.zaksen.deathLabyrinth.entity.goal.PredictionAbilityGoal
import me.zaksen.deathLabyrinth.entity.teleport.BomberReturnPoint
import me.zaksen.deathLabyrinth.entity.teleport.TeleportPoint
import me.zaksen.deathLabyrinth.util.drawCircle
import net.minecraft.world.entity.Mob
import net.minecraft.world.phys.AABB
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.entity.EntityType
import kotlin.random.Random

class BomberTeleportAbilityGoal(mob: Mob): PredictionAbilityGoal(mob, 320, 290, true) {

    private var returning = false
    private val tntSpawns: MutableList<Location> = mutableListOf()

    private var tickDrawTime = 0
    private val tickDrawTimeMax = 5

    override fun abilityTick() {
        if(tntSpawns.isNotEmpty()) {
            if(tickDrawTime < tickDrawTimeMax) {
                tickDrawTime++
            } else {
                tntSpawns.forEach {
                    drawCircle(
                        location = Location(it.world, it.x, it.y - 12, it.z),
                        size = 3.0,
                        color = Color.RED.mixColors(Color.PURPLE),
                        particleSize = 1.25f,
                        incrementAmount = 3.0
                    )
                }
                tickDrawTime = 0
            }
        }

        if(lastCheck >= 100 && returning) {
            val teleports = mob.level().getEntitiesOfClass(BomberReturnPoint::class.java, AABB(
                    mob.x - 50.0,
                    mob.y - 50.0,
                    mob.z - 50.0,
                    mob.x + 50.0,
                    mob.y + 50.0,
                    mob.z + 50.0
                )
            )

            val teleport = teleports.randomOrNull()
            if(teleport != null) mob.setPos(teleport.x, teleport.y, teleport.z)

            returning = false
        }
    }

    override fun predictionAbility() {
        val world = mob.level().world

        val minX = mob.x - 15
        val maxX = mob.x + 15
        val y = mob.y + 12
        val minZ = mob.z - 15
        val maxZ = mob.z + 15

        for(i in 0..20) {
            tntSpawns.add(Location(
                world,
                Random.Default.nextDouble(minX, maxX),
                y,
                Random.Default.nextDouble(minZ, maxZ)
            ))
        }
    }

    override fun useAbility() {
        tntSpawns.forEach {
            it.world.spawnEntity(
                it,
                EntityType.TNT
            )
        }

        tntSpawns.clear()
        tickDrawTime = 0

        val teleports = mob.level().getEntitiesOfClass(TeleportPoint::class.java, AABB(
            mob.x - 50.0,
            mob.y - 50.0,
            mob.z - 50.0,
            mob.x + 50.0,
            mob.y + 50.0,
            mob.z + 50.0
        ))

        val teleport = teleports.randomOrNull()
        if(teleport != null) mob.setPos(teleport.x, teleport.y, teleport.z)

        returning = true
    }
}