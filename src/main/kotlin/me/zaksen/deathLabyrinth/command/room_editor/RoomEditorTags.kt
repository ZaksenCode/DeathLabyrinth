package me.zaksen.deathLabyrinth.command.room_editor

import me.zaksen.deathLabyrinth.command.api.CommandProcessor
import me.zaksen.deathLabyrinth.config.data.Entity
import me.zaksen.deathLabyrinth.config.data.Position
import me.zaksen.deathLabyrinth.entity.EntityController
import me.zaksen.deathLabyrinth.game.room.editor.RoomEditorController
import me.zaksen.deathLabyrinth.game.room.editor.operation.*
import me.zaksen.deathLabyrinth.game.room.logic.tags.EntitiesPool
import me.zaksen.deathLabyrinth.util.isNumeric
import org.bukkit.Location
import org.bukkit.entity.Player
import java.text.DecimalFormat
import kotlin.math.floor

class RoomEditorTags: CommandProcessor {

    override fun process(sender: Player, args:  Array<out String>) {
        when(args[1]) {
            "pots" -> processPotsTag(sender, args)
            "entities_pools" -> processEntitiesTag(sender, args)
        }
    }

    private fun processPotsTag(sender: Player, args: Array<out String>) {
        when(args[2]) {
            "add" -> {
                val session = RoomEditorController.getSession(sender) ?: return

                RoomEditorController.processSessionOperation(sender, AddPot(
                    Position(
                    floor(sender.x - session.x),
                    floor(sender.y - session.y),
                    floor(sender.z - session.z)
                )
                )
                )
            }
            "remove" -> {
                RoomEditorController.processSessionOperation(sender, RemovePot(args[3].toInt()))
            }
            "list" -> {
                val session = RoomEditorController.getSession(sender) ?: return
                val pots = session.roomConfig.potSpawns
                var index = 0

                pots.forEach {
                    sender.sendMessage("${index++} - [${it.x}, ${it.y}, ${it.z}] - [${session.x + it.x}, ${session.y + it.y}, ${session.z + it.z}]")
                }
            }
            "tp" -> {
                val session = RoomEditorController.getSession(sender) ?: return
                val pot = session.roomConfig.potSpawns[args[3].toInt()]

                sender.teleport(Location(sender.world, session.x + pot.x, session.y + pot.y, session.z + pot.z))
            }
            "move" -> {
                val session = RoomEditorController.getSession(sender) ?: return

                RoomEditorController.processSessionOperation(sender, ChangePotPos(args[3].toInt(), Position(
                    floor(sender.x - session.x),
                    floor(sender.y - session.y),
                    floor(sender.z - session.z)
                )))
            }
        }
    }

    private fun processEntitiesTag(sender: Player, args: Array<out String>) {
        when(args[2]) {
            "add" -> {
                if(args.size > 3) {
                    val session = RoomEditorController.getSession(sender) ?: return
                    val poolId = args[3].toInt()
                    val entity = args[5]

                    RoomEditorController.processSessionOperation(sender, AddEntitiesPoolEntry(
                        poolId,
                        Entity(
                            entityName = entity,
                            spawnPosition = Position(
                                sender.x - session.x,
                                sender.y - session.y,
                                sender.z - session.z
                            )
                        )
                    ))

                    return
                }

                RoomEditorController.processSessionOperation(sender, AddEntitiesPool())
            }
            "remove" -> {
                val poolId = args[3].toInt()
                val entityId = args[4].toInt()
                RoomEditorController.processSessionOperation(sender, RemoveEntitiesPoolEntry(poolId, entityId))
            }
            "list" -> {
                val session = RoomEditorController.getSession(sender) ?: return
                val entitiesPool = session.roomConfig.getTag<EntitiesPool>() ?: return

                val format = DecimalFormat("#.###")

                for(i in 0..<entitiesPool.roomEntities.size) {
                    val pool = entitiesPool.roomEntities[i]

                    sender.sendMessage("Pool with id $i")
                    for(j in 0..<pool.size) {
                        val it = pool[j]
                        sender.sendMessage("- $i:$j = ${it.entityName} - " +
                                "[${format.format(it.spawnPosition.x)}, ${format.format(it.spawnPosition.y)}, ${format.format(it.spawnPosition.z)}] - " +
                                "[${format.format(session.x + it.spawnPosition.x)}, ${format.format(session.y + it.spawnPosition.y)}, ${format.format(session.z + it.spawnPosition.z)}]")

                    }
                }
            }
            "tp" -> {
                val session = RoomEditorController.getSession(sender) ?: return
                val entitiesPool = session.roomConfig.getTag<EntitiesPool>() ?: return

                val poolId = args[3].toInt()
                val entityId = args[4].toInt()

                val poolEntry = entitiesPool.roomEntities[poolId][entityId]

                sender.teleport(
                    Location(
                        sender.world,
                        session.x + poolEntry.spawnPosition.x,
                        session.y + poolEntry.spawnPosition.y,
                        session.z + poolEntry.spawnPosition.z
                    )
                )
            }
            "move" -> {
                val session = RoomEditorController.getSession(sender) ?: return

                val poolId = args[3].toInt()
                val entityId = args[4].toInt()

                RoomEditorController.processSessionOperation(sender, MoveEntitiesPoolEntry(
                    poolId,
                    entityId,
                    Position(
                        sender.x - session.x,
                        sender.y - session.y,
                        sender.z - session.z
                    )
                ))
            }
            "change" -> {
                val poolId = args[3].toInt()
                val entityId = args[4].toInt()
                val entity = args[5]

                RoomEditorController.processSessionOperation(sender, ChangeEntitiesPoolEntry(
                    poolId,
                    entityId,
                    entity
                ))
            }
        }
    }

