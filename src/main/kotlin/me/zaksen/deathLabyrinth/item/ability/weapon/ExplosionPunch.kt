package me.zaksen.deathLabyrinth.item.ability.weapon

import me.zaksen.deathLabyrinth.damage.DamageType
import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.event.item.ItemHitEvent
import me.zaksen.deathLabyrinth.item.ability.ItemAbility
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.inventory.ItemStack

// FIXME - Recourciec call
class ExplosionPunch: ItemAbility(
    Component.translatable("ability.dynamite_punch.name"),
    Component.translatable("ability.dynamite_punch.description"),
    9.0,
    1.25,
    damageType = DamageType.EXPLODE
) {
    private val lastUses: MutableSet<ItemStack> = mutableSetOf()

    override fun invoke(event: Event) {
        if(event !is ItemHitEvent) return

        if(lastUses.contains(event.stack)) {
            lastUses.remove(event.stack)
        } else {
            lastUses.add(event.stack)
            EventManager.callPlayerSummonExplosionEvent(event.damager as Player, event.damaged.location, 1.25, 9.0)
        }
    }

    override fun reload() {
        lastUses.clear()
    }
}