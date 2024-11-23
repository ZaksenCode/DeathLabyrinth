package me.zaksen.deathLabyrinth.item.ability.weapon

import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.event.item.ItemHitEvent
import me.zaksen.deathLabyrinth.item.ability.ItemAbility
import net.kyori.adventure.text.Component
import org.bukkit.attribute.Attribute
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.Event
import kotlin.random.Random

class NarwhalPunch: ItemAbility(
    Component.translatable("ability.narwhal_punch.name"),
    Component.translatable("ability.narwhal_punch.description")
) {
    val random: Random = Random.Default

    override fun invoke(event: Event) {
        if(event !is ItemHitEvent) return

        val damaged = event.damaged

        if(damaged !is LivingEntity) return

        val halfHealth = damaged.getAttribute(Attribute.GENERIC_MAX_HEALTH)?.baseValue!! / 2

        if(damaged.health <= halfHealth && random.nextInt(0, 100) <= 15) {
            EventManager.callPlayerDamageEntityEvent(event.damager as Player, damaged, damaged.health)
        }
    }
}