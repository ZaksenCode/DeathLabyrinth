package me.zaksen.deathLabyrinth.item.ability.weapon

import me.zaksen.deathLabyrinth.entity.friendly.FriendlyEntity
import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.event.item.ItemHitEvent
import me.zaksen.deathLabyrinth.item.ability.ItemAbility
import me.zaksen.deathLabyrinth.item.ability.recipe.Synergy
import net.kyori.adventure.text.Component
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.Event

// FIXME - The amount of damage increases proportionally to the number of mobs hit.
class AreaHit: ItemAbility(
    Component.translatable("ability.area_hit.name"),
    Component.translatable("ability.area_hit.description"),
    displayRange = 0.75,
    isDisplayDamageType = false
) {
    override fun invoke(event: Event) {
        if(event !is ItemHitEvent) return

        val affectedEntities = event.damaged.getNearbyEntities(0.75, 0.75, 0.75).filter { it.uniqueId != event.damaged.uniqueId }

        for(entity in affectedEntities) {
            if(entity is LivingEntity && entity !is Player && entity !is FriendlyEntity) {
                EventManager.callPlayerDamageEntityEvent(event.damager as Player, entity, event.damage)
            }
        }
    }

    override fun getSynergies(): List<Synergy> {
        return listOf(
            Synergy("area_hit", "big_area_hit")
        )
    }
}