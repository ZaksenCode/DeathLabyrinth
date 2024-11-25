package me.zaksen.deathLabyrinth.item.ability.stuff

import me.zaksen.deathLabyrinth.damage.DamageType
import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.event.item.ItemUseEvent
import me.zaksen.deathLabyrinth.item.ability.ItemAbility
import me.zaksen.deathLabyrinth.util.particleLine
import net.kyori.adventure.text.Component
import org.bukkit.Particle
import org.bukkit.craftbukkit.entity.CraftLivingEntity
import org.bukkit.entity.LivingEntity
import org.bukkit.event.Event
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class WitherLaserCast: ItemAbility(
    Component.translatable("ability.wither_laser_cast.name"),
    Component.translatable("ability.wither_laser_cast.description"),
    21.0
) {
    override fun invoke(event: Event) {
        if(event !is ItemUseEvent) return

        val stack = event.stack!!
        val item = event.item!!

        val rayCastEntity = event.player.rayTraceEntities(32)

        if(rayCastEntity != null && rayCastEntity.hitEntity != null && item.checkCooldown(stack)) {
            if(rayCastEntity.hitEntity!! !is LivingEntity) {
                return
            }
            EventManager.callPlayerSpellEntityDamageEvent(event.player, rayCastEntity.hitEntity as CraftLivingEntity, 21.0, DamageType.WITHER)

            (rayCastEntity.hitEntity as CraftLivingEntity).addPotionEffect(PotionEffect(
                PotionEffectType.WITHER,
                80,
                3,
                false,
                false,
                false
            ))
            (rayCastEntity.hitEntity as CraftLivingEntity).addPotionEffect(PotionEffect(
                PotionEffectType.WEAKNESS,
                80,
                1,
                false,
                false,
                false
            ))

            event.player.eyeLocation.particleLine(Particle.DRIPPING_WATER, rayCastEntity.hitPosition.toLocation(event.player.world))
        }
    }
}