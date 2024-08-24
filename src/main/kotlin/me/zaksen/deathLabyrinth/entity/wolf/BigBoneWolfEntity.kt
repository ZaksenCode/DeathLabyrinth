package me.zaksen.deathLabyrinth.entity.wolf

import net.minecraft.network.chat.Component
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.ai.goal.*
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
import net.minecraft.world.entity.animal.Wolf
import net.minecraft.world.entity.player.Player
import org.bukkit.Location
import org.bukkit.craftbukkit.CraftWorld

class BigBoneWolfEntity(location: Location): Wolf(EntityType.WOLF, (location.getWorld() as CraftWorld).handle) {

    init {
        this.getAttribute(Attributes.MAX_HEALTH)?.baseValue = 32.0
        this.health = 32.0f
        this.customName = Component.literal("Большой волк")
        this.isCustomNameVisible = true
        this.getAttribute(Attributes.SCALE)?.baseValue = 1.5
        this.setPos(location.x, location.y, location.z)
    }

    override fun registerGoals() {
        goalSelector.addGoal(1, LeapAtTargetGoal(this, 0.4f))
        goalSelector.addGoal(2, MeleeAttackGoal(this, 1.0, false))
        goalSelector.addGoal(3, WaterAvoidingRandomStrollGoal(this, 1.0))

        targetSelector.addGoal(
            1,
            HurtByTargetGoal(
                this,
                *arrayOfNulls(0)
            )
        )
        targetSelector.addGoal(
            2, NearestAttackableTargetGoal(
                this,
                Player::class.java, true
            )
        )
    }

    override fun checkDespawn() { }
}