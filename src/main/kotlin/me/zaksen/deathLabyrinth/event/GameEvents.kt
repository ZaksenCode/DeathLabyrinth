package me.zaksen.deathLabyrinth.event

import me.zaksen.deathLabyrinth.config.MainConfig
import me.zaksen.deathLabyrinth.entity.friendly.FriendlyEntity
import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.game.GameStatus
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.craftbukkit.entity.CraftEntity
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockDamageEvent
import org.bukkit.event.block.BlockExplodeEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityChangeBlockEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.entity.EntityExplodeEvent
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
            EventManager.callPlayerKillEntityEvent(event.damageSource.causingEntity!! as Player, event.entity, event.drops)
        } else {
            EventManager.callPlayerKillEntityEvent(null, event.entity, event.drops)
        }
    }

    @EventHandler
    fun processPlayerDeath(event: EntityDamageEvent) {
        if(config.debug) {
            return
        }

        val entity = event.entity
        if(entity is Player && (entity.health + entity.absorptionAmount) - event.damage <= 0) {
            EventManager.callPlayerDeathEvent(entity, event.damage)
            event.isCancelled = true
        }
    }

    @EventHandler
    fun processPlayerDamage(event: EntityDamageByEntityEvent) {
        val damager = event.damager
        var projectile_owner: Entity? = null

        if(damager is Projectile) {
            projectile_owner = Bukkit.getEntity(damager.ownerUniqueId!!)

            if(projectile_owner == null) {
                return
            }

            if((projectile_owner as CraftEntity).handle is FriendlyEntity) {
                if(event.entity is Player) {
                    event.isCancelled = true
                }
                else {
                    EventManager.callFriendlyEntityDamageEventProjectile(event)
                }
                return
            }
        }

        if((damager as CraftEntity).handle is FriendlyEntity) {
            if(event.entity is Player) {
                event.isCancelled = true
            }
            else {
                EventManager.callFriendlyEntityDamageEvent(event)
            }
            return
        }

        if(event.entity is Player) {
            EventManager.callPlayerDamagedByEntityEvent(event)
        }
    }

    @EventHandler
    fun processHitEntity(event: EntityDamageByEntityEvent) {
        val entity = event.entity

        if(event.damager is Player && entity is LivingEntity) {
            EventManager.callPlayerDamageEntityEvent(event.damager as Player, entity, event.damage, event)
        }
    }

    @EventHandler
    fun preventItemDrop(event: PlayerDropItemEvent) {
        if(!config.debug && GameController.getStatus() != GameStatus.PROCESS) {
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

    @EventHandler
    fun preventExplosion(event: BlockExplodeEvent) {
        event.blockList().clear()
        event.isCancelled = true
    }

    @EventHandler
    fun preventExplosion(event: EntityExplodeEvent) {
        event.blockList().clear()
    }
}