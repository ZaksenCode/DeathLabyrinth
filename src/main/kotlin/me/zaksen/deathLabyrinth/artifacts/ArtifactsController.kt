package me.zaksen.deathLabyrinth.artifacts

import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import me.zaksen.deathLabyrinth.artifacts.api.ArtifactRarity
import me.zaksen.deathLabyrinth.artifacts.card.CardHolder
import me.zaksen.deathLabyrinth.artifacts.chain.ArtifactsChain
import me.zaksen.deathLabyrinth.artifacts.custom.*
import me.zaksen.deathLabyrinth.artifacts.custom.godly.Greediness
import me.zaksen.deathLabyrinth.entity.interaction.ArtifactsCardHitbox
import me.zaksen.deathLabyrinth.entity.item_display.ArtifactsCard
import me.zaksen.deathLabyrinth.entity.item_display.ArtifactsCardIcon
import me.zaksen.deathLabyrinth.entity.text_display.ArtifactsCardName
import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.util.WeightedRandomList
import net.minecraft.world.phys.Vec3
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.craftbukkit.entity.CraftEntity
import org.bukkit.entity.Player
import java.util.Timer
import java.util.UUID
import kotlin.concurrent.timer

object ArtifactsController {

    private val rarityList = WeightedRandomList<ArtifactRarity>()
    val summonedCards: MutableMap<ArtifactsCardHitbox, CardHolder> = mutableMapOf()
    val chains: MutableList<ArtifactsChain> = mutableListOf()
    val artifacts: MutableMap<String, Class<out Artifact>> = mutableMapOf()

    private var lastHighlightedCards: MutableMap<UUID, CardHolder> = mutableMapOf()
    var highlightTimer: Timer = timer(period = 200) {
        if(summonedCards.isEmpty()) {
            return@timer
        }

        for(entry in GameController.players.filter { it.value.isAlive }) {
            val player = entry.key

            val lastEntity = lastHighlightedCards[player.uniqueId]
            val rayTrace = player.rayTraceEntities(6)

            if(rayTrace == null || rayTrace.hitEntity == null) {
                if(lastEntity != null) {
                    lastEntity.deHighlight()
                    lastHighlightedCards.remove(player.uniqueId)
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
                            lastHighlightedCards[player.uniqueId] = holder
                        }
                    }
                } else {
                    if((hitEntity as CraftEntity).handle is ArtifactsCardHitbox) {
                        val cardHitbox = hitEntity.handle as ArtifactsCardHitbox
                        val holder = summonedCards[cardHitbox] ?: continue
                        holder.highlight()
                        lastHighlightedCards[player.uniqueId] = holder
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
        artifacts["mystic_potion"] = MysticPotion::class.java
        artifacts["red_jar"] = RedJar::class.java
        artifacts["snowflake"] = Snowflake::class.java
        artifacts["holy_mantle"] = HolyMantle::class.java

        // RARE
        artifacts["rusty_dagger"] = RustyDagger::class.java
        artifacts["mirror_of_revenge"] = MirrorOfRevenge::class.java
        artifacts["green_heart"] = GreenHeart::class.java
        artifacts["blood_lust"] = BloodLust::class.java
        artifacts["wooden_shield"] = WoodenShield::class.java
        artifacts["explosive_mix"] = ExplosiveMix::class.java
        artifacts["token"] = Token::class.java
        artifacts["fantom_cape"] = FantomCape::class.java
        artifacts["magnifying_glass"] = MagnifyingGlass::class.java

        // EPIC
        artifacts["mossy_skull"] = MossySkull::class.java
        artifacts["gasoline"] = Gasoline::class.java
        artifacts["little_bomb"] = LittleBomb::class.java
        artifacts["totem_of_undying"] = TotemOfUndying::class.java
        artifacts["vampire_fangs"] = VampireFangs::class.java

        // GODLY
        artifacts["greediness"] = Greediness::class.java
    }

    fun summonArtifactCard(location: Location, artifact: Artifact, chain: ArtifactsChain? = null): CardHolder {
        val card = ArtifactsCard(location, artifact)
        val icon = ArtifactsCardIcon(location, artifact)
        val hitbox = ArtifactsCardHitbox(location.subtract(0.0, 0.9, 0.0), chain)
        val name = ArtifactsCardName(location.add(0.0, 1.9, 0.0), artifact)

        val holder = CardHolder(artifact, card, icon, name, hitbox)
        holder.summon(location.world)
        summonedCards[hitbox] = holder

        return holder
    }

    fun despawnArtifact(cardHolder: CardHolder) {
        cardHolder.despawn()
        summonedCards.remove(cardHolder.artifactsCardHitbox)
    }

    fun despawnArtifacts() {
        chains.forEach {
            it.clearChainCards()
        }
        summonedCards.forEach {
            it.value.despawn()
        }
        summonedCards.clear()
        chains.clear()
    }

    fun processArtifactPickup(player: Player, artifact: Artifact) {
        val playerData = GameController.players[player] ?: return
        playerData.addArtifact(artifact, player.uniqueId)
    }

    fun startArtifactsChain(location: Location, count: Int = 1, isGoodly: Boolean = false) {
        val newChain = ArtifactsChain(location, count, isGoodly)
        chains.add(newChain)
        newChain.process()
    }

    fun removeArtifactsChain(chain: ArtifactsChain) {
        chain.clearChainCards()
        chains.remove(chain)
    }

    fun getRandomArtifact(rarity: ArtifactRarity = rarityList.random()!!): Artifact {
        return artifacts.map { it.value.getDeclaredConstructor().newInstance() }.filter { it.rarity == rarity }.random()
    }

    fun getWorldArtifacts(location: Location, distance: Int): List<CardHolder> {
        val result = mutableListOf<CardHolder>()

        summonedCards.forEach {
            if(it.key.position().distanceTo(Vec3(location.x, location.y, location.z)) <= distance) {
                result.add(it.value)
            }
        }

        return result
    }
}