package me.zaksen.deathLabyrinth.item.ability.weapon

import me.zaksen.deathLabyrinth.damage.DamageType
import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.event.item.ItemHitEvent
import me.zaksen.deathLabyrinth.item.ability.ItemAbility
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.event.Event

// FIXME - Recourciec call
class ExplosionPunch: ItemAbility(
    Component.translatable("ability.dynamite_punch.name"),
    Component.translatable("ability.dynamite_punch.description"),
    9.0,
    1.25,
    damageType = DamageType.EXPLODE
) {
    override fun invoke(event: Event) {
        if(event !is ItemHitEvent) return
        EventManager.callPlayerSummonExplosionEvent(event.damager as Player, event.damaged.location, 1.25, 9.0)
    }
}