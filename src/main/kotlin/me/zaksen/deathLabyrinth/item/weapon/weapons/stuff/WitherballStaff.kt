package me.zaksen.deathLabyrinth.item.weapon.weapons.stuff

import me.zaksen.deathLabyrinth.entity.projectile.WitherBallEntity
import me.zaksen.deathLabyrinth.entity.trader.TraderType
import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.item.settings.ItemSettings
import me.zaksen.deathLabyrinth.item.weapon.WeaponItem
import me.zaksen.deathLabyrinth.item.weapon.WeaponType
import me.zaksen.deathLabyrinth.util.asTranslate
import net.kyori.adventure.text.format.TextColor
import net.minecraft.world.phys.Vec3
import org.bukkit.Material
import org.bukkit.craftbukkit.entity.CraftPlayer
import org.bukkit.event.player.PlayerInteractEvent

class WitherballStaff(id: String): WeaponItem(
    WeaponType.ATTACK_STAFF,
    id,
    ItemSettings(Material.STICK)
        .customModel(106)
        .displayName("item.witherball_staff.name".asTranslate().color(TextColor.color(128, 128, 128)))
        .abilityCooldown(1333)
        .tradePrice(50)
        .addAviableTrader(TraderType.NORMAL)
        .ability("witherball_cast")
)