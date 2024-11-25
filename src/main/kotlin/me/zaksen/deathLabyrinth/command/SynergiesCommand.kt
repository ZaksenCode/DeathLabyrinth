package me.zaksen.deathLabyrinth.command

import me.zaksen.deathLabyrinth.item.ItemsController
import me.zaksen.deathLabyrinth.item.ability.ItemAbilityManager
import me.zaksen.deathLabyrinth.menu.Menus
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

class SynergiesCommand: TabExecutor {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>?
    ): MutableList<String> {
        if(args == null) return mutableListOf("")

        if(args.size < 2) {
            return mutableListOf("item", "ability")
        }

        when(args[0]) {
            "item" -> {
                val result: MutableList<String> = mutableListOf()

                for(itemEntry in ItemsController.itemsMap) {
                    result.add(itemEntry.key)
                }

                result.sortBy {
                    it.compareTo(args[1])
                }

                return result
            }
            "ability" -> {
                val result: MutableList<String> = mutableListOf()

                for(abilityEntry in ItemAbilityManager.abilityMap) {
                    result.add(abilityEntry.key)
                }

                result.sortBy {
                    it.compareTo(args[1])
                }

                return result
            }
        }

        return mutableListOf("")
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if(!sender.hasPermission("labyrinth.command.synergies")) {
            return true
        }

        if(args == null) {
            return true
        }

        if(args.size < 2) {
            return true
        }

        when(args[0]) {
            "item" -> {
                val itemId = args[1]

                if (sender is Player) {
                    val item = ItemsController.get(itemId) ?: return true
                    Menus.synergies(sender, item.asItemStack())
                }
            }
            "ability" -> {
                val abilityId = args[1]

                if(sender is Player) {
                    val ability = ItemAbilityManager.abilityMap[abilityId] ?: return true
                    Menus.synergies(sender, ability)
                }
            }
        }

        return true
    }

}