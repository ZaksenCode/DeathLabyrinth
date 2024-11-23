package me.zaksen.deathLabyrinth.item.ability.stuff

import me.zaksen.deathLabyrinth.damage.DamageType
import me.zaksen.deathLabyrinth.entity.projectile.BombEntity
import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.event.item.ItemUseEvent
import me.zaksen.deathLabyrinth.item.ability.ItemAbility
import me.zaksen.deathLabyrinth.item.ability.recipe.Synergy
import net.kyori.adventure.text.Component
import net.minecraft.world.phys.Vec3
import org.bukkit.craftbukkit.entity.CraftPlayer
import org.bukkit.event.Event

class BombCast: ItemAbility(
    Component.translatable("ability.bomb_cast.name"),
    Component.translatable("ability.bomb_cast.description"),
    8.0,
    2.5,
    damageType = DamageType.EXPLODE
) {
    override fun invoke(event: Event) {
        if(event !is ItemUseEvent) return

        val stack = event.stack!!
        val item = event.item!!

        if(item.checkCooldown(stack)) {
            val shotVelocity = event.player.location.direction.multiply(2).normalize().multiply(1.5)

            val projectile = BombEntity(event.player.location.add(shotVelocity).add(0.0, 1.6, 0.0), 2.5, 8.0)

            projectile.deltaMovement = Vec3(shotVelocity.x, shotVelocity.y, shotVelocity.z)
            projectile.fuse = 30
            projectile.owner = (event.player as CraftPlayer).handle
            EventManager.callPlayerSummonSpellEvent(event.player, projectile)
        }
    }

    override fun getUpdateAbility(): String {
        return "bomb_cast_tier_two"
    }

    override fun getSynergies(): List<Synergy> {
        return listOf(
            Synergy("frostball_cast", "frost_bomb_cast"),
            Synergy("fireball_cast", "fire_bomb_cast"),
            Synergy("witherball_cast", "wither_bomb_cast")
        )
    }
}