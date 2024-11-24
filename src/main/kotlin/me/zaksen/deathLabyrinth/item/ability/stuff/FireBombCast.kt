package me.zaksen.deathLabyrinth.item.ability.stuff

import me.zaksen.deathLabyrinth.damage.DamageType
import me.zaksen.deathLabyrinth.entity.projectile.FireBombEntity
import me.zaksen.deathLabyrinth.entity.projectile.FrostBombEntity
import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.event.item.ItemUseEvent
import me.zaksen.deathLabyrinth.item.ability.ItemAbility
import net.kyori.adventure.text.Component
import net.minecraft.world.phys.Vec3
import org.bukkit.craftbukkit.entity.CraftPlayer
import org.bukkit.event.Event

class FireBombCast: ItemAbility(
    Component.translatable("ability.fire_bomb_cast.name"),
    Component.translatable("ability.fire_bomb_cast.description"),
    18.0,
    3.5,
    damageType = DamageType.FIRE
) {
    override fun invoke(event: Event) {
        if(event !is ItemUseEvent) return

        val stack = event.stack!!
        val item = event.item!!

        if(item.checkCooldown(stack)) {
            val shotVelocity = event.player.location.direction.multiply(2).normalize().multiply(1.5)

            val projectile = FireBombEntity(event.player.location.add(shotVelocity).add(0.0, 1.6, 0.0), 3.5, 18.0)

            projectile.deltaMovement = Vec3(shotVelocity.x, shotVelocity.y, shotVelocity.z)
            projectile.fuse = 30
            projectile.owner = (event.player as CraftPlayer).handle
            EventManager.callPlayerSummonSpellEvent(event.player, projectile)
        }
    }
}