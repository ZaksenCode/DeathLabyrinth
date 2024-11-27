package me.zaksen.deathLabyrinth.artifacts.chain

import me.zaksen.deathLabyrinth.artifacts.ArtifactsController
import me.zaksen.deathLabyrinth.artifacts.ArtifactsController.getRandomArtifact
import me.zaksen.deathLabyrinth.artifacts.ArtifactsController.summonArtifactCard
import me.zaksen.deathLabyrinth.artifacts.api.ArtifactRarity
import me.zaksen.deathLabyrinth.artifacts.card.CardHolder
import org.bukkit.Location

class ArtifactsChain(val location: Location, var count: Int, private val isGoodly: Boolean) {

    private val actualCards: MutableSet<CardHolder> = mutableSetOf()

    init {
        spawnCards()
    }

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
        actualCards.add(summonArtifactCard(
            location,
            if(isGoodly) getRandomArtifact(ArtifactRarity.GODLY)
            else getRandomArtifact(),
            this
        ))
        actualCards.add(summonArtifactCard(
            location.subtract(0.0, 1.0, 3.0),
            if(isGoodly) getRandomArtifact(ArtifactRarity.GODLY)
            else getRandomArtifact(),
            this
        ))
        actualCards.add(summonArtifactCard(
            location.subtract(0.0, 1.0, 3.0),
            if(isGoodly) getRandomArtifact(ArtifactRarity.GODLY)
            else getRandomArtifact(),
            this
        ))
        location.add(0.0, -1.0, 6.0)
    }
}