package me.zaksen.deathLabyrinth.command.room_editor

import me.zaksen.deathLabyrinth.command.api.CommandProcessor
import me.zaksen.deathLabyrinth.game.room.editor.RoomEditorController
import me.zaksen.deathLabyrinth.game.room.editor.operation.ChangeHeight
import me.zaksen.deathLabyrinth.game.room.logic.tick.HeightMinLimit
import org.bukkit.entity.Player

class RoomEditorTickProcesses: CommandProcessor {
    override fun process(sender: Player, args: Array<out String>) {
        if(args.size < 3) {
            sender.sendMessage("command.room_editor.tick_logic.not_enough_arguments")
            return
        }

        when(args[1]) {
            "min_height" -> processMinHeight(sender, args)
        }
    }

    private fun processMinHeight(sender: Player, args: Array<out String>) {
        val session = RoomEditorController.getSession(sender) ?: return
        val heightProcess = session.roomConfig.getTickProcess<HeightMinLimit>() ?: return

        when(args[2]) {
            "set" -> RoomEditorController.processSessionOperation(sender, ChangeHeight(args[3].toInt()))
            "add" -> RoomEditorController.processSessionOperation(sender, ChangeHeight(heightProcess.height + args[3].toInt()))
            "subtract" -> RoomEditorController.processSessionOperation(sender, ChangeHeight(heightProcess.height - args[3].toInt()))
        }
    }

    override fun processTab(sender: Player, args: Array<out String>): MutableList<String> {
        if(args.size == 2) {
            return mutableListOf("min_height")
        } else {
            val tickFun = args[1]
            return when (tickFun) {
                "min_height" -> processMinHeightTab(sender, args)
                else -> mutableListOf("")
            }
        }
    }

    private fun processMinHeightTab(sender: Player, args: Array<out String>): MutableList<String> {
        if(args.size == 3) {
            return mutableListOf("set", "add", "subtract")
        } else {
            val subCommand = args[2]
            return when (subCommand) {
                "set" -> mutableListOf("0")
                "add" -> mutableListOf("0")
                "subtract" -> mutableListOf("0")
                else -> mutableListOf("")
            }
        }
    }
}