package me.zaksen.deathLabyrinth.item.ability

import FireFlowCast
import me.zaksen.deathLabyrinth.event.item.ItemUseEvent
import me.zaksen.deathLabyrinth.item.ability.consume.*
import me.zaksen.deathLabyrinth.item.ability.stuff.*
import me.zaksen.deathLabyrinth.item.ability.weapon.*
import me.zaksen.deathLabyrinth.keys.PluginKeys
import me.zaksen.deathLabyrinth.util.stringList
import org.bukkit.event.Event
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

// TODO - Add new ability tiers
object ItemAbilityManager {

    val abilityMap: MutableMap<String, ItemAbility> = mutableMapOf()

    init {
        // Weapon
        abilityMap["area_hit"] = AreaHit()
        abilityMap["big_area_hit"] = BigAreaHit()
        abilityMap["huge_area_hit"] = HugeAreaHit()
        abilityMap["giant_area_hit"] = GiantAreaHit()

        abilityMap["fire_blade"] = FireBlade()
        abilityMap["narwhal_punch"] = NarwhalPunch()
        abilityMap["explosion_punch"] = ExplosionPunch()
        // Weapon - Use
        abilityMap["bubble_laser"] = BubbleLaser()
        abilityMap["wind_gust"] = WindGust()
        // Stuff
        abilityMap["fireball_cast"] = FireballCast()
        abilityMap["big_fireball_cast"] = BigFireballCast()

        abilityMap["frostball_cast"] = FrostballCast()
        abilityMap["big_frostball_cast"] = BigFrostballCast()

        abilityMap["witherball_cast"] = WitherballCast()
        abilityMap["big_witherball_cast"] = BigWitherballCast()

        abilityMap["waterball_cast"] = WaterBallCast()

        abilityMap["laser_cast"] = LaserCast()

        abilityMap["frost_laser_cast"] = FrostLaserCast()

        abilityMap["fire_laser_cast"] = FireLaserCast()

        abilityMap["wither_laser_cast"] = WitherLaserCast()

        abilityMap["healing_laser_cast"] = HealingLaserCast()

        abilityMap["electric_cast"] = ElectricCast()
        abilityMap["electric_cast_tier_two"] = ElectricCastTierTwo()
        abilityMap["electric_cast_tier_three"] = ElectricCastTierThree()

        abilityMap["healing_cast"] = HealingCast()
        abilityMap["big_healing_cast"] = BigHealingCast()
        abilityMap["necromantic_cast"] = NecromanticCast()

        abilityMap["fire_flow_cast"] = FireFlowCast()

        abilityMap["explosion_flow_cast"] = ExplosionFlowCast()

        abilityMap["explosion_cast"] = ExplosionCast()
        abilityMap["explosion_cast_tier_two"] = ExplosionCastTierTwo()
        abilityMap["explosion_cast_tier_three"] = ExplosionCastTierThree()

        abilityMap["frost_explosion_cast"] = FrostExplosionCast()
        abilityMap["fire_explosion_cast"] = FireExplosionCast()
        abilityMap["wither_explosion_cast"] = WitherExplosionCast()

        abilityMap["explosion_chain_cast"] = ExplosionChainCast()

        abilityMap["bomb_cast"] = BombCast()
        abilityMap["bomb_cast_tier_two"] = BombCastTierTwo()
        abilityMap["bomb_cast_tier_three"] = BombCastTierThree()

        abilityMap["frost_bomb_cast"] = FrostBombCast()
        abilityMap["fire_bomb_cast"] = FireBombCast()
        abilityMap["wither_bomb_cast"] = WitherBombCast()

        // Stuff - other
        abilityMap["wind_charge_cast"] = WindChargeCast()

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

        if(event is ItemUseEvent) {
            if(event.item != null && event.stack != null)
            event.item.checkAndUpdateCooldown(event.stack)
        }
    }
}