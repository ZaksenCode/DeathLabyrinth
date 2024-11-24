package me.zaksen.deathLabyrinth.item.ability.stuff

import me.zaksen.deathLabyrinth.event.item.ItemUseEvent
import me.zaksen.deathLabyrinth.item.ability.ItemAbility
import me.zaksen.deathLabyrinth.item.ability.recipe.Synergy
import net.kyori.adventure.text.Component
import org.bukkit.Particle
import org.bukkit.attribute.Attribute
import org.bukkit.event.Event
import org.bukkit.event.entity.EntityRegainHealthEvent

class HealingCast: ItemAbility(
    Component.translatable("ability.healing_cast.name"),
    Component.translatable("ability.healing_cast.description"),
    isDisplayDamageType = false
) {
    override fun invoke(event: Event) {
        if(event !is ItemUseEvent) return

        val stack = event.stack!!
        val item = event.item!!

        if(item.checkCooldown(stack)) {
            val maxHealth = event.player.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.baseValue
            val toHeal = maxHealth * 0.15

            event.player.heal(toHeal, EntityRegainHealthEvent.RegainReason.MAGIC)

            event.player.world.spawnParticle(
                Particle.TOTEM_OF_UNDYING,
                event.player.location,
                50,
                0.5,
                0.5,
                0.5
            )
        }
    }

    override fun getSynergies(): List<Synergy> {
        return listOf(
            Synergy("healing_cast", "big_healing_cast")
        )
    }
}