package me.zaksen.deathLabyrinth.game.room.exit.choice

import me.zaksen.deathLabyrinth.game.room.exit.RoomExitController
import me.zaksen.deathLabyrinth.game.room.exit.RoomExitController.getChoice
import me.zaksen.deathLabyrinth.game.room.exit.RoomExitController.summonExitChoiceCard
import org.bukkit.Location

class ChoiceContainer(val location: Location, var count: Int, var size: Int, private val isBoss: Boolean) {

    private val actualChoices: MutableSet<ChoiceHolder> = mutableSetOf()

    init {
        spawnCards()
    }

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

    // TODO - Add size parametr logic
    // FIXME - Didn't work right with size
    private fun spawnCards() {
        location.add(0.0, 0.5, 1.5 * size)

        for(i in 1..size) {
            location.subtract(0.0, 0.0, 3.0)

            actualChoices.add(summonExitChoiceCard(
                location.clone(),
                getChoice(isBoss),
                this
            ))
        }

        location.add(0.0, 0.0, 6.0)
    }
}