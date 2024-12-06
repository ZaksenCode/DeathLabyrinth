package me.zaksen.deathLabyrinth.game.room.exit.choice

import me.zaksen.deathLabyrinth.entity.interaction.ExitChoiceHitbox
import me.zaksen.deathLabyrinth.entity.item_display.ExitChoiceCard
import me.zaksen.deathLabyrinth.entity.item_display.ExitChoiceCardIcon
import me.zaksen.deathLabyrinth.entity.text_display.ExitChoiceName
import me.zaksen.deathLabyrinth.util.tryAddEntity
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import org.bukkit.Particle
import org.bukkit.World

class ChoiceHolder(
    var choice: Choice,
    val cardEntity: ExitChoiceCard,
    val cardIconEntity: ExitChoiceCardIcon,
    val exitCardName: ExitChoiceName,
    val exitCardHitbox: ExitChoiceHitbox
) {
    fun summon(world: World) {
        world.tryAddEntity(cardEntity)
        world.tryAddEntity(cardIconEntity)
        world.tryAddEntity(exitCardName)
        world.tryAddEntity(exitCardHitbox)
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
        exitCardName.discard()
        exitCardHitbox.discard()
    }

    fun highlight() {
        cardEntity.highlight()
    }

    fun deHighlight() {
        cardEntity.deHighlight()
    }
}