package me.zaksen.deathLabyrinth.item.ability.weapon

import me.zaksen.deathLabyrinth.entity.friendly.FriendlyEntity
import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.event.item.ItemHitEvent
import me.zaksen.deathLabyrinth.item.ability.ItemAbility
import net.kyori.adventure.text.Component
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.Event

class HugeAreaHit: ItemAbility(
    Component.translatable("ability.area_hit.name"),
    Component.translatable("ability.area_hit.description"),
    displayRange = 2.0
) {
    override fun invoke(event: Event) {
        if(event !is ItemHitEvent) return

        val affectedEntities = event.damaged.getNearbyEntities(2.0, 2.0, 2.0)

        for(entity in affectedEntities) {
            if(entity is LivingEntity && entity !is Player && entity !is FriendlyEntity) {
                EventManager.callPlayerDamageEntityEvent(event.damager as Player, entity, event.damage)
            }
        }
    }
}