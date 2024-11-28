package me.zaksen.deathLabyrinth.event

import me.zaksen.deathLabyrinth.artifacts.ArtifactsController
import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import me.zaksen.deathLabyrinth.event.custom.game.PlayerPickupArtifactEvent
import me.zaksen.deathLabyrinth.damage.DamageType
import me.zaksen.deathLabyrinth.event.custom.PlayerReadyEvent
import me.zaksen.deathLabyrinth.event.custom.game.*
import me.zaksen.deathLabyrinth.event.item.ItemConsumeEvent
import me.zaksen.deathLabyrinth.event.item.ItemHitEvent
import me.zaksen.deathLabyrinth.event.item.ItemUseEvent
import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.game.TradeController
import me.zaksen.deathLabyrinth.game.room.Room
import me.zaksen.deathLabyrinth.game.room.RoomController
import me.zaksen.deathLabyrinth.item.CustomItem
import me.zaksen.deathLabyrinth.item.ability.ItemAbilityManager
import me.zaksen.deathLabyrinth.keys.PluginKeys
import me.zaksen.deathLabyrinth.trading.TradeOffer
import me.zaksen.deathLabyrinth.util.tryAddEntity
import net.minecraft.world.entity.Entity
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.block.Block
import org.bukkit.craftbukkit.entity.CraftLivingEntity
import org.bukkit.craftbukkit.entity.CraftPlayer
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityRegainHealthEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.util.function.Consumer

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
            GameController.processEntityHit(coolEvent.damaged)
        } else {
            event.isCancelled = true
        }
    }

    fun callPlayerDamageEntityEvent(player: Player, entity: LivingEntity, damage: Double) {
        val coolEvent = PlayerDamageEntityEvent(player, entity, damage)
        coolEvent.callEvent()
        GameController.processAnyEvent(coolEvent)
        if (!coolEvent.isCancelled) {
            entity.damage(coolEvent.damage, coolEvent.player)
            GameController.processEntityHit(coolEvent.damaged)
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
        } else {
            event.damage = coolEvent.damage
        }
    }

    fun callPlayerDamagedByEntityEvent(damager: LivingEntity, player: Player, damage: Double) {
        val coolEvent = PlayerDamagedByEntityEvent(damager, player, damage)
        coolEvent.callEvent()
        GameController.processAnyEvent(coolEvent)

        if (!coolEvent.isCancelled) {
            player.damage(coolEvent.damage, coolEvent.damager)
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
        var projectileOwner: org.bukkit.entity.Entity?

        if(damager is Projectile) {
            projectileOwner = Bukkit.getEntity(damager.ownerUniqueId!!)

            if(projectileOwner == null) {
                return
            }

            val coolEvent = FriendlyEntityDamageEntityEvent(projectileOwner as LivingEntity, event.entity as LivingEntity, event.damage)
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

    fun callPlayerSpellEntityDamageEvent(player: Player, entity: net.minecraft.world.entity.LivingEntity, damage: Double, damageType: DamageType = DamageType.GENERAL) {
        val coolEvent = PlayerSpellEntityDamageEvent(player, entity, damage, damageType)
        coolEvent.callEvent()
        GameController.processAnyEvent(coolEvent)
        GameController.processEntityHit(coolEvent.entity.bukkitLivingEntity)
        if(!coolEvent.isCancelled) {
            coolEvent.entity.hurt(entity.damageSources().playerAttack((player as CraftPlayer).handle), coolEvent.damage.toFloat())
        }
    }

    fun callPlayerSpellEntityDamageEvent(player: Player, entity: LivingEntity, damage: Double, damageType: DamageType = DamageType.GENERAL) {
        this.callPlayerSpellEntityDamageEvent(player, (entity as CraftLivingEntity).handle, damage, damageType)
    }

    fun callSpellEntityDamageEvent(entity: net.minecraft.world.entity.LivingEntity, damage: Double, damageType: DamageType = DamageType.GENERAL) {
        val coolEvent = SpellEntityDamageEvent(entity, damage, damageType)
        coolEvent.callEvent()
        GameController.processAnyEvent(coolEvent)
        GameController.processEntityHit(coolEvent.entity.bukkitLivingEntity)
        if(!coolEvent.isCancelled) {
            coolEvent.entity.hurt(entity.damageSources().generic(), coolEvent.damage.toFloat())
        }
    }

    fun callSpellEntityDamageEvent(entity: LivingEntity, damage: Double, damageType: DamageType = DamageType.GENERAL) {
        this.callSpellEntityDamageEvent((entity as CraftLivingEntity).handle, damage, damageType)
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

    fun callPlayerPickupArtifactsEvent(player: Player, artifact: Artifact) {
        val coolEvent = PlayerPickupArtifactEvent(player, artifact)
        coolEvent.callEvent()
        GameController.processAnyEvent(coolEvent)
        if(!coolEvent.isCancelled) {
            ArtifactsController.processArtifactPickup(player, artifact)
        }
    }

    fun callPlayerPostPickupArtifactsEvent(player: Player, artifact: Artifact) {
        val coolEvent = PlayerPostPickupArtifactEvent(player, artifact)
        coolEvent.callEvent()
        GameController.processAnyEvent(coolEvent)
    }

    fun callPlayerSummonExplosionEvent(
        player: Player,
        pos: Location,
        range: Double,
        damage: Double,
        drawParticles: Boolean = true,
        playSound: Boolean = true,
        entityConsumer: Consumer<LivingEntity> = Consumer{},
        damageType: DamageType = DamageType.EXPLODE
    ) {
        val coolEvent = PlayerSummonExplosionEvent(player, pos, range, damage)
        coolEvent.callEvent()
        GameController.processAnyEvent(coolEvent)

        if(!coolEvent.isCancelled) {
            GameController.makeExplode(player, pos, coolEvent.range, coolEvent.damage, drawParticles, playSound, entityConsumer, damageType)
        }
    }

    fun callSummonExplosionEvent(
        pos: Location,
        range: Double,
        damage: Double,
        drawParticles: Boolean = true,
        playSound: Boolean = true,
        entityConsumer: Consumer<LivingEntity> = Consumer{},
        damageType: DamageType = DamageType.EXPLODE
    ) {
        val coolEvent = ExplosionEvent(pos, range, damage)
        coolEvent.callEvent()
        GameController.processAnyEvent(coolEvent)

        if(!coolEvent.isCancelled) {
            GameController.makeExplode(null, pos, coolEvent.range, coolEvent.damage, drawParticles, playSound, entityConsumer, damageType)
        }
    }

    fun callPlayerHealingEvent(player: Player, entity: LivingEntity, amount: Double) {
        val coolEvent = PlayerHealingEvent(player, entity, amount)
        coolEvent.callEvent()
        GameController.processAnyEvent(coolEvent)

        if(!coolEvent.isCancelled) {
            coolEvent.entity.heal(amount, EntityRegainHealthEvent.RegainReason.MAGIC)
        }
    }

    fun callPlayerApplySlownessEvent(player: Player, entity: LivingEntity, duration: Int, amplifier: Int) {
        val coolEvent = PlayerApplySlownessEvent(player, entity, duration, amplifier)
        coolEvent.callEvent()
        GameController.processAnyEvent(coolEvent)

        if(!coolEvent.isCancelled) {
            entity.addPotionEffect(
                PotionEffect(
                PotionEffectType.SLOWNESS,
                coolEvent.duration,
                coolEvent.amplifier,
                false,
                false,
                false
            ))
        }
    }

    fun callPlayerStartAbilityCooldownEvent(player: Player, stack: ItemStack, startTime: Long) {
        val coolEvent = PlayerStartAbilityCooldownEvent(player, stack, startTime)
        coolEvent.callEvent()
        GameController.processAnyEvent(coolEvent)

        if(!coolEvent.isCancelled) {
            // ItemAbilityManager.addStackCooldownView(coolEvent.stack)
            val meta = coolEvent.stack.itemMeta
            meta.persistentDataContainer.set(PluginKeys.customItemCooldownKey, PersistentDataType.LONG, coolEvent.startTime)
            coolEvent.stack.setItemMeta(meta)
        }
    }

    // Item Events
    fun callItemHitEvent(damager: org.bukkit.entity.Entity, damaged: org.bukkit.entity.Entity, stack: ItemStack, item: CustomItem, damage: Double, damageType: DamageType = DamageType.GENERAL) {
        val coolEvent = ItemHitEvent(damager, damaged, stack, item, damage, damageType)
        coolEvent.callEvent()
        ItemAbilityManager.useStackAbilities(stack, coolEvent)

        if(!coolEvent.isCancelled) {

            if(damaged !is LivingEntity) return

            if(damager is Player) {
                callPlayerDamageEntityEvent(damager, damaged, damage)
            } else {
                (coolEvent.damaged as LivingEntity).damage(coolEvent.damage, damager)
            }
        }
    }

    fun callItemHitEvent(damager: org.bukkit.entity.Entity, damaged: org.bukkit.entity.Entity, stack: ItemStack, item: CustomItem, event: EntityDamageByEntityEvent, damageType: DamageType = DamageType.GENERAL) {
        val coolEvent = ItemHitEvent(damager, damaged, stack, item, event.damage, damageType)
        coolEvent.callEvent()
        ItemAbilityManager.useStackAbilities(stack, coolEvent)
        GameController.processAnyEvent(coolEvent)

        if(!coolEvent.isCancelled) {
            event.damage = coolEvent.damage
        } else {
            event.isCancelled = true
        }
    }

    fun callItemUseEvent(entity: Player, stack: ItemStack?, item: CustomItem?, event: PlayerInteractEvent) {
        if(stack == null || item == null) return
        val coolEvent = ItemUseEvent(entity, stack, item, event)
        coolEvent.callEvent()
        ItemAbilityManager.useStackAbilities(stack, coolEvent)
        GameController.processAnyEvent(coolEvent)

        if(coolEvent.isCancelled) {
            event.isCancelled = true
        }
    }

    fun callItemConsumeEvent(entity: Player, stack: ItemStack?, item: CustomItem?, event: PlayerItemConsumeEvent) {
        if(stack == null || item == null) return
        val coolEvent = ItemConsumeEvent(entity, stack, item, event)
        coolEvent.callEvent()
        ItemAbilityManager.useStackAbilities(stack, coolEvent)
        GameController.processAnyEvent(coolEvent)

        if(coolEvent.isCancelled) {
            event.isCancelled = true
        }
    }
}