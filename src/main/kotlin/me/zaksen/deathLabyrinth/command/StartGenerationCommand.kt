package me.zaksen.deathLabyrinth.command

import me.zaksen.deathLabyrinth.game.room.RoomGenerator
import me.zaksen.deathLabyrinth.game.room.RoomType
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class StartGenerationCommand: CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if(!sender.hasPermission("labyrinth.command.start_generation")) {
            return true
        }

        if(sender is Player) {
            RoomGenerator.startSubFloorGeneration(
                sender.world,
                sender.chunk.x * 16,
                sender.chunk.z * 16,
                3,
                1,
                0,
                setOf(RoomType.NORMAL)
            )
        }

        return true
    }
}