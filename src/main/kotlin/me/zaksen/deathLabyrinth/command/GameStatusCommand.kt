package me.zaksen.deathLabyrinth.command

import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.util.asText
import me.zaksen.deathLabyrinth.util.asTranslate
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class GameStatusCommand: CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if(!sender.hasPermission("labyrinth.command.status")) {
            return true
        }

        sender.sendMessage("text.command.status.status".asTranslate(GameController.getStatus().toString().asText()))
        sender.sendMessage("text.command.status.players".asTranslate())

        GameController.players.forEach {
            sender.sendMessage("text.command.status.player".asTranslate(it.key.name.asText()))
        }

        return true
    }
}