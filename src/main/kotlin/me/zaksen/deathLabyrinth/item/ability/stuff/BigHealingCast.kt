package me.zaksen.deathLabyrinth.item.ability.stuff

import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.event.item.ItemUseEvent
import me.zaksen.deathLabyrinth.item.ability.ItemAbility
import me.zaksen.deathLabyrinth.item.checkCooldown
import net.kyori.adventure.text.Component
import org.bukkit.Particle
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.entity.EntityRegainHealthEvent

class BigHealingCast: ItemAbility(
    Component.translatable("ability.big_healing_cast.name"),
    Component.translatable("ability.big_healing_cast.description"),
    displayRange = 4.0,
    isDisplayDamageType = false
) {
    override fun invoke(event: Event) {
        if(event !is ItemUseEvent) return

        val stack = event.stack!!

        if(checkCooldown(stack)) {
            val players = event.player.world.getNearbyEntitiesByType(
                Player::class.java,
                event.player.location,
                4.0
            )

            players.forEach {
                val maxHealth = it.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.baseValue
                val toHeal = maxHealth * 0.3

                EventManager.callPlayerHealingEvent(event.player, it, toHeal)
                it.world.spawnParticle(
                    Particle.TOTEM_OF_UNDYING,
                    it.location,
                    50,
                    0.5,
                    0.5,
                    0.5
                )
            }
        }
    }
}