package me.zaksen.deathLabyrinth.item.ability.stuff

import me.zaksen.deathLabyrinth.damage.DamageType
import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.event.item.ItemUseEvent
import me.zaksen.deathLabyrinth.item.ability.ItemAbility
import me.zaksen.deathLabyrinth.item.ability.recipe.Synergy
import me.zaksen.deathLabyrinth.item.checkCooldown
import net.kyori.adventure.text.Component
import org.bukkit.event.Event
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class WitherExplosionCast: ItemAbility(
    Component.translatable("ability.wither_explosion_cast.name"),
    Component.translatable("ability.wither_explosion_cast.description"),
    20.0,
    2.5,
    damageType = DamageType.WITHER
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
            EventManager.callPlayerSummonExplosionEvent(event.player, pos.toLocation(event.player.world).subtract(0.0, 1.0, 0.0), 2.5, 20.0,
                entityConsumer = {
                    it.addPotionEffect(PotionEffect(
                        PotionEffectType.WITHER,
                        80,
                        3,
                        false,
                        false,
                        false
                    ))
                    it.addPotionEffect(PotionEffect(
                        PotionEffectType.WEAKNESS,
                        80,
                        1,
                        false,
                        false,
                        false
                    ))
                },
                damageType = DamageType.WITHER)
        }
    }
}