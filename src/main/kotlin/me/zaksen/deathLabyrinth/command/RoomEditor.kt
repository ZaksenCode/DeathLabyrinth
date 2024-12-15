package me.zaksen.deathLabyrinth.command

import me.zaksen.deathLabyrinth.game.room.editor.RoomEditorController
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

class RoomEditor: TabExecutor {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>?
    ): MutableList<String> {
        if(args.isNullOrEmpty() || sender !is Player) return mutableListOf("")

        if(args.size == 1) {
            return mutableListOf("new")
        } else {
            val subCommand = args[0]
            return when (subCommand) {
                "new" -> processNewTab(sender, args)
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
            "new" -> processNew(sender, args)
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
        val isReplace = args[4].toBoolean()

        if(sizeX == null || sizeY == null || sizeZ == null) {
            sender.sendMessage("command.room_editor.new.args_not_number")
            return
        }

        val posX = sender.location.chunk.x * 16
        val posZ = sender.location.chunk.z * 16

        RoomEditorController.startNewRoom(sender, sender.world, posX, sender.y.toInt(), posZ, sizeX, sizeY, sizeZ, isReplace)
    }

    private fun processNewTab(sender: Player, args: Array<out String>): MutableList<String> {
        return when(args.size) {
            2 -> mutableListOf("32", "48", "64")
            3 -> mutableListOf("32", "48", "64")
            4 -> mutableListOf("32", "48", "64")
            5 -> mutableListOf("false", "true")
            else -> mutableListOf("")
        }
    }

}