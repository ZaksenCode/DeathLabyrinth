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
import org.bukkit.event.entity.EntityRegainHealthEvent
import org.bukkit.event.player.PlayerInteractEvent

class HealingStaff(id: String): WeaponItem(
    WeaponType.MISC_STAFF,
    id,
    ItemSettings(Material.STICK)
        .customModel(100)
        .displayName("item.healing_staff.name".asTranslate().color(TextColor.color(50,205,50)))
        .abilityCooldown(30000)
        .lore(mutableListOf(
            "item.healing_staff.lore.0".asTranslate().color(TextColor.color(128, 0, 128)),
            "item.healing_staff.lore.1".asTranslate().color(TextColor.color(65,105,225))
        ))
        .quality(ItemQuality.RARE)
        .tradePrice(100)
        .addAviableTrader(TraderType.NORMAL)
)
{
    override fun onUse(event: PlayerInteractEvent) {
        val item = event.item!!

        if(checkAndUpdateCooldown(item)) {
            val maxHealth = event.player.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.baseValue
            val toHeal = maxHealth * 0.15

            event.player.heal(toHeal, EntityRegainHealthEvent.RegainReason.MAGIC)

            event.player.world.spawnParticle(
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