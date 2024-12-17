package me.zaksen.deathLabyrinth.game.room.editor.session

import kotlinx.serialization.Serializable
import me.zaksen.deathLabyrinth.config.RoomConfig
import me.zaksen.deathLabyrinth.game.room.editor.operation.Operation
import me.zaksen.deathLabyrinth.game.room.editor.operation.rollback.RollbackResult
import me.zaksen.deathLabyrinth.util.drawSquare
import me.zaksen.deathLabyrinth.util.serialization.WorldSerializer
import org.bukkit.Particle
import org.bukkit.World
import java.io.File

@Serializable
class EditorSession(
    val name: String,
    @Serializable(with = WorldSerializer::class)
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

    fun rollbackLast(): RollbackResult {
        if(history.isNotEmpty()) {
            rollbackOperation(history.last())
            return RollbackResult.SUCCESS
        }
        return RollbackResult.EMPTY_HISTORY
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

        // Draw entrance box
        val entranceX = x + roomConfig.entranceOffset.x
        val entranceY = y + roomConfig.entranceOffset.y
        val entranceZ = z + roomConfig.entranceOffset.z

        drawSquare(
            world,
            entranceX,
            entranceY,
            entranceZ,
            entranceX + 1,
            entranceY + 6,
            entranceZ + 7,
            particle = Particle.WAX_OFF,
        )

        // Draw exit box
        val exitX = x + roomConfig.exitOffset.x
        val exitY = y + roomConfig.exitOffset.y
        val exitZ = z + roomConfig.exitOffset.z

        drawSquare(
            world,
            exitX,
            exitY,
            exitZ,
            exitX - 1,
            exitY + 6,
            exitZ + 7,
            particle = Particle.WAX_OFF,
        )
    }
}