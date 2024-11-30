package me.zaksen.deathLabyrinth.item.accessory

import me.zaksen.deathLabyrinth.artifacts.ArtifactsController
import me.zaksen.deathLabyrinth.event.custom.game.*
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
                    event.output.stack = ItemStack.of(Material.AIR)

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
                    if(event.output.isStackable) {
                        event.output.stack.add(2)
                    }
                }
            }
        })
        addAbilities("speedsters_amulet", object : AccessoryAbility {
            override fun invoke(event: Event, itemStack: ItemStack, player: Player) {
                if(event !is PlayerStartAbilityCooldownEvent) return
                if(event.player.uniqueId != player.uniqueId) return
                event.startTime += (event.stack.itemMeta.persistentDataContainer.get(PluginKeys.customItemCooldownTimeKey, PersistentDataType.INTEGER)!! * 0.15).toLong()
            }
        })
        addAbilities("scoundrels_amulet", object : AccessoryAbility {
            override fun invoke(event: Event, itemStack: ItemStack, player: Player) {
                if(event !is PlayerDamageEntityEvent) return
                if(event.player.uniqueId != player.uniqueId) return
                val target = event.damaged.getTargetEntity(64)

                if(event.player != target) {
                    event.damage += event.damage * 0.3
                }
            }
        }, object: AccessoryAbility {
            override fun invoke(event: Event, itemStack: ItemStack, player: Player) {
                if(event !is PlayerDamagedByEntityEvent) return
                if(event.damaged.uniqueId != player.uniqueId) return
                event.damage += event.damage * 0.1
            }
        })
        addAbilities("brave_mans_amulet", object : AccessoryAbility {
            override fun invoke(event: Event, itemStack: ItemStack, player: Player) {
                if(event !is PlayerDamageEntityEvent) return
                if(event.player.uniqueId != player.uniqueId) return
                val target = event.damaged.getTargetEntity(64)

                if(event.player == target) {
                    event.damage += event.damage * 0.3
                }
            }
        }, object: AccessoryAbility {
            override fun invoke(event: Event, itemStack: ItemStack, player: Player) {
                if(event !is PlayerDamagedByEntityEvent) return
                if(event.damaged.uniqueId != player.uniqueId) return
                event.damage -= event.damage * 0.1
            }
        })
        addAbilities("amulet_of_poverty", object : AccessoryAbility {
            override fun invoke(event: Event, itemStack: ItemStack, player: Player) {
                if(event !is PlayerRoomCompleteEvent) return
                if(event.player.uniqueId != player.uniqueId) return

                event.reward -= (event.reward * 0.3).toInt()
            }
        }, object: AccessoryAbility {
            override fun invoke(event: Event, itemStack: ItemStack, player: Player) {
                if(event !is PlayerDamagedByEntityEvent) return
                if(event.damaged.uniqueId != player.uniqueId) return

                event.damage -= event.damage * 0.6
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