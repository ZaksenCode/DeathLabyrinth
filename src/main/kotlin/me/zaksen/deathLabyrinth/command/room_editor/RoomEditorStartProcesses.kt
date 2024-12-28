package me.zaksen.deathLabyrinth.command.room_editor

import me.zaksen.deathLabyrinth.command.api.CommandProcessor
import me.zaksen.deathLabyrinth.game.room.editor.RoomEditorController
import me.zaksen.deathLabyrinth.game.room.editor.operation.AddSpawnEntitiesProcess
import me.zaksen.deathLabyrinth.game.room.editor.operation.RemoveSpawnEntitiesProcess
import org.bukkit.entity.Player

class RoomEditorStartProcesses: CommandProcessor {
    override fun process(sender: Player, args: Array<out String>) {
        if(args.size < 3) {
            sender.sendMessage("command.room_editor.tick_logic.not_enough_arguments")
            return
        }

        when(args[1]) {
            "spawn_entities" -> processSpawnEntities(sender, args)
        }
    }

    private fun processSpawnEntities(sender: Player, args: Array<out String>) {
        when(args[2]) {
            "add" -> {
                RoomEditorController.processSessionOperation(sender, AddSpawnEntitiesProcess())
            }
            "remove" -> {
                RoomEditorController.processSessionOperation(sender, RemoveSpawnEntitiesProcess())
            }
        }
    }

    override fun processTab(sender: Player, args: Array<out String>): MutableList<String> {
        if(args.size == 2) {
            return mutableListOf("spawn_entities")
        } else {
            val startFun = args[1]
            return when (startFun) {
                "spawn_entities" -> processSpawnEntitiesTab(sender, args)
                else -> mutableListOf("")
            }
        }
    }

    private fun processSpawnEntitiesTab(sender: Player, args: Array<out String>): MutableList<String> {
        return when(args.size) {
            3 -> mutableListOf("add", "remove")
            else -> mutableListOf("")
        }
    }
}