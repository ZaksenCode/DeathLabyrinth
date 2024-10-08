package me.zaksen.deathLabyrinth.item.weapon.weapons.stuff

import me.zaksen.deathLabyrinth.entity.trader.TraderType
import me.zaksen.deathLabyrinth.item.ItemQuality
import me.zaksen.deathLabyrinth.item.settings.ItemSettings
import me.zaksen.deathLabyrinth.item.weapon.WeaponItem
import me.zaksen.deathLabyrinth.item.weapon.WeaponType
import me.zaksen.deathLabyrinth.util.asTranslate
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityRegainHealthEvent
import org.bukkit.event.player.PlayerInteractEvent

class BigHealingStaff(id: String): WeaponItem(
    WeaponType.MISC_STAFF,
    id,
    ItemSettings(Material.STICK)
        .customModel(105)
        .displayName("item.big_healing_staff.name".asTranslate().color(TextColor.color(50,205,50)))
        .lore(mutableListOf(
            "item.big_healing_staff.lore.0".asTranslate().color(TextColor.color(128, 0, 128)),
            "item.big_healing_staff.lore.1".asTranslate().color(TextColor.color(65,105,225))
        ))
        .abilityCooldown(40000)
        .quality(ItemQuality.EPIC)
        .tradePrice(180)
        .addAviableTrader(TraderType.NORMAL)
)
{
    override fun onUse(event: PlayerInteractEvent) {
        val item = event.item!!

        if(checkAndUpdateCooldown(item)) {
            val players = event.player.world.getNearbyEntitiesByType(
                Player::class.java,
                event.player.location,
                4.0
            )

            players.forEach {
                val maxHealth = it.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.baseValue
                val toHeal = maxHealth * 0.3
                it.heal(toHeal, EntityRegainHealthEvent.RegainReason.MAGIC)
                it.world.spawnParticle(
                    Particle.TOTEM_OF_UNDYING,
                    event.player.location,
                    50,
                    0.5,
                    0.5,
                    0.5
                )
            }
        }
    }
}