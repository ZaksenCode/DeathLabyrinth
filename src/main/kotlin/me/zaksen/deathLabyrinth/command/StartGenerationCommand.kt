package me.zaksen.deathLabyrinth.command

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class StartGenerationCommand: CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if(!sender.hasPermission("labyrinth.command.start_generation")) {
            return true
        }

        // FIXME - Room controller now didn't operate this
        //  RoomController.startGeneration()

        return true
    }
}