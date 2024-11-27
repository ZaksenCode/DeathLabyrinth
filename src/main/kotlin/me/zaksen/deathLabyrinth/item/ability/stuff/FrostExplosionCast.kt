package me.zaksen.deathLabyrinth.item.ability.stuff

import me.zaksen.deathLabyrinth.damage.DamageType
import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.event.item.ItemUseEvent
import me.zaksen.deathLabyrinth.item.ability.ItemAbility
import me.zaksen.deathLabyrinth.item.checkCooldown
import net.kyori.adventure.text.Component
import org.bukkit.event.Event
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class FrostExplosionCast: ItemAbility(
    Component.translatable("ability.frost_explosion_cast.name"),
    Component.translatable("ability.frost_explosion_cast.description"),
    15.0,
    2.25,
    damageType = DamageType.WATER
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
            EventManager.callPlayerSummonExplosionEvent(event.player, pos.toLocation(event.player.world).subtract(0.0, 1.0, 0.0), 2.25, 15.0,
                entityConsumer = {
                    EventManager.callPlayerApplySlownessEvent(
                        event.player,
                        it,
                        20,
                        8
                    )
                },
                damageType = DamageType.WATER)
        }
    }
}