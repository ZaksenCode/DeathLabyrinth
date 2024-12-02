package me.zaksen.deathLabyrinth.item.ability.other

import me.zaksen.deathLabyrinth.entity.friendly.FriendlyEntity
import me.zaksen.deathLabyrinth.entity.trader.Trader
import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.event.item.ItemUseEvent
import me.zaksen.deathLabyrinth.item.ability.ItemAbility
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.craftbukkit.entity.CraftEntity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.Event

class Packing: ItemAbility(
    Component.translatable("ability.packing.name"),
    Component.translatable("ability.packing.description"),
    isDisplayDamageType = false
) {
    override fun invoke(event: Event) {
        if(event !is ItemUseEvent) return

        val player = event.player
        val entities = player.getNearbyEntities(20.0,20.0, 20.0)

        entities.forEach {
            if(it is LivingEntity && it !is Player && (it as CraftEntity).handle !is Trader && (it as CraftEntity).handle !is FriendlyEntity) {
                EventManager.callPlayerDamageEntityEvent(player, it, it.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.value * 2)
                player.world.setType(it.location.add(0.0, 0.1, 0.0), Material.DECORATED_POT)
            }
        }

        event.stack?.subtract()
    }
}