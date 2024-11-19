package me.zaksen.deathLabyrinth.item.ability.stuff

import me.zaksen.deathLabyrinth.entity.projectile.FireBallEntity
import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.event.item.ItemUseEvent
import me.zaksen.deathLabyrinth.item.ability.ItemAbility
import net.kyori.adventure.text.Component
import net.minecraft.world.phys.Vec3
import org.bukkit.craftbukkit.entity.CraftPlayer
import org.bukkit.event.Event

class FireballCast: ItemAbility(
    Component.translatable("ability.fireball_cast.name"),
    Component.translatable("ability.fireball_cast.description")
) {
    override fun invoke(event: Event) {
        if(event !is ItemUseEvent) return

        val stack = event.stack!!
        val item = event.item!!

        if(item.checkAndUpdateCooldown(stack)) {
            val shotVelocity = event.player.location.direction.multiply(2).normalize()

            val projectile = FireBallEntity(event.player.location.add(shotVelocity).add(0.0, 1.6, 0.0))
            projectile.deltaMovement = Vec3(shotVelocity.x, shotVelocity.y, shotVelocity.z)
            projectile.setOwner((event.player as CraftPlayer).handle)
            EventManager.callPlayerSummonSpellEvent(event.player, projectile)
        }
    }
}