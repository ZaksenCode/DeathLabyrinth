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
import me.zaksen.deathLabyrinth.item.ItemsController
import me.zaksen.deathLabyrinth.trading.TradeOffer
import me.zaksen.deathLabyrinth.util.tryAddEntity
import net.minecraft.world.entity.Entity
import org.bukkit.World
import org.bukkit.block.Block
import org.bukkit.craftbukkit.entity.CraftLivingEntity
import org.bukkit.craftbukkit.entity.CraftPlayer
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
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

    fun callPlayerDamageEntityEvent(player: Player, entity: LivingEntity, damage: Double) {
        val coolEvent = PlayerDamageEntityEvent(player, entity, damage)
        coolEvent.callEvent()
        GameController.processAnyEvent(coolEvent)
        if (!coolEvent.isCancelled) {
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

    fun callRoomCompleteEvent(player: Player, roomNumber: Int, room: Room, reward: Int) {
        val coolEvent = RoomCompleteEvent(player, roomNumber, room, reward)
        coolEvent.callEvent()
        GameController.processAnyEvent(coolEvent)
        RoomController.processRoomCompletion(coolEvent.reward)
    }

    fun callEntitySpawnEvent(world: World, entity: Entity, requireKill: Boolean, debug: Boolean = false) {
        val coolEvent = EntitySpawnEvent(world, entity, requireKill, debug)
        coolEvent.callEvent()
        GameController.processAnyEvent(coolEvent)
        if(!coolEvent.isCancelled) {
            RoomController.processEntitySpawn(coolEvent.world, coolEvent.entity, coolEvent.requireKill, coolEvent.debug)
        }
    }

    // TODO - Random pot loot
    fun callBreakPotEvent(player: Player, pot: Block) {
        val coolEvent = PlayerBreakPotEvent(player, pot, ItemsController.get("small_heal_potion")!!.asItemStack())
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