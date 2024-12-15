package me.zaksen.deathLabyrinth.game.room.editor.session

import kotlinx.serialization.Serializable
import me.zaksen.deathLabyrinth.config.RoomConfig
import me.zaksen.deathLabyrinth.game.room.editor.operation.Operation
import me.zaksen.deathLabyrinth.util.drawSquare
import org.bukkit.World

@Serializable
class EditorSession(
    val world: World,
    val x: Int,
    val y: Int,
    val z: Int
) {

    private val history: MutableList<Operation> = mutableListOf()
    val roomConfig: RoomConfig = RoomConfig()

    fun setSize(x: Double, y: Double, z: Double) {
        roomConfig.roomSize.x = x
        roomConfig.roomSize.y = y
        roomConfig.roomSize.z = z
    }

    fun processOperation(operation: Operation) {
        history.addLast(operation)
        operation.process(this)
    }

    fun rollbackOperation(operation: Operation) {
        operation.rollback(this)
        history.remove(operation)
    }

    fun rollbackLast() {
        rollbackOperation(history.last())
    }

    fun draw() {
        drawSquare(
            world,
            x.toDouble(),
            y.toDouble(),
            z.toDouble(),
            x.toDouble() + roomConfig.roomSize.x,
            y.toDouble() + roomConfig.roomSize.y,
            z.toDouble() + roomConfig.roomSize.z
        )

        roomConfig.tickProcesses.forEach { it.debugDisplay(world, x, y, z, roomConfig) }
        roomConfig.startProcesses.forEach { it.debugDisplay(world, x, y, z, roomConfig) }
        roomConfig.completionConditions.forEach { it.debugDisplay(world, x, y, z, roomConfig) }
        roomConfig.tags.forEach { it.debugDisplay(world, x, y, z, roomConfig) }
    }
}