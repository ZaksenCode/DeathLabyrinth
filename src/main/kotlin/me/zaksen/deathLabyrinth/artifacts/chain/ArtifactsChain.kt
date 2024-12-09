package me.zaksen.deathLabyrinth.artifacts.chain

import me.zaksen.deathLabyrinth.artifacts.ArtifactsController
import me.zaksen.deathLabyrinth.artifacts.ArtifactsController.getRandomArtifact
import me.zaksen.deathLabyrinth.artifacts.ArtifactsController.summonArtifactCard
import me.zaksen.deathLabyrinth.artifacts.api.ArtifactRarity
import me.zaksen.deathLabyrinth.artifacts.card.CardHolder
import org.bukkit.Location

class ArtifactsChain(val location: Location, var count: Int, var size: Int, private val isGoodly: Boolean) {

    private val actualCards: MutableSet<CardHolder> = mutableSetOf()

    fun process() {
        clearChainCards()

        if(count > 0) {
            spawnCards()
            count--
        } else {
            ArtifactsController.removeArtifactsChain(this)
        }
    }

    fun clearChainCards() {
        actualCards.forEach {
            ArtifactsController.despawnArtifact(it)
        }
        actualCards.clear()
    }

    private fun spawnCards() {
        location.add(0.0, 1.0, 2.0 * size)

        for(i in 1..size) {
            location.subtract(0.0, 0.0, getRemoveSize())

            actualCards.add(summonArtifactCard(
                location.clone(),
                if(isGoodly) getRandomArtifact(ArtifactRarity.GODLY)
                else getRandomArtifact(),
                this
            ))
        }

        location.add(0.0, 0.0, 6.0)
    }

    private fun getRemoveSize(): Double {
        return if(size > 1) 3.0 else 2.0
    }
}