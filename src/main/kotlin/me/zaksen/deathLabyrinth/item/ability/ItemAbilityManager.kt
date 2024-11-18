package me.zaksen.deathLabyrinth.item.ability

import me.zaksen.deathLabyrinth.item.ability.consume.*
import me.zaksen.deathLabyrinth.item.ability.stuff.*
import me.zaksen.deathLabyrinth.item.ability.weapon.*
import me.zaksen.deathLabyrinth.keys.PluginKeys
import me.zaksen.deathLabyrinth.util.stringList
import org.bukkit.event.Event
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

object ItemAbilityManager {

    private val abilityMap: MutableMap<String, ItemAbility> = mutableMapOf()

    init {
        // Weapon
        abilityMap["slash_hit"] = SlashHit()
        // Stuff
        abilityMap["fireball_cast"] = FireballCast()
        abilityMap["big_fireball_cast"] = BigFireballCast()
        abilityMap["frostball_cast"] = FrostballCast()
        abilityMap["big_frostball_cast"] = BigFrostballCast()
        abilityMap["witherball_cast"] = WitherballCast()
        abilityMap["big_fireball_cast"] = BigWitherballCast()
        abilityMap["laser_cast"] = LaserCast()
        abilityMap["electric_cast"] = ElectricCast()
        abilityMap["healing_cast"] = HealingCast()
        abilityMap["big_healing_cast"] = BigHealingCast()
        abilityMap["necromantic_cast"] = NecromanticCast()
        // Consume
        abilityMap["heal_effect"] = HealEffect()
        abilityMap["small_heal_effect"] = SmallHealEffect()

    }

    fun useStackAbilities(stack: ItemStack, event: Event) {
        if(!stack.hasItemMeta()) return
        if(!stack.itemMeta.persistentDataContainer.has(PluginKeys.customItemAbilitiesKey)) return

        val meta = stack.itemMeta
        val abilityIds = meta.persistentDataContainer.get(PluginKeys.customItemAbilitiesKey, PersistentDataType.STRING)?.stringList() ?: return

        abilityIds.forEach {
            val ability = abilityMap[it] ?: return@forEach
            ability.invoke(event)
        }
    }
}