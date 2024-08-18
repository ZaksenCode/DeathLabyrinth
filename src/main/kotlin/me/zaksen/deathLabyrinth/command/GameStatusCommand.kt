package me.zaksen.deathLabyrinth.command

import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.util.ChatUtil.message
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class GameStatusCommand: CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if(!sender.hasPermission("labyrinth.command.status")) {
            return true
        }

        sender.message("Статус: {status}", Pair("{status}", GameController.getStatus().toString()))
        sender.message("Игроки:")
        GameController.players.forEach {
            sender.message(" {player}", Pair("{player}", it.key.name))
        }

        return true
    }
}