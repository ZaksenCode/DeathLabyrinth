package me.zaksen.deathLabyrinth.game.room.exit

import me.zaksen.deathLabyrinth.entity.interaction.ExitChoiceHitbox
import me.zaksen.deathLabyrinth.entity.item_display.ExitChoiceCard
import me.zaksen.deathLabyrinth.entity.item_display.ExitChoiceCardIcon
import me.zaksen.deathLabyrinth.entity.text_display.ExitChoiceName
import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.game.room.LocationType
import me.zaksen.deathLabyrinth.game.room.RoomFloorController
import me.zaksen.deathLabyrinth.game.room.RoomType
import me.zaksen.deathLabyrinth.game.room.exit.choice.Choice
import me.zaksen.deathLabyrinth.game.room.exit.choice.ChoiceHolder
import me.zaksen.deathLabyrinth.game.room.exit.choice.ChoiceContainer
import me.zaksen.deathLabyrinth.game.room.exit.choice.ChoiceType
import me.zaksen.deathLabyrinth.util.asTranslate
import me.zaksen.deathLabyrinth.util.customModel
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.craftbukkit.entity.CraftEntity
import org.bukkit.inventory.ItemStack
import java.util.*
import kotlin.concurrent.timer

object RoomExitController {

    val summonedChoices: MutableMap<ExitChoiceHitbox, ChoiceHolder> = mutableMapOf()
    private val choices: MutableList<ChoiceContainer> = mutableListOf()

    private val availableRoomChoices = mutableSetOf(
        Choice("choice.normal.name".asTranslate(), 3, mutableListOf(), setOf(RoomType.NORMAL), ItemStack.of(Material.PAPER).customModel(2000)),
        Choice("choice.elite.name".asTranslate(), 3, mutableListOf(), setOf(RoomType.ELITE), ItemStack.of(Material.PAPER).customModel(2001)),
        Choice("choice.challenge.name".asTranslate(), 1, mutableListOf(RoomType.CHALLENGE), setOf(RoomType.NORMAL), ItemStack.of(Material.PAPER).customModel(2003)),
        Choice("choice.shop.name".asTranslate(), 1, mutableListOf(RoomType.SHOP_START, RoomType.SHOP_END), setOf(RoomType.NORMAL), ItemStack.of(Material.PAPER).customModel(2004)),
        Choice("choice.forge.name".asTranslate(), 1, mutableListOf(RoomType.FORGE), setOf(RoomType.NORMAL), ItemStack.of(Material.PAPER).customModel(2005)),
        Choice("choice.treasure.name".asTranslate(), 1, mutableListOf(RoomType.TREASURE), setOf(RoomType.NORMAL), ItemStack.of(Material.PAPER).customModel(2006))
    )

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

    fun startChoice(location: Location) {
        if(RoomFloorController.shouldGenerateLocationChoice()) {
            val locations = LocationType.getLocationsFor(RoomFloorController.floor + 1)

            if(locations.isEmpty()) {
                GameController.endGameWin()
                return
            }

            startChoice(location, locations.size, ChoiceType.FLOOR, locations.toMutableList())
            return
        }

        if(RoomFloorController.shouldGenerateBossSubFloor()) {
            startChoice(location, 1, ChoiceType.BOSS)
            return
        }

        startChoice(location, 3, ChoiceType.SUB_FLOOR)
    }

    fun startChoice(location: Location, size: Int, choiceType: ChoiceType, locations: MutableList<LocationType> = mutableListOf()) {
        val newChoice = ChoiceContainer(location, 1, size, choiceType, locations)
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

    fun getChoice(choiceType: ChoiceType): Choice {
        return when(choiceType) {
            ChoiceType.SUB_FLOOR -> getRandomChoice()
            ChoiceType.BOSS -> getBossChoice()
            ChoiceType.FLOOR -> getRandomChoice() // Not should be possible
        }
    }

    fun getLocationChoice(location: LocationType): Choice {
        return Choice(location.displayName, location.startLength, mutableListOf(), setOf(RoomType.NORMAL), location.displayItem, location)
    }

    private fun getRandomChoice(): Choice {
        return availableRoomChoices.random().copy(location = RoomFloorController.actualLocation)
    }

    private fun getBossChoice(): Choice {
        return Choice("choice.boss.name".asTranslate(), 1, mutableListOf(), setOf(RoomType.BOSS), ItemStack.of(Material.PAPER).customModel(2002), RoomFloorController.actualLocation)
    }
}