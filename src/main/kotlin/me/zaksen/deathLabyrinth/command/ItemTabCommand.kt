package me.zaksen.deathLabyrinth.command

import me.zaksen.deathLabyrinth.menu.Menus
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ItemTabCommand: CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if(!sender.hasPermission("labyrinth.command.item_tab")) {
            return true
        }

        if(sender is Player) {
            Menus.itemTab(sender)
        }
        return true
    }

}