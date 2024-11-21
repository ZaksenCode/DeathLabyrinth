package me.zaksen.deathLabyrinth.entity.projectile

import net.minecraft.core.particles.ParticleTypes
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.projectile.windcharge.WindCharge
import net.minecraft.world.level.ExplosionDamageCalculator
import net.minecraft.world.level.Level
import net.minecraft.world.level.SimpleExplosionDamageCalculator
import net.minecraft.world.phys.Vec3
import org.bukkit.Location
import org.bukkit.craftbukkit.CraftWorld
import java.util.*

class WindChargeEntity(location: Location): WindCharge(EntityType.WIND_CHARGE, (location.world as CraftWorld).handle) {
    init {
        this.setPos(location.x, location.y, location.z)
    }

    override fun explode(pos: Vec3?) {
        if(pos == null) return

        level().explode(
                this,
                null,
                EXPLOSION_DAMAGE_CALCULATOR,
                pos.x(),
                pos.y(),
                pos.z(),
                1.2f,
                false,
                Level.ExplosionInteraction.TRIGGER,
                ParticleTypes.GUST_EMITTER_SMALL,
                ParticleTypes.GUST_EMITTER_LARGE,
                SoundEvents.WIND_CHARGE_BURST
        )
    }

    companion object {
        private val EXPLOSION_DAMAGE_CALCULATOR: ExplosionDamageCalculator = SimpleExplosionDamageCalculator(
            false,
            false,
            Optional.of(1.22f),
            Optional.empty()
        )
    }
}