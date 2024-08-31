package me.zaksen.deathLabyrinth.event.custom.game

import org.bukkit.damage.DamageSource
import org.bukkit.entity.LivingEntity
import org.bukkit.event.HandlerList
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.inventory.ItemStack

class PlayerKillEntityEvent(entity: LivingEntity, damageSource: DamageSource, drops: List<ItemStack>):
    EntityDeathEvent(entity, damageSource, drops) {

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