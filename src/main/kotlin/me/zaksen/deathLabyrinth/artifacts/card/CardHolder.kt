package me.zaksen.deathLabyrinth.artifacts.card

import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import me.zaksen.deathLabyrinth.entity.interaction.ArtifactsCardHitbox
import me.zaksen.deathLabyrinth.entity.item_display.ArtifactsCard
import me.zaksen.deathLabyrinth.entity.item_display.ArtifactsCardIcon
import me.zaksen.deathLabyrinth.entity.text_display.ArtifactsCardName
import me.zaksen.deathLabyrinth.util.tryAddEntity
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import net.minecraft.network.chat.Component
import org.bukkit.Particle
import org.bukkit.World
import org.bukkit.craftbukkit.inventory.CraftItemStack

class CardHolder(
    var artifact: Artifact,
    val cardEntity: ArtifactsCard,
    val cardIconEntity: ArtifactsCardIcon,
    val artifactsCardName: ArtifactsCardName,
    val artifactsCardHitbox: ArtifactsCardHitbox
) {
    fun summon(world: World) {
        world.tryAddEntity(cardEntity)
        world.tryAddEntity(cardIconEntity)
        world.tryAddEntity(artifactsCardName)
        world.tryAddEntity(artifactsCardHitbox)
    }

    fun despawn() {
        val world = cardEntity.level().world

        world.spawnParticle(
            Particle.EFFECT,
            cardEntity.x,
            cardEntity.y,
            cardEntity.z,
            100,
            0.4,
            0.6,
            0.4
        )

        world.playSound(
            Sound.sound(Key.key("minecraft:entity.player.levelup"), Sound.Source.PLAYER, 1.0f, 1.0f),
            cardEntity.x,
            cardEntity.y,
            cardEntity.z
        )

        cardEntity.discard()
        cardIconEntity.discard()
        artifactsCardName.discard()
        artifactsCardHitbox.discard()
    }

    fun highlight() {
        cardEntity.highlight()
    }

    fun deHighlight() {
        cardEntity.deHighlight()
    }

    fun changeArtifact(artifact: Artifact) {
        this.artifact = artifact
        cardIconEntity.itemStack = CraftItemStack.asNMSCopy(artifact.asItemStack())
        artifactsCardName.text = Component.translatable(PlainTextComponentSerializer.plainText().serialize(artifact.name))
    }
}
