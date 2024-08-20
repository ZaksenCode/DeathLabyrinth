package me.zaksen.deathLabyrinth.command

import me.zaksen.deathLabyrinth.game.room.RoomController
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import java.io.File

class GameReloadCommand(private val roomsDirectory: File): CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if(!sender.hasPermission("labyrinth.command.reload")) {
            return true
        }

        RoomController.reloadRooms(roomsDirectory)

        return true
    }

}