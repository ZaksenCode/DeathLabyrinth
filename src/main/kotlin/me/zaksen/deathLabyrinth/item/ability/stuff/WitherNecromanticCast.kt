package me.zaksen.deathLabyrinth.item.ability.stuff

import me.zaksen.deathLabyrinth.entity.friendly.wither.FriendlyWitherSkeletonEntity
import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.event.item.ItemUseEvent
import me.zaksen.deathLabyrinth.item.ability.ItemAbility
import net.kyori.adventure.text.Component
import org.bukkit.event.Event

class WitherNecromanticCast: ItemAbility(
    Component.translatable("ability.wither_necromantic_cast.name"),
    Component.translatable("ability.wither_necromantic_cast.description")
) {
    override fun invoke(event: Event) {
        if(event !is ItemUseEvent) return

        val stack = event.stack!!
        val item = event.item!!

        if(item.checkCooldown(stack)) {
            val skeleton = FriendlyWitherSkeletonEntity(event.player.location.add(2.0, 1.0, 1.0))
            EventManager.callPlayerSummonFriendlyEntityEvent(event.player, skeleton)
            val skeletonTwo = FriendlyWitherSkeletonEntity(event.player.location.add(-2.0, 1.0, -1.0))
            EventManager.callPlayerSummonFriendlyEntityEvent(event.player, skeletonTwo)
        }
    }
}