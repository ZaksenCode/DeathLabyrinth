package me.zaksen.deathLabyrinth.item.ability.stuff

import me.zaksen.deathLabyrinth.damage.DamageType
import me.zaksen.deathLabyrinth.event.item.ItemUseEvent
import me.zaksen.deathLabyrinth.item.ability.ItemAbility
import me.zaksen.deathLabyrinth.util.launchVibration
import net.kyori.adventure.text.Component
import org.bukkit.event.Event

class SculkCast: ItemAbility(
    Component.translatable("ability.sculk_cast.name"),
    Component.translatable("ability.sculk_cast.description"),
    6.0,
    damageType = DamageType.SCULK
) {
    override fun invoke(event: Event) {
        if(event !is ItemUseEvent) return

        val stack = event.stack!!
        val item = event.item!!

        val rayCastBlock = event.player.rayTraceBlocks(128.0)

        if(rayCastBlock != null && rayCastBlock.hitBlock != null && item.checkCooldown(stack)) {
            val shotVelocity = event.player.location.direction.multiply(2).normalize().multiply(1.5)

            launchVibration(
                (rayCastBlock.hitBlock!!.location.distance(shotVelocity.toLocation(event.player.world)) * 2).toInt(),
                event.player.eyeLocation,
                rayCastBlock.hitBlock!!.location,
                10
            )
        }
    }
}