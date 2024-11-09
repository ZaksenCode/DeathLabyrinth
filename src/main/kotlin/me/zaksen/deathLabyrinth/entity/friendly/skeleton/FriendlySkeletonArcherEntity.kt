package me.zaksen.deathLabyrinth.entity.friendly.skeleton

import me.zaksen.deathLabyrinth.entity.friendly.FriendlyEntity
import net.kyori.adventure.text.format.TextColor
import net.minecraft.network.chat.Component
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.ai.goal.FleeSunGoal
import net.minecraft.world.entity.ai.goal.RestrictSunGoal
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
import net.minecraft.world.entity.monster.Skeleton
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import org.bukkit.Location
import org.bukkit.craftbukkit.CraftWorld

class FriendlySkeletonArcherEntity(location: Location):
    Skeleton(EntityType.SKELETON, (location.world as CraftWorld).handle), FriendlyEntity {

    private var despawnTime = 60 * 20

    init {
        this.getAttribute(Attributes.MAX_HEALTH)?.baseValue = 30.0
        this.health = 30.0f
        this.customName = Component.translatable("entity.friendly_skeleton.name").withColor(TextColor.color(124, 242, 81).value())
        this.isCustomNameVisible = true

        this.getAttribute(Attributes.MOVEMENT_SPEED)?.baseValue = 0.25

        this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack(Items.BOW))

        this.setPos(location.x, location.y, location.z)
    }

    override fun registerGoals() {
        goalSelector.addGoal(1, RestrictSunGoal(this))
        goalSelector.addGoal(2, FleeSunGoal(this, 1.0))
        goalSelector.addGoal(3, WaterAvoidingRandomStrollGoal(this, 1.0))
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