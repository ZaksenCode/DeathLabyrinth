package me.zaksen.deathLabyrinth.entity.interaction

import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.game.room.exit.RoomExitController
import me.zaksen.deathLabyrinth.game.room.exit.choice.ChoiceContainer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.Interaction
import net.minecraft.world.entity.player.Player
import org.bukkit.Location
import org.bukkit.craftbukkit.CraftWorld

class ExitChoiceHitbox(
    location: Location,
    val choice: ChoiceContainer? = null
): Interaction(EntityType.INTERACTION, (location.world as CraftWorld).handle) {

    init {
        this.height = 1.8f
        this.width = 1.8f
        this.setPos(location.x, location.y, location.z)
    }

    override fun interact(player: Player, hand: InteractionHand): InteractionResult {
        if(hand == InteractionHand.MAIN_HAND) {
            val cardHolder = RoomExitController.summonedChoices[this]

            if(cardHolder != null) {
                EventManager.callPlayerChoiceSubFloorEvent(player.bukkitEntity as org.bukkit.entity.Player, cardHolder.choice)

                if(choice != null) {
                    choice.process()
                } else {
                    val holder = RoomExitController.summonedChoices[this] ?: return super.interact(player, hand)
                    RoomExitController.despawnChoice(holder)
                }
            }
        }

        return super.interact(player, hand)
    }
}