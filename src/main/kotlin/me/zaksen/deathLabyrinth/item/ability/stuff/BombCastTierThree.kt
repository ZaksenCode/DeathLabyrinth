package me.zaksen.deathLabyrinth.item.ability.stuff

import me.zaksen.deathLabyrinth.damage.DamageType
import me.zaksen.deathLabyrinth.entity.projectile.BombEntity
import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.event.item.ItemUseEvent
import me.zaksen.deathLabyrinth.item.ability.ItemAbility
import me.zaksen.deathLabyrinth.item.ability.recipe.Synergy
import me.zaksen.deathLabyrinth.item.checkCooldown
import net.kyori.adventure.text.Component
import net.minecraft.world.phys.Vec3
import org.bukkit.Location
import org.bukkit.craftbukkit.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.event.Event

class BombCastTierThree: ItemAbility(
    Component.translatable("ability.bomb_cast_tier_three.name"),
    Component.translatable("ability.bomb_cast_tier_three.description"),
    12.0,
    3.5,
    damageType = DamageType.EXPLODE
) {
    override fun invoke(event: Event) {
        if(event !is ItemUseEvent) return

        val stack = event.stack!!

        if(checkCooldown(stack)) {
            val shotVelocity = event.player.location.direction.multiply(2).normalize().multiply(1.5)

            summonBomb(
                event.player,
                event.player.location.add(shotVelocity).add(0.0, 1.6, 0.0),
                Vec3(shotVelocity.x, shotVelocity.y, shotVelocity.z)
            )

            summonBomb(
                event.player,
                event.player.location.add(shotVelocity).add(0.0, 1.6, 0.0),
                Vec3(shotVelocity.x - 0.2, shotVelocity.y, shotVelocity.z - 0.2)
            )

            summonBomb(
                event.player,
                event.player.location.add(shotVelocity).add(0.0, 1.6, 0.0),
                Vec3(shotVelocity.x + 0.2, shotVelocity.y, shotVelocity.z + 0.2)
            )
        }
    }

    private fun summonBomb(player: Player, pos: Location, velocity: Vec3) {
        val projectile = BombEntity(pos.add(0.0, 1.6, 0.0), 3.5, 12.0)

        projectile.deltaMovement = velocity
        projectile.fuse = 30
        projectile.owner = (player as CraftPlayer).handle
        EventManager.callPlayerSummonSpellEvent(player, projectile)
    }

    override fun getConflictAbilities(): List<String> {
        return listOf("bomb_cast", "bomb_cast_tier_two")
    }
}