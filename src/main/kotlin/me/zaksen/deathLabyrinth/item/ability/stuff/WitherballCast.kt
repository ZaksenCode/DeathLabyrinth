package me.zaksen.deathLabyrinth.item.ability.stuff

import me.zaksen.deathLabyrinth.damage.DamageType
import me.zaksen.deathLabyrinth.entity.projectile.WitherBallEntity
import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.event.item.ItemUseEvent
import me.zaksen.deathLabyrinth.item.ability.ItemAbility
import me.zaksen.deathLabyrinth.item.ability.recipe.Synergy
import me.zaksen.deathLabyrinth.item.checkCooldown
import net.kyori.adventure.text.Component
import net.minecraft.world.phys.Vec3
import org.bukkit.craftbukkit.entity.CraftPlayer
import org.bukkit.event.Event

class WitherballCast: ItemAbility(
    Component.translatable("ability.witherball_cast.name"),
    Component.translatable("ability.witherball_cast.description"),
    8.0,
    1.0,
    damageType = DamageType.WITHER
) {
    override fun invoke(event: Event) {
        if(event !is ItemUseEvent) return

        val stack = event.stack!!

        if(checkCooldown(stack)) {
            val shotVelocity = event.player.location.direction.multiply(2).normalize()

            val projectile = WitherBallEntity(event.player.location.add(shotVelocity).add(0.0, 1.6, 0.0))
            projectile.deltaMovement = Vec3(shotVelocity.x, shotVelocity.y, shotVelocity.z)
            projectile.setOwner((event.player as CraftPlayer).handle)
            EventManager.callPlayerSummonSpellEvent(event.player, projectile)
        }
    }

    override fun getSynergies(): List<Synergy> {
        return listOf(
            Synergy("bomb_cast", "wither_bomb_cast"),
            Synergy("witherball_cast", "big_witherball_cast"),
            Synergy("explosion_cast", "wither_explosion_cast"),
            Synergy("laser_cast", "wither_laser_cast"),
            Synergy("necromantic_cast", "wither_necromantic_cast")
        )
    }
}