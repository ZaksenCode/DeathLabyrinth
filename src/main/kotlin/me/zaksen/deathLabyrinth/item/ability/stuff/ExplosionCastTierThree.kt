package me.zaksen.deathLabyrinth.item.ability.stuff

import me.zaksen.deathLabyrinth.damage.DamageType
import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.event.item.ItemUseEvent
import me.zaksen.deathLabyrinth.item.ability.ItemAbility
import me.zaksen.deathLabyrinth.item.checkCooldown
import net.kyori.adventure.text.Component
import org.bukkit.event.Event

class ExplosionCastTierThree: ItemAbility(
    Component.translatable("ability.explosion_cast_tier_three.name"),
    Component.translatable("ability.explosion_cast_tier_two.description"),
    17.0,
    3.5,
    damageType = DamageType.EXPLODE
) {
    override fun invoke(event: Event) {
        if(event !is ItemUseEvent) return

        val stack = event.stack!!

        var rayCast = event.player.rayTraceEntities(24)

        if(rayCast == null) {
            rayCast = event.player.rayTraceBlocks(24.0)
        }

        if(rayCast == null) {
            return
        }

        if(checkCooldown(stack)) {
            val pos = rayCast.hitPosition
            EventManager.callPlayerSummonExplosionEvent(event.player, pos.toLocation(event.player.world).subtract(0.0, 1.0, 0.0), 3.5, 17.0)
        }
    }

    override fun getConflictAbilities(): List<String> {
        return listOf("explosion_cast", "explosion_cast_tier_two")
    }
}