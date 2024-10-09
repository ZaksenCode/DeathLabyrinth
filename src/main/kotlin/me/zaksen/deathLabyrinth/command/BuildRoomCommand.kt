package me.zaksen.deathLabyrinth.command

import me.zaksen.deathLabyrinth.game.room.RoomController
import me.zaksen.deathLabyrinth.util.asTranslate
import net.kyori.adventure.text.format.TextColor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

class BuildRoomCommand: TabExecutor {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>?
    ): MutableList<String>? {
        val result: MutableList<String> = mutableListOf()

        for(itemEntry in RoomController.rooms) {
            result.add(itemEntry.key)
        }

        if(args?.size!! == 1) {
            result.sortBy {
                it.compareTo(args[0])
            }
        } else {
            result.clear()
            result.add("0")
        }

        return result
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if(!sender.hasPermission("labyrinth.command.build_room")) {
            return true
        }

        if(sender is Player) {
            if(args != null) {
                try {
                    val room = RoomController.rooms[args[0]]
                    val numOfPots = args[1].toInt()

                    if (room != null) {
                        RoomController.buildRoom(room, sender.x.toInt(), sender.y.toInt(), sender.z.toInt(), true, numOfPots, numOfPots)
                    } else {
                        sender.sendMessage("text.game.room_not_found".asTranslate().color(TextColor.color(220,20,60)))
                    }
                } catch (_: IndexOutOfBoundsException) {
                    sender.sendMessage("text.game.select_pots_num".asTranslate().color(TextColor.color(220,20,60)))
                } catch (_: NumberFormatException) {
                    sender.sendMessage("text.game.select_pots_not_num".asTranslate().color(TextColor.color(220,20,60)))
                }
            }
        }

        return true
    }
}