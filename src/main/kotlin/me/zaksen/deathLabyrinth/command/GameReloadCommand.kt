package me.zaksen.deathLabyrinth.command

import me.zaksen.deathLabyrinth.config.ConfigContainer
import me.zaksen.deathLabyrinth.game.room.RoomController
import me.zaksen.deathLabyrinth.util.ChatUtil.message
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import java.io.File

class GameReloadCommand(private val configs: ConfigContainer, private val roomsDirectory: File): CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if(!sender.hasPermission("labyrinth.command.reload")) {
            return true
        }

        configs.reloadConfigs()

        RoomController.clearCachedData()
        RoomController.clearGeneration()
        RoomController.reloadRooms(roomsDirectory)

        sender.message(configs.langConfig().reloadSuccessText)

        return true
    }

}