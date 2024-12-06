package me.zaksen.deathLabyrinth.game.room.exit

import me.zaksen.deathLabyrinth.entity.interaction.ExitChoiceHitbox
import me.zaksen.deathLabyrinth.entity.item_display.ExitChoiceCard
import me.zaksen.deathLabyrinth.entity.item_display.ExitChoiceCardIcon
import me.zaksen.deathLabyrinth.entity.text_display.ExitChoiceName
import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.game.room.RoomFloorController
import me.zaksen.deathLabyrinth.game.room.RoomType
import me.zaksen.deathLabyrinth.game.room.exit.choice.Choice
import me.zaksen.deathLabyrinth.game.room.exit.choice.ChoiceHolder
import me.zaksen.deathLabyrinth.game.room.exit.choice.ChoiceContainer
import me.zaksen.deathLabyrinth.util.asTranslate
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.craftbukkit.entity.CraftEntity
import org.bukkit.inventory.ItemStack
import java.util.*
import kotlin.concurrent.timer

object RoomExitController {

    val summonedChoices: MutableMap<ExitChoiceHitbox, ChoiceHolder> = mutableMapOf()
    val choices: MutableList<ChoiceContainer> = mutableListOf()

    private var lastHighlightedChoices: MutableMap<UUID, ChoiceHolder> = mutableMapOf()
    var highlightTimer: Timer = timer(period = 200) {
        if(summonedChoices.isEmpty()) {
            return@timer
        }

        for(entry in GameController.players.filter { it.value.isAlive }) {
            val player = entry.key

            val lastEntity = lastHighlightedChoices[player.uniqueId]
            val rayTrace = player.rayTraceEntities(6)

            if(rayTrace == null || rayTrace.hitEntity == null) {
                if(lastEntity != null) {
                    lastEntity.deHighlight()
                    lastHighlightedChoices.remove(player.uniqueId)
                }
            } else {
                val hitEntity = rayTrace.hitEntity!!

                if(lastEntity != null) {
                    if(!lastEntity.exitCardHitbox.uuid.equals(hitEntity.uniqueId)) {
                        lastEntity.deHighlight()

                        if((hitEntity as CraftEntity).handle is ExitChoiceHitbox) {
                            val cardHitbox = hitEntity.handle as ExitChoiceHitbox
                            val holder = summonedChoices[cardHitbox] ?: continue
                            holder.highlight()
                            lastHighlightedChoices[player.uniqueId] = holder
                        }
                    }
                } else {
                    if((hitEntity as CraftEntity).handle is ExitChoiceHitbox) {
                        val cardHitbox = hitEntity.handle as ExitChoiceHitbox
                        val holder = summonedChoices[cardHitbox] ?: continue
                        holder.highlight()
                        lastHighlightedChoices[player.uniqueId] = holder
                    }
                }
            }
        }
    }

    fun summonExitChoiceCard(location: Location, choice: Choice, choiceContainer: ChoiceContainer? = null): ChoiceHolder {
        val card = ExitChoiceCard(location, choice)
        val icon = ExitChoiceCardIcon(location, choice)
        val hitbox = ExitChoiceHitbox(location.subtract(0.0, 0.9, 0.0), choiceContainer)
        val name = ExitChoiceName(location.add(0.0, 1.9, 0.0), choice)

        val holder = ChoiceHolder(choice, card, icon, name, hitbox)
        holder.summon(location.world)
        summonedChoices[hitbox] = holder

        return holder
    }

    fun despawnChoice(choiceHolder: ChoiceHolder) {
        choiceHolder.despawn()
        summonedChoices.remove(choiceHolder.exitCardHitbox)
    }

    // TODO - Add random generating values and boss check
    fun startChoice(location: Location) {
        if(RoomFloorController.shouldGenerateLocationChoice()) {
            // TODO - Location choice (size 2)
            println("\nTODO - Spawn location choice!!!!!\n")

            return
        }

        if(RoomFloorController.shouldGenerateBossSubFloor()) {
            startChoice(location, 1, true)
            return
        }

        startChoice(location, 3, false)
    }

    fun startChoice(location: Location, size: Int, isBoss: Boolean = false) {
        val newChoice = ChoiceContainer(location, 1, size, isBoss)
        choices.add(newChoice)
        newChoice.process()
    }

    fun despawnChoices() {
        choices.forEach {
            it.clearChoiceCards()
        }
        summonedChoices.forEach {
            it.value.despawn()
        }
        summonedChoices.clear()
        choices.clear()
    }

    fun removeExitChoice(choiceContainer: ChoiceContainer) {
        choiceContainer.clearChoiceCards()
        choices.remove(choiceContainer)
    }

    fun getChoice(isBoss: Boolean): Choice {
        return if(isBoss) getBossChoice() else getRandomChoice()
    }

    // TODO - Generate choices
    fun getStartChoice(): Choice {
        return Choice("choice.text.name".asTranslate(), 3, setOf(RoomType.NORMAL), ItemStack.of(Material.AIR))
    }

    // TODO - Generate choices
    fun getRandomChoice(): Choice {
        return Choice("choice.text.name".asTranslate(), 3, setOf(RoomType.NORMAL), ItemStack.of(Material.BOW))
    }

    // TODO - Generate choices
    fun getBossChoice(): Choice {
        return Choice("choice.text.name".asTranslate(), 1, setOf(RoomType.BOSS), ItemStack.of(Material.NETHERITE_SWORD))
    }

    // TODO - Generate choices
    fun getLocationChoice(): Choice {
        return Choice("choice.text.name".asTranslate(), 3, setOf(RoomType.NORMAL), ItemStack.of(Material.SPRUCE_SAPLING))
    }
}