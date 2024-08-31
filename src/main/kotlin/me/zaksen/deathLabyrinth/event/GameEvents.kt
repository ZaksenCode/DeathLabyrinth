package me.zaksen.deathLabyrinth.event

import me.zaksen.deathLabyrinth.config.MainConfig
import me.zaksen.deathLabyrinth.entity.friendly.FriendlyEntity
import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.game.room.RoomController
import org.bukkit.Material
import org.bukkit.entity.AbstractArrow
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityChangeBlockEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.ItemStack

class GameEvents(private val config: MainConfig): Listener {

    @EventHandler
    fun preventBlockBreaking(event: BlockBreakEvent) {
        if(!config.debug) {

            if(event.block.type == Material.DECORATED_POT) {
                event.block.type = Material.AIR
                EventManager.callBreakPotEvent(event.player, event.block)
            }

            event.isCancelled = true
        }
    }

    @EventHandler
    fun processPotBreaking(event: EntityChangeBlockEvent) {
        if(event.block.type == Material.DECORATED_POT && event.entity is Player) {
            event.block.type = Material.AIR
            EventManager.callBreakPotEvent(event.entity as Player, event.block)
        } else {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun preventBlockPlacing(event: BlockPlaceEvent) {
        if(!config.debug) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun processRoomEntityDeath(event: EntityDeathEvent) {
        if(event.damageSource.causingEntity is Player) {
            EventManager.callPlayerKillEntityEvent(event.entity, event.damageSource, event.drops)
        } else {
            RoomController.processEntityRoomDeath(event)
        }
    }

    @EventHandler
    fun processPlayerDeath(event: EntityDamageEvent) {
        if(config.debug) {
            return
        }

        val entity = event.entity
        if(entity is Player && entity.health - event.damage <= 0) {
            EventManager.callPlayerDeathEvent(entity, event.cause, event.damageSource, event.damage)
            event.isCancelled = true
        }
    }

    @EventHandler
    fun processPlayerDamage(event: EntityDamageByEntityEvent) {
        val damager = event.damager

        if(damager is FriendlyEntity && event.entity is Player) {
            event.isCancelled = true
            return
        }

        if(event.entity is Player) {
            EventManager.callPlayerDamagedByEntityEvent(event)
        }
    }

    @EventHandler
    fun processHitEntity(event: EntityDamageEvent) {
        val entity = event.entity

        if(entity is LivingEntity) {
            EventManager.callEntityHitEvent(entity, event.cause, event.damageSource, event.damage)
        }
    }

    @EventHandler
    fun preventItemDrop(event: PlayerDropItemEvent) {
        if(!config.debug) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun useItem(event: PlayerInteractEvent) {
        val item: ItemStack = event.player.inventory.itemInMainHand
        val offHandItem: ItemStack = event.player.inventory.itemInOffHand

        itemCheck(item, event)
        itemCheck(offHandItem, event)
    }

    @EventHandler
    fun preventPotUse(event: PlayerInteractEvent) {
        if(event.hasBlock()) {
            val block = event.clickedBlock!!

            if(event.action == Action.RIGHT_CLICK_BLOCK && block.type == Material.DECORATED_POT) {
                event.isCancelled = true
            }
        }
    }

    private fun itemCheck(stack: ItemStack, event: PlayerInteractEvent) {
        if(!stack.hasItemMeta()) {
            return
        }

        if(stack.type == Material.PAPER) {
            when(stack.itemMeta.customModelData) {
                200, 201 -> {
                    EventManager.callReadyEvent(event.player)
                    event.isCancelled = true
                }
            }
        }
    }

    @EventHandler
    fun setupPlayer(event: PlayerJoinEvent) {
        if(!config.debug) {
            GameController.join(event.player)
        }
    }

    @EventHandler
    fun discardPlayer(event: PlayerQuitEvent) {
        if(!config.debug) {
            GameController.leave(event.player)
        }
    }
}