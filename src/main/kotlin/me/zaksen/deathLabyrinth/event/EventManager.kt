package me.zaksen.deathLabyrinth.event

import me.zaksen.deathLabyrinth.artifacts.ArtifactsController
import me.zaksen.deathLabyrinth.artifacts.card.CardHolder
import me.zaksen.deathLabyrinth.command.PlayerPickupArtifactEvent
import me.zaksen.deathLabyrinth.event.custom.PlayerReadyEvent
import me.zaksen.deathLabyrinth.event.custom.game.*
import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.game.TradeController
import me.zaksen.deathLabyrinth.game.room.Room
import me.zaksen.deathLabyrinth.game.room.RoomController
import me.zaksen.deathLabyrinth.trading.TradeOffer
import me.zaksen.deathLabyrinth.util.tryAddEntity
import net.minecraft.world.entity.Entity
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.block.Block
import org.bukkit.craftbukkit.entity.CraftLivingEntity
import org.bukkit.craftbukkit.entity.CraftPlayer
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.inventory.ItemStack

object EventManager {

    fun callReadyEvent(player: Player) {
        val coolEvent = PlayerReadyEvent(player)
        coolEvent.callEvent()
        GameController.processAnyEvent(coolEvent)
        if (!coolEvent.isCancelled) {
            GameController.toggleReadyState(coolEvent.player)
        }
    }

    fun callPlayerDeathEvent(player: Player, damage: Double) {
        val coolEvent = PlayerDeathEvent(player, damage)
        coolEvent.callEvent()
        GameController.processAnyEvent(coolEvent)
        if (!coolEvent.isCancelled) {
            GameController.processPlayerDeath(coolEvent.player)
        }
    }

    fun callPlayerDamageEntityEvent(player: Player, entity: LivingEntity, damage: Double, event: EntityDamageByEntityEvent) {
        val coolEvent = PlayerDamageEntityEvent(player, entity, damage)
        coolEvent.callEvent()
        GameController.processAnyEvent(coolEvent)
        if (!coolEvent.isCancelled) {
            event.damage = coolEvent.damage
            GameController.processEntityHit(coolEvent.entity)
        } else {
            event.isCancelled = true
        }
    }

    fun callPlayerDamageEntityEvent(player: Player, entity: LivingEntity, damage: Double) {
        val coolEvent = PlayerDamageEntityEvent(player, entity, damage)
        coolEvent.callEvent()
        GameController.processAnyEvent(coolEvent)
        if (!coolEvent.isCancelled) {
            print("Damage entity!!")
            entity.damage(coolEvent.damage, coolEvent.player)
            GameController.processEntityHit(coolEvent.entity)
        }
    }

    fun callPlayerKillEntityEvent(player: Player?, entity: LivingEntity, drops: List<ItemStack>) {
        val coolEvent = PlayerKillEntityEvent(player, entity, drops)
        coolEvent.callEvent()
        GameController.processAnyEvent(coolEvent)
        if (!coolEvent.isCancelled) {
            RoomController.processEntityRoomDeath(coolEvent)
        }
    }

    fun callPlayerDamagedByEntityEvent(event: EntityDamageByEntityEvent) {
        val coolEvent = PlayerDamagedByEntityEvent(event.damager, event.entity as Player, event.damage)
        coolEvent.callEvent()
        GameController.processAnyEvent(coolEvent)
        if (coolEvent.isCancelled) {
            event.isCancelled = true
        }
    }

    fun callFriendlyEntityDamageEvent(event: EntityDamageByEntityEvent) {
        val coolEvent = FriendlyEntityDamageEntityEvent(event.damager as LivingEntity, event.entity as LivingEntity, event.damage)
        coolEvent.callEvent()
        GameController.processAnyEvent(coolEvent)
        if (!coolEvent.isCancelled) {
            event.damage = coolEvent.damage
            GameController.processEntityHit(coolEvent.entity)
        } else {
            event.isCancelled = true
        }
    }

    fun callFriendlyEntityDamageEventProjectile(event: EntityDamageByEntityEvent) {
        val damager = event.damager
        var projectile_owner: org.bukkit.entity.Entity?

        if(damager is Projectile) {
            projectile_owner = Bukkit.getEntity(damager.ownerUniqueId!!)

            if(projectile_owner == null) {
                return
            }

            val coolEvent = FriendlyEntityDamageEntityEvent(projectile_owner as LivingEntity, event.entity as LivingEntity, event.damage)
            coolEvent.callEvent()
            GameController.processAnyEvent(coolEvent)
            if (!coolEvent.isCancelled) {
                event.damage = coolEvent.damage
                GameController.processEntityHit(coolEvent.entity)
            } else {
                event.isCancelled = true
            }
        }
    }

    fun callRoomCompleteEvent(players: List<Player>, roomNumber: Int, room: Room) {
        val coolEvent = RoomCompleteEvent(players, roomNumber, room)
        coolEvent.callEvent()
        GameController.processAnyEvent(coolEvent)
        RoomController.processRoomCompletion()
    }

