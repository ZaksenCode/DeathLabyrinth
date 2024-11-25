package me.zaksen.deathLabyrinth.entity.friendly.wither

import me.zaksen.deathLabyrinth.entity.friendly.FriendlyEntity
import me.zaksen.deathLabyrinth.item.ItemsController
import net.kyori.adventure.text.format.TextColor
import net.minecraft.network.chat.Component
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal
import net.minecraft.world.entity.ai.goal.RestrictSunGoal
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
import net.minecraft.world.entity.monster.WitherSkeleton
import net.minecraft.world.entity.player.Player
import org.bukkit.Location
import org.bukkit.craftbukkit.CraftWorld
import org.bukkit.craftbukkit.inventory.CraftItemStack

class FriendlyWitherSkeletonEntity(location: Location):
    WitherSkeleton(EntityType.WITHER_SKELETON, (location.world as CraftWorld).handle), FriendlyEntity {

    private var despawnTime = 60 * 20

    init {
        this.getAttribute(Attributes.MAX_HEALTH)?.baseValue = 80.0
        this.health = 80.0f
        this.customName = Component.translatable("entity.friendly_skeleton.name").withColor(TextColor.color(124, 242, 81).value())
        this.isCustomNameVisible = true

        this.getAttribute(Attributes.MOVEMENT_SPEED)?.baseValue = 0.29

        this.setItemSlot(EquipmentSlot.MAINHAND, CraftItemStack.asNMSCopy(ItemsController.get("iron_sword")!!.asItemStack()))

        this.setPos(location.x, location.y, location.z)
    }

    override fun registerGoals() {
        goalSelector.addGoal(1, RestrictSunGoal(this))
        goalSelector.addGoal(2, MeleeAttackGoal(this, 0.5, false))
        targetSelector.addGoal(1, HurtByTargetGoal(this,
            Player::class.java,
            FriendlyEntity::class.java
        ))
        targetSelector.addGoal(
            2, NearestAttackableTargetGoal(
                this,
                LivingEntity::class.java, true
            ) {
                it.isAlive && it !is Player && it !is FriendlyEntity
            }
        )
    }

    override fun checkDespawn() {
        despawnTime--

        if(despawnTime <= 0) {
            this.discard()
        }
    }

    override fun dropExperience(attacker: Entity?) { }

    override fun dropEquipment() { }

    override fun shouldDropLoot(): Boolean {
        return false
    }

    override fun hurt(source: DamageSource, amount: Float): Boolean {
        if(source.entity != null && (source.entity is Player || source.entity is FriendlyEntity)) {
            return false
        }

        return super.hurt(source, amount)
    }
}