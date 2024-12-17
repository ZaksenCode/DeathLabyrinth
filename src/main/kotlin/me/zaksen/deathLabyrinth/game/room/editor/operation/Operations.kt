package me.zaksen.deathLabyrinth.game.room.editor.operation

import kotlinx.serialization.Serializable
import me.zaksen.deathLabyrinth.game.room.editor.session.EditorSession
import me.zaksen.deathLabyrinth.game.room.logic.tags.*
import net.minecraft.core.Direction

@Serializable
class MoveRoomEntrance(private val direction: Direction): Operation {
    override fun process(session: EditorSession) {
        when(direction) {
            Direction.DOWN -> session.roomConfig.entranceOffset.y += 1
            Direction.UP -> session.roomConfig.entranceOffset.y -= 1
            Direction.NORTH -> session.roomConfig.entranceOffset.z += 1
            Direction.SOUTH -> session.roomConfig.entranceOffset.z -= 1
            Direction.WEST -> session.roomConfig.entranceOffset.x += 1
            Direction.EAST -> session.roomConfig.entranceOffset.x -= 1
        }
    }

    override fun rollback(session: EditorSession) {
        when(direction) {
            Direction.DOWN -> session.roomConfig.entranceOffset.y -= 1
            Direction.UP -> session.roomConfig.entranceOffset.y += 1
            Direction.NORTH -> session.roomConfig.entranceOffset.z -= 1
            Direction.SOUTH -> session.roomConfig.entranceOffset.z += 1
            Direction.WEST -> session.roomConfig.entranceOffset.x -= 1
            Direction.EAST -> session.roomConfig.entranceOffset.x += 1
        }
    }
}

@Serializable
class MoveRoomExit(private val direction: Direction): Operation {
    override fun process(session: EditorSession) {
        when(direction) {
            Direction.DOWN -> session.roomConfig.exitOffset.y -= 1
            Direction.UP -> session.roomConfig.exitOffset.y += 1
            Direction.NORTH -> session.roomConfig.exitOffset.z -= 1
            Direction.SOUTH -> session.roomConfig.exitOffset.z += 1
            Direction.WEST -> session.roomConfig.exitOffset.x -= 1
            Direction.EAST -> session.roomConfig.exitOffset.x += 1
        }
    }

    override fun rollback(session: EditorSession) {
        when(direction) {
            Direction.DOWN -> session.roomConfig.exitOffset.y += 1
            Direction.UP -> session.roomConfig.exitOffset.y -= 1
            Direction.NORTH -> session.roomConfig.exitOffset.z += 1
            Direction.SOUTH -> session.roomConfig.exitOffset.z -= 1
            Direction.WEST -> session.roomConfig.exitOffset.x += 1
            Direction.EAST -> session.roomConfig.exitOffset.x -= 1
        }
    }
}

@Serializable
class ExpandRoom(private val direction: Direction): Operation {
    override fun process(session: EditorSession) {
        when(direction) {
            Direction.DOWN -> {
                session.y -= 1
                session.roomConfig.roomSize.y += 1

                session.roomConfig.tags.forEach {
                    it.addOffset(0, 1, 0)
                }
            }
            Direction.UP -> {
                session.roomConfig.roomSize.y += 1
            }
            Direction.NORTH -> {
                session.z -= 1
                session.roomConfig.roomSize.z += 1

                session.roomConfig.tags.forEach {
                    it.addOffset(0, 0, 1)
                }
            }
            Direction.SOUTH -> {
                session.roomConfig.roomSize.z += 1
            }
            Direction.WEST -> {
                session.x -= 1
                session.roomConfig.roomSize.x += 1

                session.roomConfig.tags.forEach {
                    it.addOffset(1, 0, 0)
                }
            }
            Direction.EAST -> {
                session.roomConfig.roomSize.x += 1
            }
        }
    }

    override fun rollback(session: EditorSession) {
        when(direction) {
            Direction.DOWN -> {
                session.y += 1
                session.roomConfig.roomSize.y -= 1

                session.roomConfig.tags.forEach {
                    it.addOffset(0, -1, 0)
                }
            }
            Direction.UP -> {
                session.roomConfig.roomSize.y -= 1
            }
            Direction.NORTH -> {
                session.z += 1
                session.roomConfig.roomSize.z -= 1

                session.roomConfig.tags.forEach {
                    it.addOffset(0, 0, -1)
                }
            }
            Direction.SOUTH -> {
                session.roomConfig.roomSize.z -= 1
            }
            Direction.WEST -> {
                session.x += 1
                session.roomConfig.roomSize.x -= 1

                session.roomConfig.tags.forEach {
                    it.addOffset(-1, 0, 0)
                }
            }
            Direction.EAST -> {
                session.roomConfig.roomSize.x -= 1
            }
        }
    }
}