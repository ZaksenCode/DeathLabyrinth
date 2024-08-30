package me.zaksen.deathLabyrinth.game.hud

import me.zaksen.deathLabyrinth.data.PlayerData
import org.bukkit.entity.Player
import java.util.*
import kotlin.concurrent.timer

class HudController {

    private var drawingTask: Timer? = null
    private val hudDrawers: MutableSet<HudDrawer> = mutableSetOf()

    fun initDrawingTask() {
        drawingTask = timer(period = 500) {
            hudDrawers.forEach {
                it.draw()
            }
        }
    }

    fun stopDrawingTask() {
        drawingTask?.cancel()
        drawingTask = null
    }

    fun addPlayerToDraw(player: Player, playerData: PlayerData) {
        hudDrawers.add(DataHudDrawer(playerData, player))
    }

    fun clearDrawers() {
        hudDrawers.clear()
    }
}