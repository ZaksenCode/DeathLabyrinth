package me.zaksen.deathLabyrinth.command

import me.zaksen.deathLabyrinth.exception.room.RoomLoadingException
import me.zaksen.deathLabyrinth.game.room.RoomBuilder
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
    ): MutableList<String> {
        val result: MutableList<String> = mutableListOf()

        if(args == null) {
            return result
        }

        for(roomEntry in RoomController.roomIds) {
            result.add(roomEntry.key)
        }

        if(args.size == 1) {
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
                    when(args.size) {
                        1 -> {
                            val room = RoomController.loadRoom(args[0])
                            val builtRoom = RoomBuilder.buildRoom(room, sender.chunk.x * 16, sender.y.toInt(), sender.chunk.z * 16, 0)
                            RoomController.addProcessingRoom(builtRoom)
                        }
                        2 -> {
                            val room = RoomController.loadRoom(args[0])
                            val numOfPots = args[1].toInt()
                            val builtRoom = RoomBuilder.buildRoom(room, sender.chunk.x * 16, sender.y.toInt(), sender.chunk.z * 16, numOfPots)
                            RoomController.addProcessingRoom(builtRoom)
                        }
                    }
                } catch (_: IndexOutOfBoundsException) {
                    sender.sendMessage("text.game.select_pots_num".asTranslate().color(TextColor.color(220,20,60)))
                } catch (_: NumberFormatException) {
                    sender.sendMessage("text.game.select_pots_not_num".asTranslate().color(TextColor.color(220,20,60)))
                } catch (_: RoomLoadingException) {
                    sender.sendMessage("text.game.unable_found_room".asTranslate().color(TextColor.color(220,20,60)))
                }
            }
        }

        return true
    }
}