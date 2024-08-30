package me.zaksen.deathLabyrinth.game.hud

import me.zaksen.deathLabyrinth.data.PlayerData
import me.zaksen.deathLabyrinth.util.asText
import net.kyori.adventure.key.Key
import org.bukkit.entity.Player
import kotlin.math.log10

class DataHudDrawer(private val data: PlayerData, viewer: Player): HudDrawer(viewer) {

    override fun draw() {
        if(data.playerClass == null) {
            return
        }
        // Считает количество разрядов у числа и отнимает его от количества пробелов
        val removeSpaces = if(data.money == 0) {
            0
        }
        else {
            (log10(data.money.toDouble()) + 1).toInt()
        }
        val builder = StringBuilder()

        for(i in 1..defaultSpace - removeSpaces) {
            builder.append(" ")
        }

        builder.append("${data.money}").append(coinSymbol)
        viewer.sendActionBar(builder.toString().asText().font(Key.key("dl:money")))
    }

    companion object {
        // >                                <
        val defaultSpace = 32
        val coinSymbol = "\uE000"
    }
}