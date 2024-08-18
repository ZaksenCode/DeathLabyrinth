package me.zaksen.deathLabyrinth.command

import me.zaksen.deathLabyrinth.item.ItemsController
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

class GiveItemCommand: TabExecutor {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>?
    ): MutableList<String> {
        val result: MutableList<String> = mutableListOf()

        for(itemEntry in ItemsController.itemsMap) {
            result.add(itemEntry.key)
        }

        return result
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if(!sender.hasPermission("labyrinth.command.give")) {
            return true
        }

        if(args == null) {
            return true
        }

        val itemId = args[0]

        if(sender is Player) {
            val item = ItemsController.get(itemId)

            if(item != null) {
                sender.inventory.addItem(item.asItemstack())
            }
        }


        return true
    }
}