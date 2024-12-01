package me.zaksen.deathLabyrinth.entity.goal.ability

import me.zaksen.deathLabyrinth.entity.goal.AbilityGoal
import me.zaksen.deathLabyrinth.util.drawCircle
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.Mob
import net.minecraft.world.phys.Vec3
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class StunPlayersGoal(mob: Mob): AbilityGoal(mob, 15) {
    override fun useAbility() {
        val world = mob.level().world

        mob.addEffect(MobEffectInstance(
            MobEffects.MOVEMENT_SLOWDOWN,
            30,
            4
        ))

        drawCircle(location = Location(world, mob.x, mob.y + 1.25, mob.z), size = 14.0)

        val players = mob.level().world.getNearbyPlayers(Location(world, mob.x, mob.y, mob.z), 48.0).filter {
            mob.position().distanceTo(Vec3(it.x, it.y, it.z)) > 14.0
        }

        players.forEach {
            it.addPotionEffect(PotionEffect(
                PotionEffectType.SLOWNESS,
                60,
                4,
                false,
                false,
                false
            ))

            it.addPotionEffect(PotionEffect(
                PotionEffectType.DARKNESS,
                40,
                4,
                false,
                false,
                false
            ))

            it.playSound(
                it,
                Sound.ENTITY_ELDER_GUARDIAN_HURT,
                1.0f,
                1.0f
            )

            it.spawnParticle(
                Particle.POOF,
                it.location,
                15
            )
        }
    }
}