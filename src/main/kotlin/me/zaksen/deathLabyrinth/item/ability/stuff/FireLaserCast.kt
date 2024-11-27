package me.zaksen.deathLabyrinth.item.ability.stuff

import me.zaksen.deathLabyrinth.damage.DamageType
import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.event.item.ItemUseEvent
import me.zaksen.deathLabyrinth.item.ability.ItemAbility
import me.zaksen.deathLabyrinth.item.checkCooldown
import me.zaksen.deathLabyrinth.util.particleLine
import net.kyori.adventure.text.Component
import org.bukkit.Particle
import org.bukkit.craftbukkit.entity.CraftLivingEntity
import org.bukkit.entity.LivingEntity
import org.bukkit.event.Event
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class FireLaserCast: ItemAbility(
    Component.translatable("ability.fire_laser_cast.name"),
    Component.translatable("ability.fire_laser_cast.description"),
    23.0
) {
    override fun invoke(event: Event) {
        if(event !is ItemUseEvent) return

        val stack = event.stack!!

        val rayCastEntity = event.player.rayTraceEntities(32)

        if(rayCastEntity != null && rayCastEntity.hitEntity != null && checkCooldown(stack)) {
            if(rayCastEntity.hitEntity!! !is LivingEntity) {
                return
            }
            EventManager.callPlayerSpellEntityDamageEvent(event.player, rayCastEntity.hitEntity as CraftLivingEntity, 23.0, DamageType.FIRE)

            (rayCastEntity.hitEntity as CraftLivingEntity).fireTicks = 100

            event.player.eyeLocation.particleLine(Particle.FLAME, rayCastEntity.hitPosition.toLocation(event.player.world))
        }
    }
}