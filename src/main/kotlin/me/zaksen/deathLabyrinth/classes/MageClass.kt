package me.zaksen.deathLabyrinth.classes

import me.zaksen.deathLabyrinth.item.ItemsController
import me.zaksen.deathLabyrinth.item.weapon.WeaponType
import org.bukkit.entity.Player

class MageClass : PlayerClass {

    override fun getClassName(): String {
        return "<blue>Маг</blue>"
    }

    override fun availableWeapons(): Set<WeaponType> {
        return setOf(WeaponType.ATTACK_STAFF, WeaponType.MISC_STAFF)
    }

    override fun launchSetup(player: Player) {
        player.inventory.addItem(ItemsController.get("frost_ball_stuff")!!.asItemStack())
    }

}