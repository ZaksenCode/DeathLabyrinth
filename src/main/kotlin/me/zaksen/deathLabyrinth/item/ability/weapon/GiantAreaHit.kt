package me.zaksen.deathLabyrinth.item.ability.weapon

import me.zaksen.deathLabyrinth.entity.friendly.FriendlyEntity
import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.event.item.ItemHitEvent
import me.zaksen.deathLabyrinth.item.ability.ItemAbility
import net.kyori.adventure.text.Component
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.Event

class GiantAreaHit: ItemAbility(
    Component.translatable("ability.giant_area_hit.name"),
    Component.translatable("ability.giant_area_hit.description"),
    displayRange = 3.5,
    isDisplayDamageType = false
) {
    override fun invoke(event: Event) {
        if(event !is ItemHitEvent) return

        val affectedEntities = event.damaged.getNearbyEntities(3.5, 3.5, 3.5)

        for(entity in affectedEntities) {
            if(entity is LivingEntity && entity !is Player && entity !is FriendlyEntity) {
                EventManager.callPlayerDamageEntityEvent(event.damager as Player, entity, event.damage)
            }
        }
    }

    override fun getConflictAbilities(): List<String> {
        return listOf("area_hit", "big_area_hit", "huge_area_hit")
    }
}