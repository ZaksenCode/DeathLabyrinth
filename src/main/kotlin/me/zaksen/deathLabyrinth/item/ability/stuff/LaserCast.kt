package me.zaksen.deathLabyrinth.item.ability.stuff

import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.event.item.ItemUseEvent
import me.zaksen.deathLabyrinth.item.ability.ItemAbility
import me.zaksen.deathLabyrinth.util.particleLine
import net.kyori.adventure.text.Component
import org.bukkit.Particle
import org.bukkit.craftbukkit.entity.CraftLivingEntity
import org.bukkit.entity.LivingEntity
import org.bukkit.event.Event

class LaserCast: ItemAbility(
    Component.translatable("ability.laser_cast.name"),
    Component.translatable("ability.laser_cast.description")
) {
    override fun invoke(event: Event) {
        if(event !is ItemUseEvent) return

        val stack = event.stack!!
        val item = event.item!!

        val rayCastEntity = event.player.rayTraceEntities(64)

        if(rayCastEntity != null && rayCastEntity.hitEntity != null && item.checkAndUpdateCooldown(stack)) {
            if(rayCastEntity.hitEntity!! !is LivingEntity) {
                return
            }
            EventManager.callPlayerSpellEntityDamageEvent(event.player, rayCastEntity.hitEntity as CraftLivingEntity, 12.0)

            event.player.eyeLocation.particleLine(Particle.WITCH, rayCastEntity.hitPosition.toLocation(event.player.world))
        }
    }
}