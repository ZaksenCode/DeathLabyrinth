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
        .ability("electric_cast")
)