    override fun processTab(sender: Player, args:  Array<out String>): MutableList<String> {
        if(args.size == 2) {
            return mutableListOf("pots", "entities_pools")
        } else {
            val tag = args[1]
            return when (tag) {
                "pots" -> processPotsTab(sender, args)
                "entities_pools" -> processEntitiesTab(sender, args)
                else -> mutableListOf("")
            }
        }
    }

    private fun processPotsTab(sender: Player, args: Array<out String>): MutableList<String> {
        return when(args.size) {
            3 -> {
                mutableListOf("add", "remove", "list", "tp", "move")
            }
            4 -> {
                when(args[3]) {
                    "remove",
                    "tp",
                    "move" -> {
                        val session = RoomEditorController.getSession(sender) ?: return mutableListOf("")

                        var i = 0
                        val result = mutableListOf<String>()

                        session.roomConfig.potSpawns.forEach { _ ->
                            result.add("${i++}")
                        }

                        return result
                    }
                    else -> mutableListOf("")
                }
            }
            else -> mutableListOf("")
        }
    }

    private fun processEntitiesTab(sender: Player, args: Array<out String>): MutableList<String> {
        return when(args.size) {
            3 -> {
                mutableListOf("add", "remove", "list", "tp", "move", "change")
            }
            4 -> {
                when(args[2]) {
                    "add",
                    "remove",
                    "tp",
                    "move",
                    "change" -> {
                        val session = RoomEditorController.getSession(sender) ?: return mutableListOf("")

                        var i = 0
                        val result = mutableListOf<String>()

                        val pools = session.roomConfig.getTag<EntitiesPool>()

                        pools?.roomEntities?.forEach { _ ->
                            result.add("${i++}")
                        }

                        return result
                    }
                    else -> mutableListOf()
                }
            }
            5 -> {
                if (args[3].isNumeric()) {
                    val session = RoomEditorController.getSession(sender) ?: return mutableListOf("")
                    val poolIndex = args[3].toInt()

                    if (poolIndex >= args.size) {
                        return mutableListOf("")
                    }

                    var i = 0
                    val result = mutableListOf("add")

                    val pools = session.roomConfig.getTag<EntitiesPool>()

                    pools?.roomEntities?.get(poolIndex)?.forEach { _ ->
                        result.add("${i++}")
                    }

                    return result
                } else {
                    return mutableListOf("")
                }
            }
            6 -> {
                when(args[2]) {
                    "add",
                    "change" -> {
                        val result: MutableList<String> = mutableListOf()

                        for(entity in EntityController.entities) {
                            result.add(entity.key)
                        }

                        result.sortBy {
                            it.compareTo(args[5])
                        }

                        return result
                    }
                    else -> mutableListOf()
                }
            }
            else -> mutableListOf("")
        }
    }
}