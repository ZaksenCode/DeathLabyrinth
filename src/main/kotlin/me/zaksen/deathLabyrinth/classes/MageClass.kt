package me.zaksen.deathLabyrinth.classes

import me.zaksen.deathLabyrinth.item.weapon.WeaponType
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class MageClass() : PlayerClass {

    override fun getClassName(): String {
        return "<blue>Маг</blue>"
    }

    override fun availableWeapons(): Set<WeaponType> {
        return setOf(WeaponType.STAFF)
    }

    override fun launchSetup(player: Player) {
        player.inventory.clear()

        player.inventory.setItem(0, ItemStack(Material.STICK))
    }

}