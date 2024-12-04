package me.zaksen.deathLabyrinth.command

import me.zaksen.deathLabyrinth.game.room.RoomController
import me.zaksen.deathLabyrinth.util.asTranslate
import net.kyori.adventure.text.format.TextColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class RoomStatusCommand: CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if(!sender.hasPermission("labyrinth.command.clear_command")) {
            return true
        }

        if(sender is Player) {
            if(args != null) {
                val room = RoomController.getPlayerProcessingRoom(sender)

                if(room == null) {
                    sender.sendMessage("text.game.not_in_room".asTranslate().color(TextColor.color(220,20,60)))
                } else {
                    sender.sendMessage("Is completed: ${room.isCompleted}")
                    sender.sendMessage("Is started: ${room.isStarted}")
                }
            }
        }


        return true
    }
}