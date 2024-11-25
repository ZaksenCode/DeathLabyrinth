package me.zaksen.deathLabyrinth.classes

import me.zaksen.deathLabyrinth.data.PlayerData
import me.zaksen.deathLabyrinth.item.ItemsController
import me.zaksen.deathLabyrinth.item.weapon.WeaponType
import me.zaksen.deathLabyrinth.util.asTranslate
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.entity.Player
import kotlin.random.Random

class MageClass : PlayerClass {

    override fun getClassName(): Component {
        return "class.mage.name".asTranslate().color(TextColor.color(30,144,255))
    }

    override fun availableWeapons(): Set<WeaponType> {
        return setOf(WeaponType.ATTACK_STAFF, WeaponType.MISC_STAFF)
    }

    override fun launchSetup(player: Player, playerData: PlayerData) {
        when(Random.Default.nextInt(1, 4)) {
            1 -> {
                player.inventory.addItem(ItemsController.get("frost_ball_stuff")!!.asItemStack())
            }
            2 -> {
                player.inventory.addItem(ItemsController.get("fire_ball_stuff")!!.asItemStack())
            }
            else -> {
                player.inventory.addItem(ItemsController.get("wither_ball_stuff")!!.asItemStack())
            }
        }
        player.inventory.addItem(ItemsController.get("heal_potion")!!.asItemStack())
    }

}