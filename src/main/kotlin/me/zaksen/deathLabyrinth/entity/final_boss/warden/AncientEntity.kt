package me.zaksen.deathLabyrinth.entity.final_boss.warden

import me.zaksen.deathLabyrinth.entity.difficulty.Scaleable
import me.zaksen.deathLabyrinth.entity.difficulty.ScalingStrategies
import me.zaksen.deathLabyrinth.entity.goal.ability.BomberAbilityGoal
import me.zaksen.deathLabyrinth.entity.goal.ability.BomberTeleportAbilityGoal
import me.zaksen.deathLabyrinth.entity.goal.ability.LeapAbility
import net.kyori.adventure.text.format.TextColor
import net.minecraft.network.chat.Component
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

class AncientEntity(val location: Location): Skeleton(EntityType.SKELETON, (location.world as CraftWorld).handle), Scaleable {

    init {
        this.getAttribute(Attributes.MOVEMENT_SPEED)?.baseValue = 0.29
        this.getAttribute(Attributes.ATTACK_DAMAGE)?.baseValue = defaultAttackDamage
        this.getAttribute(Attributes.MAX_HEALTH)?.baseValue = defaultMaxHealth
        this.getAttribute(Attributes.SCALE)?.baseValue = 1.5
        this.health = defaultMaxHealth.toFloat()

        this.customName = Component.translatable("entity.anceint.name").withColor(TextColor.color(124, 242, 81).value())
        this.isCustomNameVisible = true

        this.setPos(location.x, location.y, location.z)
    }

    override fun registerGoals() {
        goalSelector.addGoal(1, MeleeAttackGoal(this, 1.0, false))
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

    override fun scale() {
        this.getAttribute(Attributes.MAX_HEALTH)?.baseValue = ScalingStrategies.BY_COMPLETED_BOSSES.strategy.scale(defaultMaxHealth)
        this.health = ScalingStrategies.BY_COMPLETED_BOSSES.strategy.scale(defaultMaxHealth).toFloat()
        this.getAttribute(Attributes.ATTACK_DAMAGE)?.baseValue = ScalingStrategies.BY_COMPLETED_BOSSES.strategy.scale(defaultAttackDamage)
    }

    companion object {
        const val defaultMaxHealth = 4000.0
        const val defaultAttackDamage = 24.0
    }
}