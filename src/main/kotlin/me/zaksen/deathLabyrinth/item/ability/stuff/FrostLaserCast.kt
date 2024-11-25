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

class FrostLaserCast: ItemAbility(
    Component.translatable("ability.frost_laser_cast.name"),
    Component.translatable("ability.frost_laser_cast.description"),
    25.0
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
            EventManager.callPlayerSpellEntityDamageEvent(event.player, rayCastEntity.hitEntity as CraftLivingEntity, 25.0, DamageType.WATER)

            EventManager.callPlayerApplySlownessEvent(
                event.player,
                (rayCastEntity.hitEntity as CraftLivingEntity),
                20,
                8
            )

            event.player.eyeLocation.particleLine(Particle.DRIPPING_WATER, rayCastEntity.hitPosition.toLocation(event.player.world))
        }
    }
}