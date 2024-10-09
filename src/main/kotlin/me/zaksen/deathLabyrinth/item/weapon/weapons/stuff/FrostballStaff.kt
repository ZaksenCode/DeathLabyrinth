package me.zaksen.deathLabyrinth.item.weapon.weapons.stuff

import me.zaksen.deathLabyrinth.entity.projectile.FrostBallEntity
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

class FrostballStaff(id: String): WeaponItem(
    WeaponType.ATTACK_STAFF,
    id,
    ItemSettings(Material.STICK)
        .customModel(101)
        .displayName("item.frostball_staff.name".asTranslate().color(TextColor.color(0, 191, 255)))
        .abilityCooldown(800)
        .lore(mutableListOf(
            "item.frostball_staff.lore.0".asTranslate().color(TextColor.color(128, 0, 128)),
            "item.frostball_staff.lore.1".asTranslate().color(TextColor.color(65,105,225))
        ))
        .tradePrice(35)
        .addAviableTrader(TraderType.NORMAL)
)
{
    override fun onUse(event: PlayerInteractEvent) {
        val item = event.item!!

        if(checkAndUpdateCooldown(item)) {
            val shotVelocity = event.player.location.direction.multiply(2).normalize()

            val projectile = FrostBallEntity(event.player.location.add(shotVelocity).add(0.0, 1.6, 0.0))
            projectile.deltaMovement = Vec3(shotVelocity.x, shotVelocity.y, shotVelocity.z)
            projectile.setOwner((event.player as CraftPlayer).handle)
            EventManager.callPlayerSummonSpellEvent(event.player, projectile)
        }
    }
}