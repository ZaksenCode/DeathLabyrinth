package me.zaksen.deathLabyrinth.item.weapon.weapons.stuff

import me.zaksen.deathLabyrinth.item.settings.ItemSettings
import me.zaksen.deathLabyrinth.item.weapon.WeaponItem
import me.zaksen.deathLabyrinth.item.weapon.WeaponType
import me.zaksen.deathLabyrinth.util.ChatUtil
import me.zaksen.deathLabyrinth.util.ChatUtil.actionBar
import org.bukkit.Material
import org.bukkit.event.player.PlayerInteractEvent

class FrostBallStuff(id: String): WeaponItem(
    WeaponType.ATTACK_STAFF,
    id,
    ItemSettings(Material.STICK)
        .customModel(101)
        .displayName(ChatUtil.format("<aqua>Леденящий посох</aqua>"))
        .abilityCooldown(1000)
)
{
    override fun onUse(event: PlayerInteractEvent) {
        val item = event.item!!

        if(checkAndUpdateCooldown(item)) {

        } else {
            event.player.actionBar("<red>Этот предмет ещё на перезарядке!</red>")
        }
    }
}