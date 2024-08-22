package me.zaksen.deathLabyrinth.entity.projectile

import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.projectile.windcharge.WindCharge
import net.minecraft.world.level.entity.EntityTypeTest
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3
import org.bukkit.Location
import org.bukkit.craftbukkit.CraftWorld

// Имитировать эффект взрыва вместо реального
class FrostBallEntity(location: Location): WindCharge(EntityType.WIND_CHARGE, (location.getWorld() as CraftWorld).handle) {

    init {
        this.setPos(location.x, location.y, location.z)
    }

    override fun explode(pos: Vec3?) {
        super.explode(pos)

        val entities = level().getEntities(
            EntityTypeTest.forClass(LivingEntity::class.java),
            AABB(
            x - 1.0,
            y - 1.0,
            z - 1.0,
            x + 1.0,
            y + 1.0,
            z + 1.0
            )
        ) {
            it.isAlive && it !is Player
        }

        for (entity in entities) {
            entity.hurt(this.damageSources().freeze(),4.0f)
            entity.addEffect(MobEffectInstance(
                MobEffects.MOVEMENT_SLOWDOWN,
                15
            ), this.effectSource)
        }
    }
}