package me.zaksen.deathLabyrinth.item.ability.stuff

import me.zaksen.deathLabyrinth.damage.DamageType
import me.zaksen.deathLabyrinth.entity.projectile.FrostBallEntity
import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.event.item.ItemUseEvent
import me.zaksen.deathLabyrinth.item.ability.ItemAbility
import me.zaksen.deathLabyrinth.item.ability.recipe.Synergy
import me.zaksen.deathLabyrinth.item.checkCooldown
import net.kyori.adventure.text.Component
import net.minecraft.world.phys.Vec3
import org.bukkit.craftbukkit.entity.CraftPlayer
import org.bukkit.event.Event

class FrostballCast: ItemAbility(
    Component.translatable("ability.frostball_cast.name"),
    Component.translatable("ability.frostball_cast.description"),
    4.0,
    1.5,
    damageType = DamageType.WATER
) {
    override fun invoke(event: Event) {
        if(event !is ItemUseEvent) return

        val stack = event.stack!!

        if(checkCooldown(stack)) {
            val shotVelocity = event.player.location.direction.multiply(2).normalize()

            val projectile = FrostBallEntity(event.player.location.add(shotVelocity).add(0.0, 1.6, 0.0))
            projectile.deltaMovement = Vec3(shotVelocity.x, shotVelocity.y, shotVelocity.z)
            projectile.setOwner((event.player as CraftPlayer).handle)
            EventManager.callPlayerSummonSpellEvent(event.player, projectile)
        }
    }

    override fun getSynergies(): List<Synergy> {
        return listOf(
            Synergy("bomb_cast", "frost_bomb_cast"),
            Synergy("frostball_cast", "big_frostball_cast"),
            Synergy("explosion_cast", "frost_explosion_cast"),
            Synergy("fireball_cast", "waterball_cast"),
            Synergy("laser_cast", "frost_laser_cast")
        )
    }
}