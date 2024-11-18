package me.zaksen.deathLabyrinth.item.weapon.weapons.stuff

import me.zaksen.deathLabyrinth.entity.projectile.BigWitherBallEntity
import me.zaksen.deathLabyrinth.entity.trader.TraderType
import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.item.ItemQuality
import me.zaksen.deathLabyrinth.item.settings.ItemSettings
import me.zaksen.deathLabyrinth.item.weapon.WeaponItem
import me.zaksen.deathLabyrinth.item.weapon.WeaponType
import me.zaksen.deathLabyrinth.util.asTranslate
import net.kyori.adventure.text.format.TextColor
import net.minecraft.world.phys.Vec3
import org.bukkit.Material
import org.bukkit.craftbukkit.entity.CraftPlayer
import org.bukkit.event.player.PlayerInteractEvent

class BigWitherballStaff(id: String): WeaponItem(
    WeaponType.ATTACK_STAFF,
    id,
    ItemSettings(Material.STICK)
        .customModel(107)
        .displayName("item.big_witherball_staff.name".asTranslate().color(TextColor.color(128, 128, 128)))
        .abilityCooldown(4000)
        .lore(mutableListOf(
            "item.big_witherball_staff.lore.0".asTranslate().color(TextColor.color(128, 0, 128)),
            "item.big_witherball_staff.lore.1".asTranslate().color(TextColor.color(65,105,225))
        )).quality(ItemQuality.UNCOMMON)
        .tradePrice(80)
        .addAviableTrader(TraderType.NORMAL)
        .ability("big_witherball_cast")
)