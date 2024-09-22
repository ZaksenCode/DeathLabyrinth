package me.zaksen.deathLabyrinth.entity.boss.skeleton

import me.zaksen.deathLabyrinth.entity.difficulty.Scaleable
import me.zaksen.deathLabyrinth.entity.difficulty.ScalingStrategies
import me.zaksen.deathLabyrinth.entity.goal.ability.BomberAbilityGoal
import me.zaksen.deathLabyrinth.entity.goal.ability.BomberTeleportAbilityGoal
import me.zaksen.deathLabyrinth.entity.goal.ability.LeapAbility
import net.kyori.adventure.text.format.TextColor
import net.minecraft.network.chat.Component
import net.minecraft.tags.DamageTypeTags
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
import net.minecraft.world.entity.monster.Skeleton
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import org.bukkit.Location
import org.bukkit.craftbukkit.CraftWorld

class BomberEntity(location: Location): Skeleton(EntityType.SKELETON, (location.world as CraftWorld).handle), Scaleable {

    init {
        this.getAttribute(Attributes.MOVEMENT_SPEED)?.baseValue = 0.29
        this.getAttribute(Attributes.ATTACK_DAMAGE)?.baseValue = defaultAttackDamage
        this.getAttribute(Attributes.MAX_HEALTH)?.baseValue = defaultMaxHealth
        this.getAttribute(Attributes.SCALE)?.baseValue = 1.5
        this.health = defaultMaxHealth.toFloat()

        this.customName = Component.literal("Подрывник").withColor(TextColor.color(124, 242, 81).value())
        this.isCustomNameVisible = true

        this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack(Items.TNT))
        this.setItemSlot(EquipmentSlot.OFFHAND, ItemStack(Items.FLINT_AND_STEEL))

        this.setPos(location.x, location.y, location.z)
    }

    override fun registerGoals() {
        goalSelector.addGoal(1, MeleeAttackGoal(this, 1.0, false))
        goalSelector.addGoal(2, BomberAbilityGoal(this))
        goalSelector.addGoal(3, BomberTeleportAbilityGoal(this))
        goalSelector.addGoal(4, LeapAbility(this))

        targetSelector.addGoal(1, HurtByTargetGoal(this, *arrayOfNulls(0)))
        targetSelector.addGoal(
            2, NearestAttackableTargetGoal(
                this,
                Player::class.java, true
            )
        )
    }

    override fun checkDespawn() { }

    override fun dropExperience(attacker: Entity?) { }

    override fun dropEquipment() { }

    override fun shouldDropLoot(): Boolean {
        return false
    }

    override fun hurt(source: DamageSource, amount: Float): Boolean {
        if(source.`is`(DamageTypeTags.IS_EXPLOSION)) {
            return false
        }

        return super.hurt(source, amount)
    }

    override fun scale() {
        this.getAttribute(Attributes.MAX_HEALTH)?.baseValue = ScalingStrategies.BY_COMPLETED_BOSSES.strategy.scale(defaultMaxHealth)
        this.health = ScalingStrategies.BY_COMPLETED_BOSSES.strategy.scale(defaultMaxHealth).toFloat()
        this.getAttribute(Attributes.ATTACK_DAMAGE)?.baseValue = ScalingStrategies.BY_COMPLETED_BOSSES.strategy.scale(defaultAttackDamage)
    }

    companion object {
        const val defaultMaxHealth = 1500.0
        const val defaultAttackDamage = 14.0
    }
}