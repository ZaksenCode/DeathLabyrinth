package me.zaksen.deathLabyrinth.classes

import me.zaksen.deathLabyrinth.data.PlayerData
import me.zaksen.deathLabyrinth.item.weapon.WeaponType
import org.bukkit.entity.Player

interface PlayerClass {

    fun getClassName(): String

    fun availableWeapons(): Set<WeaponType>

    fun launchSetup(player: Player, playerData: PlayerData)
}