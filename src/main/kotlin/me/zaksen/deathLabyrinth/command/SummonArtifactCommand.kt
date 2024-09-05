package me.zaksen.deathLabyrinth.command

import me.zaksen.deathLabyrinth.artifacts.ArtifactsController
import me.zaksen.deathLabyrinth.util.ChatUtil.message
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

class SummonArtifactCommand: TabExecutor {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>?
    ): MutableList<String> {
        val result: MutableList<String> = mutableListOf()

        for(artifact in ArtifactsController.artifacts) {
            result.add(artifact.key)
        }

        if(args?.size!! >= 1) {
            result.sortBy {
                it.compareTo(args[0])
            }
        }

        return result
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if(!sender.hasPermission("labyrinth.command.summon_artifact")) {
            return true
        }

        if(sender is Player) {
            try {
                if(args != null) {
                    val artifactClass = ArtifactsController.artifacts[args[0]]
                    if(artifactClass != null) {
                        val artifact = artifactClass.getDeclaredConstructor().newInstance()
                        ArtifactsController.summonArtifactCard(sender.location, artifact)
                    } else {
                        sender.message("<red>Указанный артефакт не существует!")
                    }
                }
            } catch (_: IndexOutOfBoundsException) {
                sender.message("<red>Укажите имя артефакта!")
            }
        }

        return true
    }
}