package me.zaksen.deathLabyrinth.classes

import me.zaksen.deathLabyrinth.item.weapon.WeaponType

class WarriorClass() : PlayerClass {

    override fun getClassName(): String {
        return "<red>Воин</red>"
    }

    override fun availableWeapons(): Set<WeaponType> {
        return setOf(WeaponType.SWORD, WeaponType.DAGGER, WeaponType.HAMMER)
    }

}