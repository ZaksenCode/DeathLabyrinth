package me.zaksen.deathLabyrinth.classes

import me.zaksen.deathLabyrinth.item.weapon.WeaponType
import org.bukkit.entity.Player

class WarriorClass() : PlayerClass {

    override fun getClassName(): String {
        return "<red>Воин</red>"
    }

    override fun availableWeapons(): Set<WeaponType> {
        return setOf(WeaponType.SWORD, WeaponType.DAGGER, WeaponType.HAMMER)
    }

    override fun launchSetup(player: Player) {

    }

}