package me.zaksen.deathLabyrinth.item.weapon.weapons.stuff

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
import org.bukkit.event.player.PlayerInteractEvent

class LaserStaff(id: String): WeaponItem(
    WeaponType.ATTACK_STAFF,
    id,
    ItemSettings(Material.STICK)
        .customModel(110)
        .displayName("item.laser_staff.name".asTranslate().color(TextColor.color(128, 128, 128)))
        .abilityCooldown(1000)
        .lore(mutableListOf(
            "item.laser_staff.lore.0".asTranslate().color(TextColor.color(128, 0, 128)),
            "item.laser_staff.lore.1".asTranslate().color(TextColor.color(65,105,225))
        )).quality(ItemQuality.UNCOMMON)
        .tradePrice(60)
        .addAviableTrader(TraderType.NORMAL)
)
{
    override fun onUse(event: PlayerInteractEvent) {
        val item = event.item!!

        val rayCastEntity = event.player.rayTraceEntities(64)

        if(rayCastEntity != null && rayCastEntity.hitEntity != null && checkAndUpdateCooldown(item)) {
            if(rayCastEntity.hitEntity!! !is LivingEntity) {
                return
            }
            EventManager.callPlayerSpellEntityDamageEvent(event.player, rayCastEntity.hitEntity as CraftLivingEntity, 12.0)

            event.player.eyeLocation.particleLine(Particle.WITCH, rayCastEntity.hitPosition.toLocation(event.player.world))
        }
    }
}