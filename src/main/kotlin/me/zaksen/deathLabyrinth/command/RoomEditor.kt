package me.zaksen.deathLabyrinth.command

import me.zaksen.deathLabyrinth.game.room.editor.RoomEditorController
import me.zaksen.deathLabyrinth.game.room.editor.operation.*
import me.zaksen.deathLabyrinth.game.room.editor.operation.rollback.RollbackResult
import net.minecraft.core.Direction
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.craftbukkit.entity.CraftEntity
import org.bukkit.entity.Player
import java.util.*

// TODO - Check new commands -> exit, save, stop, export
class RoomEditor: TabExecutor {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>?
    ): MutableList<String> {
        if(args.isNullOrEmpty() || sender !is Player) return mutableListOf("")

        if(args.size == 1) {
            return mutableListOf("load", "new", "entrance", "undo", "exit", "save", "stop", "export")
        } else {
            val subCommand = args[0]
            return when (subCommand) {
                "load" -> processLoadTab(sender, args)
                "new" -> processNewTab(sender, args)
                "entrance" -> processEntranceTab(sender, args)
                "exit" -> processExitTab(sender, args)
                else -> return mutableListOf("")
            }
        }
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if(!sender.hasPermission("labyrinth.command.room_editor")) return true
        if(sender !is Player) return true

        if(args.isNullOrEmpty()) {
            sender.sendMessage("command.room_editor.no_args")
            return true
        }

        val subCommand = args[0]

        when(subCommand) {
            "load" -> processLoad(sender, args)
            "new" -> processNew(sender, args)
            "entrance" -> processEntrance(sender, args)
            "exit" -> processExit(sender, args)
            "undo" -> processUndo(sender, args)
            "save" -> processSave(sender, args)
            "stop" -> processStop(sender, args)
            "export" -> processExport(sender, args)
        }

        return true
    }

    private fun processNew(sender: Player, args: Array<out String>) {
        if(args.size < 5) {
            sender.sendMessage("command.room_editor.new.not_enough_arguments")
            return
        }

        val sizeX = args[1].toIntOrNull()
        val sizeY = args[2].toIntOrNull()
        val sizeZ = args[3].toIntOrNull()
        val name = args[4]

        if(sizeX == null || sizeY == null || sizeZ == null) {
            sender.sendMessage("command.room_editor.new.args_not_number")
            return
        }

        val posX = sender.location.chunk.x * 16
        val posZ = sender.location.chunk.z * 16

        RoomEditorController.startNewRoom(sender, sender.world, posX, sender.y.toInt(), posZ, sizeX, sizeY, sizeZ, name)
    }

    private fun processNewTab(sender: Player, args: Array<out String>): MutableList<String> {
        return when(args.size) {
            2 -> mutableListOf("32", "48", "64")
            3 -> mutableListOf("32", "48", "64")
            4 -> mutableListOf("32", "48", "64")
            5 -> mutableListOf("name")
            else -> mutableListOf("")
        }
    }

    private fun processEntrance(sender: Player, args: Array<out String>) {
        val direction = if(args.size < 3) {
            (sender as CraftEntity).handle.direction
        } else {
            Direction.valueOf(args[2].uppercase(Locale.getDefault()))
        }

        RoomEditorController.processSessionOperation(sender, MoveRoomEntrance(direction))
    }

    private fun processEntranceTab(sender: Player, args: Array<out String>): MutableList<String> {
        return when(args.size) {
            2 -> mutableListOf("move")
            3 -> mutableListOf("${Direction.UP}", "${Direction.DOWN}", "${Direction.WEST}", "${Direction.EAST}", "${Direction.NORTH}", "${Direction.SOUTH}")
            else -> mutableListOf("")
        }
    }

    private fun processUndo(sender: Player, args: Array<out String>) {
        val result = RoomEditorController.processSessionRollback(sender)

        when(result) {
            RollbackResult.NO_SESSION -> {
                sender.sendMessage("command.room_editor.no_session")
            }
            RollbackResult.EMPTY_HISTORY -> {
                sender.sendMessage("command.room_editor.undo.empty_history")
            }
            else -> {}
        }
    }

    private fun processExit(sender: Player, args: Array<out String>) {
        val direction = if(args.size < 3) {
            (sender as CraftEntity).handle.direction
        } else {
            Direction.valueOf(args[2].uppercase(Locale.getDefault()))
        }

        RoomEditorController.processSessionOperation(sender, MoveRoomExit(direction))
    }

    private fun processExitTab(sender: Player, args: Array<out String>): MutableList<String> {
        return when(args.size) {
            2 -> mutableListOf("move")
            3 -> mutableListOf("${Direction.UP}", "${Direction.DOWN}", "${Direction.WEST}", "${Direction.EAST}", "${Direction.NORTH}", "${Direction.SOUTH}")
            else -> mutableListOf("")
        }
    }

    private fun processSave(sender: Player, args: Array<out String>) {
        RoomEditorController.saveSessions()
    }

    private fun processStop(sender: Player, args: Array<out String>) {
        RoomEditorController.exportSession(sender)
        RoomEditorController.stopSession(sender)
        RoomEditorController.saveSessions()
    }

    private fun processExport(sender: Player, args: Array<out String>) {
        RoomEditorController.exportSession(sender)
        RoomEditorController.saveSessions()
    }

    private fun processLoad(sender: Player, args: Array<out String>) {

    }

    private fun processLoadTab(sender: Player, args: Array<out String>): MutableList<String> {
        return when(args.size) {
            2 -> mutableListOf("")
            else -> mutableListOf("")
        }
    }
}