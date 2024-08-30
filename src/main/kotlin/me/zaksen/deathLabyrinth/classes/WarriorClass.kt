package me.zaksen.deathLabyrinth.classes

import me.zaksen.deathLabyrinth.item.ItemsController
import me.zaksen.deathLabyrinth.item.weapon.WeaponType
import org.bukkit.entity.Player

class WarriorClass() : PlayerClass {

    override fun getClassName(): String {
        return "<red>Воин</red>"
    }

    override fun availableWeapons(): Set<WeaponType> {
        return setOf(WeaponType.SWORD, WeaponType.DAGGER, WeaponType.HAMMER, WeaponType.SPEAR)
    }

    override fun launchSetup(player: Player) {
        player.inventory.addItem(ItemsController.get("wooden_sword")!!.asItemStack())
        player.inventory.addItem(ItemsController.get("heal_potion")!!.asItemStack())
    }

}