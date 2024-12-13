package me.zaksen.deathLabyrinth.entity.minecart

import me.zaksen.deathLabyrinth.util.drawCircle
import me.zaksen.deathLabyrinth.util.particleLine
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.tags.BlockTags
import net.minecraft.util.Mth
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MoverType
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.vehicle.MinecartSpawner
import net.minecraft.world.level.block.Rotation
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.Particle.DustOptions
import org.bukkit.craftbukkit.CraftWorld
import org.bukkit.util.BoundingBox
import org.bukkit.util.Vector

// TODO - Add room completion condition for follow minecarft
class FollowMinecart(location: Location): MinecartSpawner(EntityType.SPAWNER_MINECART, (location.getWorld() as CraftWorld).handle) {

    var isInverted = false

    init {
        this.maxSpeed = 0.15

        this.setPos(location.x, location.y, location.z)
    }

    override fun tick() {
        super.tick()

        drawCircle(
            world = level().world,
            x = position().x,
            y = position().y,
            z = position().z,
            size = 3.0,
            color = Color.AQUA,
            particleSize = 0.5f
        )

        if(level().world.getNearbyEntities(BoundingBox.of(
                Vector(position().x - 3, position().y - 3, position().z - 3),
                Vector(position().x + 3, position().y + 3, position().z + 3)
            )) {
                it is org.bukkit.entity.Player
            }.isNotEmpty()) {

            val moveDir = if(isInverted) direction.counterClockWise else direction.clockWise
            push(moveDir.stepX.toDouble(), moveDir.stepY.toDouble(), moveDir.stepZ.toDouble())
        } else {
            this.setDeltaMovement(0.0, 0.0, 0.0)
        }
    }

    override fun interact(player: Player, hand: InteractionHand): InteractionResult {
        if(hand == InteractionHand.MAIN_HAND) {
            isInverted = !isInverted

            return InteractionResult.SUCCESS
        }

        return super.interact(player, hand)
    }

    override fun hurt(source: DamageSource, amount: Float): Boolean {
        return false
    }

    override fun isCollidable(ignoreClimbing: Boolean): Boolean {
        return false
    }

    override fun checkDespawn() { }
}