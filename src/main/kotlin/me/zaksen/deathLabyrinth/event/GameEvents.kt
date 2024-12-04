package me.zaksen.deathLabyrinth.event

import com.destroystokyo.paper.event.server.ServerTickStartEvent
import me.zaksen.deathLabyrinth.config.MainConfig
import me.zaksen.deathLabyrinth.entity.friendly.FriendlyEntity
import me.zaksen.deathLabyrinth.event.custom.WorldTickEvent
import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.game.GameController.processAnyEvent
import me.zaksen.deathLabyrinth.game.GameStatus
import me.zaksen.deathLabyrinth.game.room.RoomController
import me.zaksen.deathLabyrinth.item.ItemsController
import me.zaksen.deathLabyrinth.keys.PluginKeys
import me.zaksen.deathLabyrinth.menu.Menus
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.craftbukkit.entity.CraftEntity
import org.bukkit.craftbukkit.inventory.CraftInventoryPlayer
import org.bukkit.entity.Entity
import org.bukkit.entity.Item
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockExplodeEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.*
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

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

    @EventHandler
    fun openMenusFromInventory(event: InventoryClickEvent) {
        if(event.clickedInventory is CraftInventoryPlayer && event.slotType == InventoryType.SlotType.CONTAINER) {
            when(event.slot) {
                17 -> {
                    event.isCancelled = true
                    event.whoClicked.closeInventory(InventoryCloseEvent.Reason.OPEN_NEW)
                    val data = GameController.players[event.whoClicked as Player] ?: return
                    Menus.artifactsMenu(event.whoClicked as Player, data.artifacts)
                }
                26 -> {
                    event.isCancelled = true
                    event.whoClicked.closeInventory(InventoryCloseEvent.Reason.OPEN_NEW)
                    Menus.accessoryMenu(event.whoClicked as Player)
                }
            }
        }
    }

    @EventHandler
    fun worldTickEvent(event: ServerTickStartEvent) {
        val coolEvent = WorldTickEvent()
        coolEvent.callEvent()
        processAnyEvent(coolEvent)
        RoomController.processRooms()
    }

    @EventHandler
    fun fireItems(event: EntityCombustEvent) {
        val entity = event.entity

        if(entity is Item) {
            event.isCancelled = true

            val stack = entity.itemStack

            if(!stack.hasItemMeta()) return
            if(!stack.itemMeta.persistentDataContainer.has(PluginKeys.customItemKey)) return

            val customItemId = stack.itemMeta.persistentDataContainer.get(PluginKeys.customItemKey, PersistentDataType.STRING)!!

            when(customItemId) {
                "meat_bat" -> {
                    entity.world.dropItemNaturally(entity.location, ItemsController.get("cooked_meat_bat")!!.asItemStack())
                    entity.remove()
                }
            }
        }
    }
}