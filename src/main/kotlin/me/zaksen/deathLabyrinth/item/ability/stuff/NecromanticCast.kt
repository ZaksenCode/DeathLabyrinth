package me.zaksen.deathLabyrinth.item.ability.stuff

import me.zaksen.deathLabyrinth.entity.friendly.skeleton.FriendlySkeletonArcherEntity
import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.event.item.ItemUseEvent
import me.zaksen.deathLabyrinth.item.ability.ItemAbility
import me.zaksen.deathLabyrinth.item.ability.recipe.Synergy
import me.zaksen.deathLabyrinth.item.checkCooldown
import net.kyori.adventure.text.Component
import org.bukkit.event.Event

class NecromanticCast: ItemAbility(
    Component.translatable("ability.necromantic_cast.name"),
    Component.translatable("ability.necromantic_cast.description")
) {
    override fun invoke(event: Event) {
        if(event !is ItemUseEvent) return

        val stack = event.stack!!

        if(checkCooldown(stack)) {
            val skeleton = FriendlySkeletonArcherEntity(event.player.location.add(2.0, 1.0, 1.0))
            EventManager.callPlayerSummonFriendlyEntityEvent(event.player, skeleton)
            val skeletonTwo = FriendlySkeletonArcherEntity(event.player.location.add(-2.0, 1.0, -1.0))
            EventManager.callPlayerSummonFriendlyEntityEvent(event.player, skeletonTwo)
        }
    }

    override fun getSynergies(): List<Synergy> {
        return listOf(
            Synergy("witherball_cast", "wither_necromantic_cast")
        )
    }
}