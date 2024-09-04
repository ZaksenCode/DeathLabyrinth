package me.zaksen.deathLabyrinth.command

import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.menu.Menus
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ArtifactsCommand: CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if(sender is Player) {
            val data = GameController.players[sender] ?: return true
            Menus.artifactsMenu(sender, data.artifacts)
        }
        return true
    }
}