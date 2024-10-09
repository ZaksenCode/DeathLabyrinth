package me.zaksen.deathLabyrinth.item.weapon.weapons.stuff

import me.zaksen.deathLabyrinth.entity.friendly.FriendlyEntity
import me.zaksen.deathLabyrinth.entity.trader.TraderType
import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.item.ItemQuality
import me.zaksen.deathLabyrinth.item.settings.ItemSettings
import me.zaksen.deathLabyrinth.item.weapon.WeaponItem
import me.zaksen.deathLabyrinth.item.weapon.WeaponType
import me.zaksen.deathLabyrinth.util.asTranslate
import me.zaksen.deathLabyrinth.util.particleLine
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.craftbukkit.entity.CraftLivingEntity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractEvent

class ElectricStaff(id: String): WeaponItem(
    WeaponType.ATTACK_STAFF,
    id,
    ItemSettings(Material.STICK)
        .customModel(109)
        .displayName("item.electric_staff.name".asTranslate().color(TextColor.color(128, 128, 128)))
        .abilityCooldown(500)
        .lore(mutableListOf(
            "item.electric_staff.lore.0".asTranslate().color(TextColor.color(128, 0, 128)),
            "item.electric_staff.lore.1".asTranslate().color(TextColor.color(65,105,225))
        )).quality(ItemQuality.UNCOMMON)
        .tradePrice(60)
        .addAviableTrader(TraderType.NORMAL)
)
{
    override fun onUse(event: PlayerInteractEvent) {
        val item = event.item!!

        val rayCast = event.player.rayTraceEntities(16)

        if(rayCast != null && rayCast.hitEntity != null && checkAndUpdateCooldown(item)) {
            val entity = rayCast.hitEntity!!

            if(entity !is LivingEntity || entity is Player || entity is FriendlyEntity) {
                return
            }

            val toDamage = getAffectedEntities(entity)

            if(toDamage.isEmpty()) {
                return
            }

            event.player.location.add(0.0, 1.6, 0.0).particleLine(Particle.ENCHANTED_HIT, entity.location.add(0.0, 1.0, 0.0))

            toDamage.forEach {
                EventManager.callPlayerSpellEntityDamageEvent(event.player, it as CraftLivingEntity, 4.0)
            }

            drawParticles(toDamage)
        }
    }

    private fun getAffectedEntities(firstEntity: LivingEntity): MutableList<LivingEntity> {
        val result = mutableListOf<LivingEntity>()
        val livingEntities = mutableListOf<LivingEntity>()
        firstEntity.getNearbyEntities(6.0, 6.0, 6.0).map {
            if(it !is LivingEntity || it is Player || it is FriendlyEntity) {
                return@map
            }
            livingEntities.add(it)
        }

        if(livingEntities.isNotEmpty()) {
            for(i in 1..2) {
                result.add(livingEntities.random())
            }
        }

        result.add(firstEntity)

        return result
    }

    private fun drawParticles(between: MutableList<LivingEntity>) {
        var firstLocation = between.removeFirst().location.add(0.0, 1.0, 0.0)
        if(between.isEmpty()) {
            return
        }
        var secondLocation = between.removeFirst().location.add(0.0, 1.0, 0.0)
        firstLocation.particleLine(Particle.ENCHANTED_HIT, secondLocation)

        while(between.isNotEmpty()) {
            firstLocation = secondLocation
            secondLocation = between.removeFirst().location.add(0.0, 1.0, 0.0)
            firstLocation.particleLine(Particle.ENCHANTED_HIT, secondLocation)
        }
    }
}