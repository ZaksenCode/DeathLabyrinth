package me.zaksen.deathLabyrinth.command

import me.zaksen.deathLabyrinth.game.GameController
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

class GameJoinCommand: TabExecutor {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>?
    ): MutableList<String> {
        if(args != null) {
            if(args.size == 1) {
                val typedNick: String = args[0]
                val result: MutableList<String> = mutableListOf()

                Bukkit.getOnlinePlayers()
                    .stream()
                    .filter {
                        it.name.startsWith(typedNick)
                    }
                    .toList().forEach {
                        result.add(it.name)
                    }

                return result
            }
        }
        return mutableListOf()
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if(!sender.hasPermission("labyrinth.command.join")) {
            return true
        }

        if(sender is Player) {
            if(args == null) {
                GameController.join(sender)
                return true
            }

            if(args.isEmpty()) {
                GameController.join(sender)
                return true
            }
            else {
                val player = Bukkit.getPlayer(args[0]) ?: return true
                GameController.join(player)
            }
        }

        return true
    }
}