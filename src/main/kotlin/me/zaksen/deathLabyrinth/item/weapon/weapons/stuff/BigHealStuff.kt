package me.zaksen.deathLabyrinth.item.weapon.weapons.stuff

import me.zaksen.deathLabyrinth.item.ItemQuality
import me.zaksen.deathLabyrinth.item.settings.ItemSettings
import me.zaksen.deathLabyrinth.item.weapon.WeaponItem
import me.zaksen.deathLabyrinth.item.weapon.WeaponType
import me.zaksen.deathLabyrinth.util.asText
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityRegainHealthEvent
import org.bukkit.event.player.PlayerInteractEvent

class BigHealStuff(id: String): WeaponItem(
    WeaponType.MISC_STUFF,
    id,
    ItemSettings(Material.STICK)
        .customModel(105)
        .displayName("<green>Святой посох исцеления</green>".asText())
        .loreLine("<dark_purple>Лечит всех игроков в радиусе 4 блоков</dark_purple>".asText())
        .abilityCooldown(40000)
        .quality(ItemQuality.EPIC)
        .tradePrice(180)
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
                it.heal(12.0, EntityRegainHealthEvent.RegainReason.MAGIC)
            }
        }
    }
}