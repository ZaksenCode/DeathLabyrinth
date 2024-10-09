package me.zaksen.deathLabyrinth.classes

import me.zaksen.deathLabyrinth.artifacts.custom.GreenHeart
import me.zaksen.deathLabyrinth.data.PlayerData
import me.zaksen.deathLabyrinth.item.ItemsController
import me.zaksen.deathLabyrinth.item.weapon.WeaponType
import me.zaksen.deathLabyrinth.util.asTranslate
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.entity.Player
import org.bukkit.inventory.EquipmentSlot

class WarriorClass : PlayerClass {

    override fun getClassName(): Component {
        return "class.warrior.name".asTranslate().color(TextColor.color(220,20,60))
    }

    override fun availableWeapons(): Set<WeaponType> {
        return setOf(WeaponType.SWORD, WeaponType.DAGGER, WeaponType.HAMMER, WeaponType.SPEAR)
    }

    override fun launchSetup(player: Player, playerData: PlayerData) {
        player.inventory.addItem(ItemsController.get("wooden_sword")!!.asItemStack())
        player.inventory.setItem(EquipmentSlot.OFF_HAND, ItemsController.get("shield")!!.asItemStack())
        player.inventory.addItem(ItemsController.get("heal_potion")!!.asItemStack())

        playerData.addArtifact(GreenHeart())
    }

}