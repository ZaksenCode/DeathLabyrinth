package me.zaksen.deathLabyrinth.item.ability.weapon

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

class BubbleLaser: ItemAbility(
    Component.translatable("ability.bubble_laser.name"),
    Component.translatable("ability.bubble_laser.description"),
    30.0
) {
    override fun invoke(event: Event) {
        if(event !is ItemUseEvent) return

        val stack = event.stack!!

        val rayCastEntity = event.player.rayTraceEntities(64)

        if(rayCastEntity != null && rayCastEntity.hitEntity != null && checkCooldown(stack)) {
            if(rayCastEntity.hitEntity!! !is LivingEntity) {
                return
            }
            EventManager.callPlayerSpellEntityDamageEvent(event.player, rayCastEntity.hitEntity as CraftLivingEntity, 30.0)

            event.player.eyeLocation.particleLine(Particle.BUBBLE_POP, rayCastEntity.hitPosition.toLocation(event.player.world))
        }
    }
}