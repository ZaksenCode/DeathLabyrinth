package me.zaksen.deathLabyrinth.command

import me.zaksen.deathLabyrinth.entity.EntityController
import me.zaksen.deathLabyrinth.util.asTranslate
import net.kyori.adventure.text.format.TextColor
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

        for(entity in EntityController.entities) {
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
        if(!sender.hasPermission("labyrinth.command.custom_summon")) {
            return true
        }

        if(sender is Player) {
            try {
                if(args != null) {
                    val entityClass = EntityController.entities[args[0]]
                    if(entityClass != null) {
                        val entity = entityClass.getDeclaredConstructor(Location::class.java).newInstance(sender.location)
                        val world = (sender.world as CraftWorld).handle
                        world.tryAddFreshEntityWithPassengers(entity)
                    } else {
                        sender.sendMessage("text.command.custom_summon.entity_not_found".asTranslate().color(TextColor.color(240,128,128)))
                    }
                }
            } catch (_: IndexOutOfBoundsException) {
                sender.sendMessage("text.command.custom_summon.entity_not_specified".asTranslate().color(TextColor.color(240,128,128)))
            }
        }

        return true
    }
}