package me.zaksen.deathLabyrinth.item.weapon.weapons.stuff

import me.zaksen.deathLabyrinth.entity.trader.TraderType
import me.zaksen.deathLabyrinth.item.ItemQuality
import me.zaksen.deathLabyrinth.item.settings.ItemSettings
import me.zaksen.deathLabyrinth.item.weapon.WeaponItem
import me.zaksen.deathLabyrinth.item.weapon.WeaponType
import me.zaksen.deathLabyrinth.util.asTranslate
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material

class BigFrostballStaff(id: String): WeaponItem(
    WeaponType.ATTACK_STAFF,
    id,
    ItemSettings(Material.STICK)
        .customModel(103)
        .displayName("item.big_frostball_staff.name".asTranslate().color(TextColor.color(0, 191, 255)))
        .abilityCooldown(3200)
        .lore(mutableListOf(
            "item.big_frostball_staff.lore.0".asTranslate().color(TextColor.color(128, 0, 128)),
            "item.big_frostball_staff.lore.1".asTranslate().color(TextColor.color(65,105,225))
        ))
        .quality(ItemQuality.UNCOMMON)
        .tradePrice(55)
        .addAviableTrader(TraderType.NORMAL)
        .ability("big_frostball_cast")
)