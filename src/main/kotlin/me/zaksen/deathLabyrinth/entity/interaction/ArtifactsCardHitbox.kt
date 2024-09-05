package me.zaksen.deathLabyrinth.entity.interaction

import me.zaksen.deathLabyrinth.artifacts.ArtifactsController
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.Interaction
import net.minecraft.world.entity.player.Player
import org.bukkit.Location
import org.bukkit.craftbukkit.CraftWorld

class ArtifactsCardHitbox(location: Location): Interaction(EntityType.INTERACTION, (location.world as CraftWorld).handle) {

    init {
        this.height = 1.8f
        this.width = 1.8f
        this.setPos(location.x, location.y, location.z)
    }

    override fun interact(player: Player, hand: InteractionHand): InteractionResult {
        if(hand == InteractionHand.MAIN_HAND) {
            ArtifactsController.summonedCards[this]?.despawn()
        }

        return super.interact(player, hand)
    }
}