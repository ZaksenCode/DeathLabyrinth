package me.zaksen.deathLabyrinth.item.ability.consume

import me.zaksen.deathLabyrinth.entity.friendly.FriendlyEntity
import me.zaksen.deathLabyrinth.entity.trader.Trader
import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.event.item.ItemUseEvent
import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.item.ability.ItemAbility
import net.kyori.adventure.text.Component
import org.bukkit.attribute.Attribute
import org.bukkit.craftbukkit.entity.CraftEntity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.Event

class GoldRush: ItemAbility(
    Component.translatable("ability.gold_rush.name"),
    Component.translatable("ability.gold_rush.description"),
    isDisplayDamageType = false
) {
    override fun invoke(event: Event) {
        if(event !is ItemUseEvent) return

        val player = event.player
        val entities = player.getNearbyEntities(12.0,12.0, 12.0)

        entities.forEach {
            if(it is LivingEntity && it !is Player && (it as CraftEntity).handle !is Trader && (it as CraftEntity).handle !is FriendlyEntity) {
                val maxHealth = it.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.value
                EventManager.callPlayerDamageEntityEvent(player, it, maxHealth * 2)
                GameController.addMoney(player, (maxHealth / 2).toInt())
            }
        }

        event.stack?.subtract()
    }
}