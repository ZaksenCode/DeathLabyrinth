package me.zaksen.deathLabyrinth.event.custom.game

import org.bukkit.damage.DamageSource
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.entity.EntityDamageByEntityEvent

class PlayerDamagedByEntityEvent(damager: Entity, damaged: Player, cause: DamageCause, source: DamageSource, damage: Double):
    EntityDamageByEntityEvent(damager, damaged, cause, source, damage) {

    override fun getHandlers(): HandlerList {
        return HANDLER_LIST
    }

    companion object {
        private val HANDLER_LIST: HandlerList = HandlerList()

        fun getHandlersList(): HandlerList {
            return HANDLER_LIST
        }
    }
}