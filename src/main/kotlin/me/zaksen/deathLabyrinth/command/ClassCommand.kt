package me.zaksen.deathLabyrinth.command

import me.zaksen.deathLabyrinth.config.ConfigContainer
import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.game.GameStatus
import me.zaksen.deathLabyrinth.menu.Menus
import me.zaksen.deathLabyrinth.util.asTranslate
import net.kyori.adventure.text.format.TextColor
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
                    Menus.classChoice(sender)
                } else {
                    sender.sendMessage("text.game.not_started".asTranslate().color(TextColor.color(240,128,128)))
                }
            } else {
                val playerClass = playerData.playerClass!!
                sender.sendMessage("text.game.class_name".asTranslate(playerClass.getClassName()).color(TextColor.color(50,205,50)))
            }
        }

        return true
    }

}