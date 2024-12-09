package me.zaksen.deathLabyrinth.game.room.exit.choice

import me.zaksen.deathLabyrinth.game.room.LocationType
import me.zaksen.deathLabyrinth.game.room.exit.RoomExitController
import me.zaksen.deathLabyrinth.game.room.exit.RoomExitController.getChoice
import me.zaksen.deathLabyrinth.game.room.exit.RoomExitController.getLocationChoice
import me.zaksen.deathLabyrinth.game.room.exit.RoomExitController.summonExitChoiceCard
import org.bukkit.Location

class ChoiceContainer(val location: Location, var count: Int, var size: Int, private val choiceType: ChoiceType, private val locations: MutableList<LocationType> = mutableListOf()) {

    private val actualChoices: MutableSet<ChoiceHolder> = mutableSetOf()

    fun process() {
        clearChoiceCards()

        if(count > 0) {
            spawnCards()
            count--
        } else {
            RoomExitController.removeExitChoice(this)
        }
    }

    fun clearChoiceCards() {
        actualChoices.forEach {
            RoomExitController.despawnChoice(it)
        }
        actualChoices.clear()
    }

    // FIXME - Didn't work right with size
    private fun spawnCards() {
        location.add(0.0, 1.0, 2.0 * size)

        for(i in 1..size) {
            location.subtract(0.0, 0.0, getRemoveSize())

            actualChoices.add(summonExitChoiceCard(
                location.clone(),
                prepareChoice(),
                this
            ))
        }

        location.add(0.0, 0.0, 6.0)
    }

    private fun getRemoveSize(): Double {
        return if(size > 1) 3.0 else 2.0
    }

    private fun prepareChoice(): Choice {

        if(choiceType == ChoiceType.FLOOR) {
            val first = locations.removeFirst()
            return getLocationChoice(first)
        }

        return getChoice(choiceType)
    }
}