package me.zaksen.deathLabyrinth.item.weapon

import me.zaksen.deathLabyrinth.item.CustomItem
import me.zaksen.deathLabyrinth.item.ItemType
import me.zaksen.deathLabyrinth.item.settings.ItemSettings

// TODO - Make items stats dynamic
open class WeaponItem(private val weaponType: WeaponType, id: String, settings: ItemSettings): CustomItem(id, ItemType.WEAPON, settings) {

    fun getWeaponType(): WeaponType {
        return weaponType
    }
}