package me.zaksen.deathLabyrinth.event.custom.game

import org.bukkit.damage.DamageSource
import org.bukkit.entity.LivingEntity
import org.bukkit.event.HandlerList
import org.bukkit.event.entity.EntityDamageEvent

// TODO - Добавить события на все случаи жизни
class PlayerDamageEntityEvent(entity: LivingEntity, damageCause: DamageCause, damageSource: DamageSource, damage: Double):
    EntityDamageEvent(entity, damageCause, damageSource, damage) {

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