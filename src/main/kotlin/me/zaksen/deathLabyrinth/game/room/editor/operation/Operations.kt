package me.zaksen.deathLabyrinth.game.room.editor.operation

import kotlinx.serialization.Serializable
import me.zaksen.deathLabyrinth.config.data.Entity
import me.zaksen.deathLabyrinth.config.data.Position
import me.zaksen.deathLabyrinth.game.room.editor.session.EditorSession
import me.zaksen.deathLabyrinth.game.room.logic.start.SpawnEntitiesProcess
import me.zaksen.deathLabyrinth.game.room.logic.tags.EntitiesPool
import me.zaksen.deathLabyrinth.game.room.logic.tick.HeightMinLimit
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

@Serializable
class ShrinkRoom(private val direction: Direction): Operation {
    override fun process(session: EditorSession) {
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

    override fun rollback(session: EditorSession) {
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
}

@Serializable
class ChangeHeight(private val newHeight: Int): Operation {
    private var oldHeight: Int = 0

    override fun process(session: EditorSession) {
        var heightProcess = session.roomConfig.getTickProcess<HeightMinLimit>()

        if(heightProcess == null) {
            heightProcess = HeightMinLimit(0)
            session.roomConfig.tickProcesses.addLast(heightProcess)
        }

        oldHeight = heightProcess.height
        heightProcess.height = newHeight
    }

    override fun rollback(session: EditorSession) {
        var heightProcess = session.roomConfig.getTickProcess<HeightMinLimit>()

        if(heightProcess == null) {
            heightProcess = HeightMinLimit(0)
            session.roomConfig.tickProcesses.addLast(heightProcess)
        }

        heightProcess.height = oldHeight
    }
}

@Serializable
class AddPot(private val position: Position): Operation {
    private var addedIndex = 0

    override fun process(session: EditorSession) {
        session.roomConfig.potSpawns.addLast(position)
        addedIndex = session.roomConfig.potSpawns.lastIndex
    }

    override fun rollback(session: EditorSession) {
        session.roomConfig.potSpawns.removeAt(addedIndex)
    }
}

@Serializable
class RemovePot(private val index: Int): Operation {
    private lateinit var removePosition: Position

    override fun process(session: EditorSession) {
        removePosition = session.roomConfig.potSpawns.removeAt(index)
    }

    override fun rollback(session: EditorSession) {
        session.roomConfig.potSpawns.addLast(removePosition)
    }
}

@Serializable
class ChangePotPos(private val index: Int, private val newPos: Position): Operation {
    private lateinit var oldPosition: Position

    override fun process(session: EditorSession) {
        val pot = session.roomConfig.potSpawns[index]
        oldPosition = pot
        session.roomConfig.potSpawns[index] = newPos
    }

    override fun rollback(session: EditorSession) {
        session.roomConfig.potSpawns[index] = oldPosition
    }
}

@Serializable
class AddSpawnEntitiesProcess : Operation {
    private var wasAdded = false
    private var addedIndex = 0

    override fun process(session: EditorSession) {
        val newTag = SpawnEntitiesProcess()

        if(session.roomConfig.startProcesses.filterIsInstance<SpawnEntitiesProcess>().isEmpty()) {
            session.roomConfig.startProcesses.addLast(newTag)
            addedIndex = session.roomConfig.startProcesses.lastIndex
            wasAdded = true
        }
    }

    override fun rollback(session: EditorSession) {
        if(wasAdded) {
            session.roomConfig.startProcesses.removeAt(addedIndex)
        }
    }
}

@Serializable
class RemoveSpawnEntitiesProcess : Operation {
    private var removed: SpawnEntitiesProcess? = null

    override fun process(session: EditorSession) {
        val tagList = session.roomConfig.startProcesses.filterIsInstance<SpawnEntitiesProcess>()

        if(tagList.isNotEmpty()) {
           val removed = tagList.first()
            session.roomConfig.startProcesses.remove(removed)
        }
    }

    override fun rollback(session: EditorSession) {
        if(removed != null) {
            session.roomConfig.startProcesses.addLast(removed!!)
        }
    }
}

@Serializable
class AddEntitiesPool : Operation {
    private var wasTagCreated = false

    override fun process(session: EditorSession) {
        val poolsTag = session.roomConfig.getTag<EntitiesPool>()

        if(poolsTag == null) {
            val newPools = EntitiesPool(mutableListOf())
            session.roomConfig.tags.addLast(newPools)
            wasTagCreated = true
        }
    }

    override fun rollback(session: EditorSession) {
        if(wasTagCreated) {
            session.roomConfig.tags.removeIf{it is EntitiesPool}
        }
    }

}

@Serializable
class AddEntitiesPoolEntry(private val poolId: Int, val entity: Entity) : Operation {
    private var wasTagCreated = false
    private var addedIndex = -1

    override fun process(session: EditorSession) {
        var poolsTag = session.roomConfig.getTag<EntitiesPool>()

        if(poolsTag == null) {
            val newPools = EntitiesPool()
            session.roomConfig.tags.addLast(newPools)
            poolsTag = newPools
            wasTagCreated = true
        }

        poolsTag.roomEntities[poolId].addLast(entity)
        addedIndex = poolsTag.roomEntities[poolId].lastIndex
    }

    override fun rollback(session: EditorSession) {
        if(addedIndex != -1) {
            val poolsTag = session.roomConfig.getTag<EntitiesPool>()
            poolsTag!!.roomEntities[poolId].removeAt(addedIndex)
        }

        if(wasTagCreated) {
            session.roomConfig.tags.removeIf{it is EntitiesPool}
        }
    }

}

@Serializable
class MoveEntitiesPoolEntry(private val poolId: Int, private val entryId: Int, private val location: Position) : Operation {
    private var previousPosition: Position? = null

    override fun process(session: EditorSession) {
        val poolsTag = session.roomConfig.getTag<EntitiesPool>() ?: return
        val poolEntry = poolsTag.roomEntities[poolId][entryId]
        previousPosition = poolEntry.spawnPosition
        poolEntry.spawnPosition = location
    }

    override fun rollback(session: EditorSession) {
        if(previousPosition != null) {
            val poolsTag = session.roomConfig.getTag<EntitiesPool>() ?: return
            val poolEntry = poolsTag.roomEntities[poolId][entryId]
            poolEntry.spawnPosition = previousPosition!!
        }
    }
}

@Serializable
class RemoveEntitiesPoolEntry(private val poolId: Int, private val entryId: Int) : Operation {
    private var removedEntry: Entity? = null

    override fun process(session: EditorSession) {
        val poolsTag = session.roomConfig.getTag<EntitiesPool>() ?: return
        removedEntry = poolsTag.roomEntities[poolId].removeAt(entryId)
    }

    override fun rollback(session: EditorSession) {
        if(removedEntry != null) {
            val poolsTag = session.roomConfig.getTag<EntitiesPool>() ?: return
            poolsTag.roomEntities[poolId].add(entryId, removedEntry!!)
        }
    }
}

@Serializable
class ChangeEntitiesPoolEntry(private val poolId: Int, private val entryId: Int, private val entity: String) : Operation {
    private var previousEntity: String? = null

    override fun process(session: EditorSession) {
        val poolsTag = session.roomConfig.getTag<EntitiesPool>() ?: return
        val poolEntry = poolsTag.roomEntities[poolId][entryId]
        previousEntity = poolEntry.entityName
        poolEntry.entityName = entity
    }

    override fun rollback(session: EditorSession) {
        if(previousEntity != null) {
            val poolsTag = session.roomConfig.getTag<EntitiesPool>() ?: return
            val poolEntry = poolsTag.roomEntities[poolId][entryId]
            poolEntry.entityName = previousEntity!!
        }
    }
}
