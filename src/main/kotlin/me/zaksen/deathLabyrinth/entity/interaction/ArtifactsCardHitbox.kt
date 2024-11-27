package me.zaksen.deathLabyrinth.entity.interaction

import me.zaksen.deathLabyrinth.artifacts.ArtifactsController
import me.zaksen.deathLabyrinth.event.EventManager
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.Interaction
import net.minecraft.world.entity.player.Player
import org.bukkit.Location
import org.bukkit.craftbukkit.CraftWorld

class ArtifactsCardHitbox(
    location: Location,
    val processChain: Boolean = true
): Interaction(EntityType.INTERACTION, (location.world as CraftWorld).handle) {

    init {
        this.height = 1.8f
        this.width = 1.8f
        this.setPos(location.x, location.y, location.z)
    }

    override fun interact(player: Player, hand: InteractionHand): InteractionResult {
        if(hand == InteractionHand.MAIN_HAND) {
            val cardHolder = ArtifactsController.summonedCards[this]

            if(cardHolder != null) {
                EventManager.callPlayerPickupArtifactsEvent(player.bukkitEntity as org.bukkit.entity.Player, cardHolder.artifact, processChain)

                if(!processChain) {
                    ArtifactsController.despawnArtifact(cardHolder)
                }
            }
        }

        return super.interact(player, hand)
    }
}