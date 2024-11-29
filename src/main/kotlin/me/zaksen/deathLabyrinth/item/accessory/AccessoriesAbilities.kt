package me.zaksen.deathLabyrinth.item.accessory

import me.zaksen.deathLabyrinth.artifacts.ArtifactsController
import me.zaksen.deathLabyrinth.event.custom.game.PlayerBreakPotEvent
import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.item.accessory.ability.AccessoryAbility
import me.zaksen.deathLabyrinth.item.accessory.ability.AccessoryAbilityContainer
import me.zaksen.deathLabyrinth.keys.PluginKeys
import me.zaksen.deathLabyrinth.util.getAngle
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

object AccessoriesAbilities {
    private val abilitiesMap: MutableMap<String, AccessoryAbilityContainer> = mutableMapOf()

    init {
        addAbilities("amulet_of_greed", object : AccessoryAbility {
            override fun invoke(event: Event, itemStack: ItemStack, player: Player) {
                if(event !is PlayerBreakPotEvent) return
                if(event.player.uniqueId != player.uniqueId) return

                if(GameController.checkChance(20)) {
                    val potLocation = event.decoratedPot.location
                    event.output = ItemStack.of(Material.AIR)

                    val artifactLocation = Location(
                        potLocation.world,
                        potLocation.x + 0.5,
                        potLocation.y + 1.5,
                        potLocation.z + 0.5
                    )

                    artifactLocation.yaw = artifactLocation.getAngle(event.player.location)

                    ArtifactsController.summonArtifactCard(
                        artifactLocation,
                        ArtifactsController.getRandomArtifact()
                    )
                } else {
                    event.output.add(2)
                }
            }
        })
    }

    private fun addAbilities(id: String, vararg abilities: AccessoryAbility) {
        val newContainer = AccessoryAbilityContainer()

        abilities.forEach {
            newContainer.add(it)
        }

        abilitiesMap[id] = newContainer
    }

    fun invokeAccessoryAbilities(event: Event, stack: ItemStack, player: Player) {
        val itemId = stack.itemMeta.persistentDataContainer.get(PluginKeys.customItemKey, PersistentDataType.STRING) ?: return
        val abilityContainer = abilitiesMap[itemId] ?: return

        abilityContainer.invoke(event, stack, player)
    }
}