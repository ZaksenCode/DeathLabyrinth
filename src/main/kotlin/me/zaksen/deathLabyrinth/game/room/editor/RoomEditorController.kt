package me.zaksen.deathLabyrinth.game.room.editor

import me.zaksen.deathLabyrinth.config.ConfigContainer
import me.zaksen.deathLabyrinth.util.drawSquare
import org.bukkit.Location
import org.bukkit.World

object RoomEditorController {

    private lateinit var configs: ConfigContainer

    fun setup(container: ConfigContainer) {
        this.configs = container

        if(container.mainConfig().debug) {
            println("<DL:RE> - Room editor was setup!")
        }
    }

    fun startNewRoom(
        world: World,
        posX: Int,
        posY: Int,
        posZ: Int,
        sizeX: Int,
        sizeY: Int,
        sizeZ: Int,
        replace: Boolean = false
    ) {
        if(configs.mainConfig().debug) {

        }

        drawSquare(
            world,
            posX.toDouble(),
            posY.toDouble(),
            posZ.toDouble(),
            posX.toDouble() + sizeX,
            posY.toDouble() + sizeY,
            posZ.toDouble() + sizeZ
        )
    }
}