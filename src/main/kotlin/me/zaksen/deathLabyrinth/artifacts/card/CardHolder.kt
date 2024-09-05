package me.zaksen.deathLabyrinth.artifacts.card

import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import me.zaksen.deathLabyrinth.entity.interaction.ArtifactsCardHitbox
import me.zaksen.deathLabyrinth.entity.item_display.ArtifactsCard
import me.zaksen.deathLabyrinth.entity.item_display.ArtifactsCardIcon
import me.zaksen.deathLabyrinth.entity.text_display.ArtifactsCardName
import me.zaksen.deathLabyrinth.util.tryAddEntity
import org.bukkit.World

data class CardHolder(
    val artifact: Artifact,
    val cardEntity: ArtifactsCard,
    val artifactEntity: ArtifactsCardIcon,
    val artifactsCardName: ArtifactsCardName,
    val artifactsCardHitbox: ArtifactsCardHitbox
) {
    fun summon(world: World) {
        world.tryAddEntity(cardEntity)
        world.tryAddEntity(artifactEntity)
        world.tryAddEntity(artifactsCardName)
        world.tryAddEntity(artifactsCardHitbox)
    }

    fun despawn() {
        cardEntity.discard()
        artifactEntity.discard()
        artifactsCardName.discard()
        artifactsCardHitbox.discard()
    }

    fun highlight() {
        cardEntity.highlight()
    }

    fun deHighlight() {
        cardEntity.deHighlight()
    }
}
