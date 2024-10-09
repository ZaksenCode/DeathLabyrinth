package me.zaksen.deathLabyrinth.artifacts

import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import me.zaksen.deathLabyrinth.artifacts.api.ArtifactRarity
import me.zaksen.deathLabyrinth.artifacts.card.CardHolder
import me.zaksen.deathLabyrinth.artifacts.custom.*
import me.zaksen.deathLabyrinth.artifacts.custom.godly.Greediness
import me.zaksen.deathLabyrinth.entity.interaction.ArtifactsCardHitbox
import me.zaksen.deathLabyrinth.entity.item_display.ArtifactsCard
import me.zaksen.deathLabyrinth.entity.item_display.ArtifactsCardIcon
import me.zaksen.deathLabyrinth.entity.text_display.ArtifactsCardName
import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.util.WeightedRandomList
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.craftbukkit.entity.CraftEntity
import org.bukkit.entity.Player
import java.util.Timer
import java.util.UUID
import kotlin.concurrent.timer

object ArtifactsController {

    val rarityList = WeightedRandomList<ArtifactRarity>()
    val summonedCards: MutableMap<ArtifactsCardHitbox, CardHolder> = mutableMapOf()
    val artifacts: MutableMap<String, Class<out Artifact>> = mutableMapOf()

    private var lastChainLocation: Location = Location(Bukkit.getWorld("world"), 0.0, 0.0, 0.0)
    private var remainingChains: Int = 0

    private var lastHightlitedCards: MutableMap<UUID, CardHolder> = mutableMapOf()
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
        rarityList.addEntry(ArtifactRarity.COMMON, 0.6)
        rarityList.addEntry(ArtifactRarity.RARE, 0.3)
        rarityList.addEntry(ArtifactRarity.EPIC, 0.1)

        // COMMON
        artifacts["green_heart"] = GreenHeart::class.java
        artifacts["mystic_potion"] = MysticPotion::class.java

        // RARE
        artifacts["rusty_dagger"] = RustyDagger::class.java

        // EPIC
        artifacts["blood_lust"] = BloodLust::class.java
        artifacts["jewel"] = Jewel::class.java
        artifacts["mirror_of_revenge"] = MirrorOfRevenge::class.java

        // GODLY
        artifacts["greediness"] = Greediness::class.java
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

    fun processArtifactPickup(player: Player, cardHolder: CardHolder) {
        processArtifactsChain()
        val playerData = GameController.players[player] ?: return
        playerData.addArtifact(cardHolder.artifact)
    }

    fun startArtifactsChain(location: Location, count: Int = 1, isGoodly: Boolean = false) {
        lastChainLocation = location
        remainingChains = count
        processArtifactsChain(isGoodly)
    }

    fun processArtifactsChain(isGoodly: Boolean = false) {
        despawnArtifacts()

        if(isGoodly) {
            remainingChains = 1
        }

        if(remainingChains > 0) {
            summonArtifactCard(
                lastChainLocation,
                if(isGoodly) getRandomArtifact(ArtifactRarity.GODLY)
                else getRandomArtifact()
            )
            println("Last chain location: ${lastChainLocation.x} ${lastChainLocation.y} ${lastChainLocation.z}")
            summonArtifactCard(
                lastChainLocation.subtract(0.0, 1.0, 3.0),
                if(isGoodly) getRandomArtifact(ArtifactRarity.GODLY)
                else getRandomArtifact()
            )
            summonArtifactCard(
                lastChainLocation.subtract(0.0, 1.0, 3.0),
                if(isGoodly) getRandomArtifact(ArtifactRarity.GODLY)
                else getRandomArtifact()
            )
            remainingChains--
        }
    }

    private fun getRandomArtifact(rarity: ArtifactRarity = rarityList.random()!!): Artifact {
        return artifacts.map { it.value.getDeclaredConstructor().newInstance() }.filter { it.rarity == rarity }.random()
    }
}