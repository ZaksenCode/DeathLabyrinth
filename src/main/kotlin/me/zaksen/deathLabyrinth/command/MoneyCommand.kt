package me.zaksen.deathLabyrinth.command

import me.zaksen.deathLabyrinth.game.GameController
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

class MoneyCommand: TabExecutor {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>?
    ): MutableList<String>? {
        if(args == null) {
            return mutableListOf()
        }

        return when(args.size) {
            1 -> mutableListOf("set")
            else -> mutableListOf()
        }
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if(args == null || !sender.hasPermission("labyrinth.command.money")) {
            return true
        }

        when(args.size) {
            1 -> {
                sender.sendMessage("Введите сумму")
            }
            2 -> {
                if(sender is Player) {
                    try {
                        val amount = args[1].toInt()
                        processSet(sender, amount)
                    } catch (_: NumberFormatException) { }
                }
            }
        }

        return true
    }

    private fun processSet(player: Player, amount: Int) {
        val data = GameController.players[player]

        if(data != null) {
            data.money = amount
        }
    }
}