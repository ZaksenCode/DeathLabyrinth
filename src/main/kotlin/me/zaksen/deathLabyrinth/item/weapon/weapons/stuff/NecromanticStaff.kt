package me.zaksen.deathLabyrinth.item.weapon.weapons.stuff

import me.zaksen.deathLabyrinth.entity.trader.TraderType
import me.zaksen.deathLabyrinth.item.ItemQuality
import me.zaksen.deathLabyrinth.item.settings.ItemSettings
import me.zaksen.deathLabyrinth.item.weapon.WeaponItem
import me.zaksen.deathLabyrinth.item.weapon.WeaponType
import me.zaksen.deathLabyrinth.util.asTranslate
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material

class NecromanticStaff(id: String): WeaponItem(
    WeaponType.MISC_STAFF,
    id,
    ItemSettings(Material.STICK)
        .customModel(108)
        .displayName("item.necromantic_staff.name".asTranslate().color(TextColor.color(128, 128, 128)))
        .abilityCooldown(60000)
        .quality(ItemQuality.RARE)
        .tradePrice(110)
        .addAviableTrader(TraderType.NORMAL)
        .ability("necromantic_cast")
)