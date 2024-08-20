package me.zaksen.deathLabyrinth.command

import me.zaksen.deathLabyrinth.entity.CustomEntityController
import me.zaksen.deathLabyrinth.util.ChatUtil.message
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.craftbukkit.CraftWorld
import org.bukkit.entity.Player

class CustomSummonCommand: TabExecutor {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>?
    ): MutableList<String>? {
        val result: MutableList<String> = mutableListOf()

        for(entity in CustomEntityController.entities) {
            result.add(entity.key)
        }

        if(args?.size!! >= 1) {
            result.sortBy {
                it.compareTo(args[0])
            }
        }

        return result
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if(sender is Player) {
            try {
                if(args != null) {
                    val entityClass = CustomEntityController.entities[args[0]]
                    if(entityClass != null) {
                        val entity = entityClass.getDeclaredConstructor(Location::class.java).newInstance(sender.location)
                        val world = (sender.world as CraftWorld).handle
                        world.tryAddFreshEntityWithPassengers(entity)
                    } else {
                        sender.message("<red>Указанная сущность не существует!")
                    }
                }
            } catch (_: IndexOutOfBoundsException) {
                sender.message("<red>Укажите имя сущности!")
            }
        }

        return true
    }
}