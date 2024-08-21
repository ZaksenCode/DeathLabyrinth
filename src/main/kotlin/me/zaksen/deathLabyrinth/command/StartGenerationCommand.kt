package me.zaksen.deathLabyrinth.command

import me.zaksen.deathLabyrinth.game.room.RoomController
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class StartGenerationCommand: CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if(!sender.hasPermission("labyrinth.command.start_generation")) {
            return true
        }

        RoomController.startGeneration()

        return true
    }
}