package me.zaksen.deathLabyrinth.command

import me.zaksen.deathLabyrinth.config.ConfigContainer
import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.game.GameStatus
import me.zaksen.deathLabyrinth.menu.MenuController
import me.zaksen.deathLabyrinth.menu.custom.ClassChoiceMenu
import me.zaksen.deathLabyrinth.util.ChatUtil.message
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ClassCommand(private val configs: ConfigContainer): CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if(sender is Player) {
            val playerData = GameController.players[sender]

            if(playerData?.playerClass == null) {
                if(GameController.getStatus() == GameStatus.PRE_PROCESS) {
                    MenuController.openMenu(sender, ClassChoiceMenu(configs))
                } else {
                    sender.message(configs.langConfig().classGameNotStarted)
                }
            } else {
                val playerClass = playerData.playerClass!!
                sender.message(configs.langConfig().classAlreadySelected, Pair("{class_name}", playerClass.getClassName()))
            }
        }

        return true
    }

}