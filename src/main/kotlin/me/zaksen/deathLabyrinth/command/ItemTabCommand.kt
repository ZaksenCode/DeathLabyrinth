package me.zaksen.deathLabyrinth.command

import me.zaksen.deathLabyrinth.menu.MenuController
import me.zaksen.deathLabyrinth.menu.custom.ItemTabMenu
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ItemTabCommand: CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if(sender is Player) {
            var page = 0

            if(args != null) {
                try {
                    page = args[0].toInt()
                } catch (_: NumberFormatException) {

                } catch (_: IndexOutOfBoundsException) {

                }
            }

            MenuController.openMenu(sender, ItemTabMenu(page))
        }
        return true
    }

}