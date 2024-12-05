package me.zaksen.deathLabyrinth.game.room.editor

import me.zaksen.deathLabyrinth.util.drawSquare
import org.bukkit.Location
import org.bukkit.World

object RoomEditorController {

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
        println("Start new room at $posX, $posY, $posZ with size: $sizeX, $sizeY, $sizeZ")

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