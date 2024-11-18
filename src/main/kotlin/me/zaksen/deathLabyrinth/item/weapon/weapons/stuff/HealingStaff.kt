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
        .quality(ItemQuality.RARE)
        .tradePrice(100)
        .addAviableTrader(TraderType.NORMAL)
        .ability("healing_cast")
)