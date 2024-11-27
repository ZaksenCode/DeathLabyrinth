package me.zaksen.deathLabyrinth.item.ability.stuff

import me.zaksen.deathLabyrinth.entity.friendly.FriendlyEntity
import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.event.item.ItemUseEvent
import me.zaksen.deathLabyrinth.item.ability.ItemAbility
import me.zaksen.deathLabyrinth.item.checkCooldown
import me.zaksen.deathLabyrinth.util.particleLine
import net.kyori.adventure.text.Component
import org.bukkit.Particle
import org.bukkit.attribute.Attribute
import org.bukkit.craftbukkit.entity.CraftEntity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.Event

class HealingLaserCast: ItemAbility(
    Component.translatable("ability.healing_laser_cast.name"),
    Component.translatable("ability.healing_laser_cast.description"),
    isDisplayDamageType = false
) {
    override fun invoke(event: Event) {
        if(event !is ItemUseEvent) return

        val stack = event.stack!!

        val rayCastEntity = event.player.rayTraceEntities(32)

        if(rayCastEntity != null && rayCastEntity.hitEntity != null && checkCooldown(stack)) {
            if(rayCastEntity.hitEntity!! is Player || (rayCastEntity.hitEntity!! as CraftEntity).handle is FriendlyEntity) {
                val maxHealth = event.player.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.baseValue
                val toHeal = maxHealth * 0.2

                EventManager.callPlayerHealingEvent(event.player, rayCastEntity.hitEntity!! as LivingEntity, toHeal)

                event.player.eyeLocation.particleLine(Particle.END_ROD, rayCastEntity.hitPosition.toLocation(event.player.world))

                rayCastEntity.hitEntity!!.world.spawnParticle(
                    Particle.TOTEM_OF_UNDYING,
                    event.player.location,
                    50,
                    0.5,
                    0.5,
                    0.5
                )
            }
        }
    }
}