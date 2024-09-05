package me.zaksen.deathLabyrinth.artifacts

import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import me.zaksen.deathLabyrinth.artifacts.card.CardHolder
import me.zaksen.deathLabyrinth.entity.interaction.ArtifactsCardHitbox
import me.zaksen.deathLabyrinth.entity.item_display.ArtifactsCard
import me.zaksen.deathLabyrinth.entity.item_display.ArtifactsCardIcon
import me.zaksen.deathLabyrinth.entity.text_display.ArtifactsCardName
import me.zaksen.deathLabyrinth.game.GameController
import org.bukkit.Location
import org.bukkit.craftbukkit.entity.CraftEntity
import java.util.Timer
import java.util.UUID
import kotlin.concurrent.timer

// TODO - Add spawn chains like in isaac coop mod
object ArtifactsController {

    val summonedCards: MutableMap<ArtifactsCardHitbox, CardHolder> = mutableMapOf()
    val artifacts: MutableMap<String, Class<out Artifact>> = mutableMapOf()

    var lastHightlitedCards: MutableMap<UUID, CardHolder> = mutableMapOf()
    var highlightTimer: Timer = timer(period = 200) {
        if(summonedCards.isEmpty()) {
            return@timer
        }

        for(entry in GameController.players.filter { it.value.isAlive }) {
            val player = entry.key

            val lastEntity = lastHightlitedCards[player.uniqueId]
            val rayTrace = player.rayTraceEntities(6)

            if(rayTrace == null || rayTrace.hitEntity == null) {
                if(lastEntity != null) {
                    lastEntity.deHighlight()
                    lastHightlitedCards.remove(player.uniqueId)
                }
            } else {
                val hitEntity = rayTrace.hitEntity!!

                if(lastEntity != null) {
                    if(!lastEntity.artifactsCardHitbox.uuid.equals(hitEntity.uniqueId)) {
                        lastEntity.deHighlight()

                        if((hitEntity as CraftEntity).handle is ArtifactsCardHitbox) {
                            val cardHitbox = hitEntity.handle as ArtifactsCardHitbox
                            val holder = summonedCards[cardHitbox] ?: continue
                            holder.highlight()
                            lastHightlitedCards[player.uniqueId] = holder
                        }
                    }
                } else {
                    if((hitEntity as CraftEntity).handle is ArtifactsCardHitbox) {
                        val cardHitbox = hitEntity.handle as ArtifactsCardHitbox
                        val holder = summonedCards[cardHitbox] ?: continue
                        holder.highlight()
                        lastHightlitedCards[player.uniqueId] = holder
                    }
                }
            }
        }
    }

    init {
        artifacts["green_heart"] = GreenHeart::class.java
    }

    fun summonArtifactCard(location: Location, artifact: Artifact) {
        val card = ArtifactsCard(location, artifact)
        val icon = ArtifactsCardIcon(location, artifact)
        val hitbox = ArtifactsCardHitbox(location.subtract(0.0, 0.9, 0.0))
        val name = ArtifactsCardName(location.add(0.0, 1.9, 0.0), artifact)

        val holder = CardHolder(artifact, card, icon, name, hitbox)
        holder.summon(location.world)
        summonedCards[hitbox] = holder
    }

    fun despawnArtifacts() {
        summonedCards.forEach {
            it.value.despawn()
        }
        summonedCards.clear()
    }
}