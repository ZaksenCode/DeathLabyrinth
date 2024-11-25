package me.zaksen.deathLabyrinth.item.ability.stuff

import me.zaksen.deathLabyrinth.damage.DamageType
import me.zaksen.deathLabyrinth.event.item.ItemUseEvent
import me.zaksen.deathLabyrinth.item.ability.ItemAbility
import net.kyori.adventure.text.Component
import org.bukkit.event.Event

class SculkCast: ItemAbility(
    Component.translatable("ability.sculk_cast.name"),
    Component.translatable("ability.sculk_cast.description"),
    6.0,
    damageType = DamageType.SCULK
) {
    override fun invoke(event: Event) {
        if(event !is ItemUseEvent) return

        val stack = event.stack!!
        val item = event.item!!

        if(item.checkCooldown(stack)) {
            val shotVelocity = event.player.location.direction.multiply(2).normalize().multiply(1.5)

            // EventManager.callPlayerSummonSpellEvent(event.player, projectile)
        }
    }
}