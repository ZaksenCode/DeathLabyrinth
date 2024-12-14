package me.zaksen.deathLabyrinth.entity.villager

import me.zaksen.deathLabyrinth.entity.friendly.FriendlyEntity
import me.zaksen.deathLabyrinth.entity.skeleton.SkeletonArcherEntity
import me.zaksen.deathLabyrinth.entity.skeleton.SkeletonWarriorEntity
import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.game.room.RoomController
import me.zaksen.deathLabyrinth.menu.Menus
import net.kyori.adventure.text.format.TextColor
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
import net.minecraft.world.entity.monster.Skeleton
import net.minecraft.world.entity.npc.Villager
import net.minecraft.world.entity.player.Player
import org.bukkit.Location
import org.bukkit.craftbukkit.CraftWorld

class BlacksmithEntity(location: Location): Villager(EntityType.VILLAGER, (location.world as CraftWorld).handle), FriendlyEntity {

    private var despawnTicks = 120 * 20

    init {
        this.getAttribute(Attributes.MAX_HEALTH)?.baseValue = 50.0
        this.health = 50.0f
        this.customName = Component.translatable( "entity.blacksmith.name").withColor(TextColor.color(124, 242, 81).value())
        this.isCustomNameVisible = true
        this.isNoAi = true
        this.isNoGravity = false

        this.setPos(location.x, location.y, location.z)
    }

    override fun registerGoals() {
        goalSelector.addGoal(1, MeleeAttackGoal(this, 1.0, false))
        goalSelector.addGoal(
            2, LookAtPlayerGoal(
                this,
                Player::class.java, 8.0f
            )
        )
        targetSelector.addGoal(1, HurtByTargetGoal(this,
            Skeleton::class.java,
            SkeletonArcherEntity::class.java,
            SkeletonWarriorEntity::class.java
        )
        )
    }

    override fun mobInteract(player: Player, hand: InteractionHand): InteractionResult {
        if(target == null) {
            Menus.blacksmithMenu(player.bukkitEntity as org.bukkit.entity.Player)
        }
        return super.mobInteract(player, hand)
    }

    override fun checkDespawn() {
        despawnTicks--

        if(despawnTicks <= 0) {
            for(i in 0..30) {
                this.level().addParticle(
                    ParticleTypes.EFFECT,
                    x,
                    y,
                    z,
                    0.2,
                    0.4,
                    0.2
                )
            }

            EventManager.callPlayerKillEntityEvent(null, this.bukkitLivingEntity, listOf())
            this.discard()
        }
    }

    override fun dropExperience(attacker: Entity?) { }

    override fun dropEquipment() { }

    override fun shouldDropLoot(): Boolean {
        return false
    }
}