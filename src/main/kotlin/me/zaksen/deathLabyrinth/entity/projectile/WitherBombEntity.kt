package me.zaksen.deathLabyrinth.entity.projectile

import me.zaksen.deathLabyrinth.damage.DamageType
import me.zaksen.deathLabyrinth.event.EventManager
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.network.ServerPlayerConnection
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MoverType
import net.minecraft.world.entity.item.PrimedTnt
import net.minecraft.world.entity.player.Player
import org.bukkit.Location
import org.bukkit.craftbukkit.CraftWorld
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class WitherBombEntity(location: Location, val range: Double, val damage: Double): PrimedTnt(EntityType.TNT, (location.world as CraftWorld).handle) {

    init {
        this.setPos(location.x, location.y, location.z)
    }

    override fun checkDespawn() { }

    override fun tick() {
        if (level().spigotConfig.maxTntTicksPerTick > 0 && ++level().spigotConfig.currentPrimedTnt > level().spigotConfig.maxTntTicksPerTick) {
            return
        } // Spigot

        this.handlePortal()
        this.applyGravity()
        this.move(MoverType.SELF, this.deltaMovement)
        // Paper start - Configurable TNT height nerf
        if (level().paperConfig().fixes.tntEntityHeightNerf.test { v: Int -> this.y > v }) {
            this.discard()
            return
        }
        // Paper end - Configurable TNT height nerf
        this.deltaMovement = deltaMovement.scale(0.98)
        if (this.onGround()) {
            this.deltaMovement = deltaMovement.multiply(0.7, -0.5, 0.7)
        }

        val i = this.fuse - 1

        this.fuse = i
        if (i <= 0) {
            // CraftBukkit start - Need to reverse the order of the explosion and the entity death so we have a location for the event
            // this.discard();
            if (!level().isClientSide) {
                if(owner != null && owner is Player) {
                    EventManager.callPlayerSummonExplosionEvent(
                        owner!!.bukkitEntity as org.bukkit.entity.Player,
                        Location(this.level().world, this.x, this.y, this.z),
                        range,
                        damage,
                        entityConsumer = {
                            it.addPotionEffect(PotionEffect(
                                PotionEffectType.WITHER,
                                80,
                                3,
                                false,
                                false,
                                false
                            ))
                            it.addPotionEffect(PotionEffect(
                                PotionEffectType.WEAKNESS,
                                80,
                                1,
                                false,
                                false,
                                false
                            ))
                        },
                        damageType = DamageType.WITHER
                    )
                } else {
                    EventManager.callSummonExplosionEvent(
                        Location(this.level().world, this.x, this.y, this.z),
                        range,
                        damage,
                        entityConsumer = {
                            it.addPotionEffect(PotionEffect(
                                PotionEffectType.WITHER,
                                80,
                                3,
                                false,
                                false,
                                false
                            ))
                            it.addPotionEffect(PotionEffect(
                                PotionEffectType.WEAKNESS,
                                80,
                                1,
                                false,
                                false,
                                false
                            ))
                        },
                        damageType = DamageType.WITHER
                    )
                }
            }
            this.discard() // CraftBukkit - add Bukkit remove cause
            // CraftBukkit end
        } else {
            this.updateInWaterStateAndDoFluidPushing()
            if (level().isClientSide) {
                level().addParticle(ParticleTypes.SMOKE, this.x, this.y + 0.5, this.z, 0.0, 0.0, 0.0)
            }
        }

        // Paper start - Option to prevent TNT from moving in water
        if (!this.isRemoved && this.wasTouchingWater && level().paperConfig().fixes.preventTntFromMovingInWater) {
            /*
             * Author: Jedediah Smith <jedediah@silencegreys.com>
             */
            // Send position and velocity updates to nearby players on every tick while the TNT is in water.
            // This does pretty well at keeping their clients in sync with the server.
            val ete = (level() as ServerLevel).getChunkSource().chunkMap.entityMap[id]
            if (ete != null) {
                val velocityPacket = ClientboundSetEntityMotionPacket(this)
                val positionPacket = ClientboundTeleportEntityPacket(this)

                ete.seenBy.stream()
                    .filter { viewer: ServerPlayerConnection -> (viewer.player.x - this.x) * (viewer.player.y - this.y) * (viewer.player.z - this.z) < 16 * 16 }
                    .forEach { viewer: ServerPlayerConnection ->
                        viewer.send(velocityPacket)
                        viewer.send(positionPacket)
                    }
            }
        }
        // Paper end - Option to prevent TNT from moving in water
    }
}