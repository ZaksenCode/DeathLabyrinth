package me.zaksen.deathLabyrinth.command

import me.zaksen.deathLabyrinth.artifacts.ArtifactsController
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class DespawnArtifactsCommand: CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if(!sender.hasPermission("labyrinth.command.despawn_artifacts")) {
            return true
        }
        ArtifactsController.despawnArtifacts()
        return true
    }
}