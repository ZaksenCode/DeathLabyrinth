package me.zaksen.deathLabyrinth.item.ability.stuff

import me.zaksen.deathLabyrinth.damage.DamageType
import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.event.item.ItemUseEvent
import me.zaksen.deathLabyrinth.item.ability.ItemAbility
import me.zaksen.deathLabyrinth.item.ability.recipe.Synergy
import net.kyori.adventure.text.Component
import org.bukkit.event.Event

class ExplosionCast: ItemAbility(
    Component.translatable("ability.explosion_cast.name"),
    Component.translatable("ability.explosion_cast.description"),
    5.0,
    1.5,
    damageType = DamageType.EXPLODE
) {
    override fun invoke(event: Event) {
        if(event !is ItemUseEvent) return

        val stack = event.stack!!
        val item = event.item!!

        var rayCast = event.player.rayTraceEntities(24)

        if(rayCast == null) {
            rayCast = event.player.rayTraceBlocks(24.0)
        }

        if(rayCast == null) {
            return
        }

        if(item.checkCooldown(stack)) {
            val pos = rayCast.hitPosition
            EventManager.callPlayerSummonExplosionEvent(event.player, pos.toLocation(event.player.world).subtract(0.0, 1.0, 0.0), 1.5, 5.0)
        }
    }

    override fun getUpdateAbility(): String {
        return "explosion_cast_tier_two"
    }

    override fun getSynergies(): List<Synergy> {
        return listOf(
            Synergy("fire_flow_cast", "explosion_flow_cast"),
            Synergy("electric_cast", "explosion_chain_cast")
        )
    }
}