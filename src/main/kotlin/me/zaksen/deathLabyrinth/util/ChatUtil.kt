package me.zaksen.deathLabyrinth.util

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.title.Title
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.HumanEntity

object ChatUtil {

    fun format(text: String, vararg args: Pair<String, String>): Component {
        return MiniMessage.miniMessage().deserialize(applyArgs(text, *args))
    }

    fun applyArgs(text: String, vararg args: Pair<String, String>): String {
        var result = text

        for(arg in args) {
            result = result.replace(arg.first, arg.second)
        }

        return result
    }

    fun CommandSender.message(msg: String, vararg args: Pair<String, String>) {
        sendMessage(format(msg, *args))
    }

    fun HumanEntity.title(msg: String, subMsg: String = "", vararg args: Pair<String, String>) {
        showTitle(Title.title(format(msg, *args), format(subMsg, *args)))
    }

    fun HumanEntity.actionBar(msg: String, vararg args: Pair<String, String>) {
        sendActionBar(format(msg, *args))
    }

    fun broadcast(msg: String, vararg args: Pair<String, String>) {
        Bukkit.getOnlinePlayers().forEach {
            it.message(msg, *args)
        }
    }

    fun broadcastTitle(msg: String, subMsg: String = "", vararg args: Pair<String, String>) {
        Bukkit.getOnlinePlayers().forEach {
            it.title(msg, subMsg, *args)
        }
    }
}