package me.zaksen.deathLabyrinth.data

import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import me.zaksen.deathLabyrinth.classes.PlayerClass
import java.util.*

// TODO - Add additional equipment slots
data class PlayerData(
    var isReady: Boolean = false,
    var isAlive: Boolean = true,
    var money: Int = 0,
    var playerClass: PlayerClass? = null,
    val artifacts: MutableList<Artifact> = mutableListOf(),
    val accessories: AccessoriesInventory = AccessoriesInventory()
) {
    fun addArtifact(artifact: Artifact, owner: UUID? = null) {
        if(artifact.ownerUuid == null || owner != null) {
            artifact.ownerUuid = owner
        }

        if(artifacts.contains(artifact)) {
            val index = artifacts.indexOf(artifact)
            val artifact1 = artifacts[index]
            artifact1.count++
            artifacts[index] = artifact1
        } else {
            artifacts.add(artifact)
        }
    }

    fun removeArtifact(artifact: Artifact) {
        if(artifacts.contains(artifact)) {
            artifacts.remove(artifact)
        }
    }
}