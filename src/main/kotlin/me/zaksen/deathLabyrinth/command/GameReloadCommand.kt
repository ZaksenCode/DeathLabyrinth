package me.zaksen.deathLabyrinth.command

import me.zaksen.deathLabyrinth.config.ConfigContainer
import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.game.room.RoomController
import me.zaksen.deathLabyrinth.util.ChatUtil.message
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import java.io.File

class GameReloadCommand(private val configs: ConfigContainer, private val roomsDirectory: File): TabExecutor {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>?
    ): MutableList<String>? {
        return mutableListOf("full")
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if(!sender.hasPermission("labyrinth.command.reload")) {
            return true
        }

        configs.reloadConfigs()
        RoomController.reloadRooms(roomsDirectory)

        if(args != null) {
            try {
                when (args[0]) {
                    "full" -> {
                        fullReload()
                    }
                }
            } catch (_: IndexOutOfBoundsException) {

            }
        }

        sender.message(configs.langConfig().reloadSuccessText)

        return true
    }

    private fun fullReload() {
        GameController.reload()
    }

}