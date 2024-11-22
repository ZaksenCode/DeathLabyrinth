package me.zaksen.deathLabyrinth.item.weapon.weapons.stuff

import me.zaksen.deathLabyrinth.entity.trader.TraderType
import me.zaksen.deathLabyrinth.item.ItemQuality
import me.zaksen.deathLabyrinth.item.settings.ItemSettings
import me.zaksen.deathLabyrinth.item.weapon.WeaponItem
import me.zaksen.deathLabyrinth.item.weapon.WeaponType
import me.zaksen.deathLabyrinth.util.asTranslate
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material

class FireFlowStaff(id: String): WeaponItem(
    WeaponType.ATTACK_STAFF,
    id,
    ItemSettings(Material.STICK)
        .customModel(112)
        .displayName("item.fire_flow_staff.name".asTranslate().color(TextColor.color(128, 128, 128)))
        .abilityCooldown(100)
        .quality(ItemQuality.RARE)
        .tradePrice(80)
        .addAviableTrader(TraderType.NORMAL)
        .ability("fire_flow_cast")
)