    fun callPlayerRoomCompleteEvent(player: Player, roomNumber: Int, room: Room, reward: Int) {
        val coolEvent = PlayerRoomCompleteEvent(player, roomNumber, room, reward)
        coolEvent.callEvent()
        GameController.processAnyEvent(coolEvent)
        RoomController.grantRoomReward(coolEvent.player, coolEvent.reward)
    }

    fun callEntitySpawnEvent(world: World, entity: Entity, requireKill: Boolean, debug: Boolean = false) {
        val coolEvent = EntitySpawnEvent(world, entity, requireKill, debug)
        coolEvent.callEvent()
        GameController.processAnyEvent(coolEvent)
        if(!coolEvent.isCancelled) {
            RoomController.processEntitySpawn(coolEvent.world, coolEvent.entity, coolEvent.requireKill, coolEvent.debug)
        }
    }

    fun callEntityCloneSpawnEvent(world: World, entity: Entity, requireKill: Boolean, debug: Boolean = false) {
        val coolEvent = EntityCloneSpawnEvent(world, entity, requireKill, debug)
        coolEvent.callEvent()
        GameController.processAnyEvent(coolEvent)
        if(!coolEvent.isCancelled) {
            RoomController.processEntitySpawn(coolEvent.world, coolEvent.entity, coolEvent.requireKill, coolEvent.debug)
        }
    }

    fun callBreakPotEvent(player: Player, pot: Block) {
        val coolEvent = PlayerBreakPotEvent(player, pot, GameController.getRandomPotLoot())
        coolEvent.callEvent()
        GameController.processAnyEvent(coolEvent)
        GameController.processPotBreaking(coolEvent)
    }

    fun callPlayerSummonFriendlyEntityEvent(player: Player, entity: net.minecraft.world.entity.LivingEntity) {
        val coolEvent = PlayerSummonFriendlyEntityEvent(player, entity)
        coolEvent.callEvent()
        GameController.processAnyEvent(coolEvent)
        if(!coolEvent.isCancelled) {
            coolEvent.player.world.tryAddEntity(entity)
        }
    }

    fun callPlayerSummonFriendlyEntityCloneEvent(player: Player, entity: net.minecraft.world.entity.LivingEntity) {
        val coolEvent = FriendlyEntityCloneSummonEvent(player, entity)
        coolEvent.callEvent()
        GameController.processAnyEvent(coolEvent)
        if(!coolEvent.isCancelled) {
            coolEvent.player.world.tryAddEntity(entity)
        }
    }

    fun callPlayerSpellEntityDamageEvent(player: Player, entity: net.minecraft.world.entity.LivingEntity, damage: Double) {
        val coolEvent = PlayerSpellEntityDamageEvent(player, entity, damage)
        coolEvent.callEvent()
        GameController.processAnyEvent(coolEvent)
        if(!coolEvent.isCancelled) {
            coolEvent.entity.hurt(entity.damageSources().playerAttack((player as CraftPlayer).handle), coolEvent.damage.toFloat())
        }
    }

    fun callPlayerSpellEntityDamageEvent(player: Player, entity: LivingEntity, damage: Double) {
        this.callPlayerSpellEntityDamageEvent(player, (entity as CraftLivingEntity).handle, damage)
    }

    fun callSpellEntityDamageEvent(entity: net.minecraft.world.entity.LivingEntity, damage: Double) {
        val coolEvent = SpellEntityDamageEvent(entity, damage)
        coolEvent.callEvent()
        GameController.processAnyEvent(coolEvent)
        if(!coolEvent.isCancelled) {
            coolEvent.entity.hurt(entity.damageSources().generic(), coolEvent.damage.toFloat())
        }
    }

    fun callSpellEntityDamageEvent(entity: LivingEntity, damage: Double) {
        this.callSpellEntityDamageEvent((entity as CraftLivingEntity).handle, damage)
    }

    fun callPlayerSummonSpellEvent(player: Player, entity: Entity) {
        val coolEvent = PlayerSummonSpellEvent(player, entity)
        coolEvent.callEvent()
        GameController.processAnyEvent(coolEvent)
        if(!coolEvent.isCancelled) {
            coolEvent.player.world.tryAddEntity(coolEvent.spell)
        }
    }

    fun callPlayerTradeEvent(player: Player, offer: TradeOffer) {
        val coolEvent = PlayerTradeEvent(player, offer)
        coolEvent.callEvent()
        GameController.processAnyEvent(coolEvent)
        if(!coolEvent.isCancelled) {
            TradeController.processTrade(player, coolEvent.offer)
        }
    }

    fun callPlayerPickupArtifactsEvent(player: Player, cardHolder: CardHolder) {
        val coolEvent = PlayerPickupArtifactEvent(player, cardHolder)
        coolEvent.callEvent()
        GameController.processAnyEvent(coolEvent)
        if(!coolEvent.isCancelled) {
            ArtifactsController.processArtifactPickup(player, cardHolder)
        }
    }
}