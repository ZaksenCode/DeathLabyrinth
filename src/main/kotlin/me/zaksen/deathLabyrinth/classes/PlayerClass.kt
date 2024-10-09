package me.zaksen.deathLabyrinth.classes

import me.zaksen.deathLabyrinth.data.PlayerData
import me.zaksen.deathLabyrinth.item.weapon.WeaponType
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player

interface PlayerClass {

    fun getClassName(): Component

    fun availableWeapons(): Set<WeaponType>

    fun launchSetup(player: Player, playerData: PlayerData)
}