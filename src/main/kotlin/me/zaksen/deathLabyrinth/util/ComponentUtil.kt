package me.zaksen.deathLabyrinth.util

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.title.Title
import org.bukkit.Bukkit
import org.bukkit.entity.HumanEntity
import xyz.xenondevs.inventoryaccess.component.AdventureComponentWrapper

fun String.asText() = MiniMessage.miniMessage().deserialize(this).decoration(TextDecoration.ITALIC, false)
fun String.asTranslate() = Component.translatable(this).decoration(TextDecoration.ITALIC, false)
fun String.asTranslate(vararg arguments: Component) = Component.translatable(this, *arguments).decoration(TextDecoration.ITALIC, false)
fun String.toWrapper() = AdventureComponentWrapper(this.asText())

fun Component.toWrapper() = AdventureComponentWrapper(this)

fun Component.broadcast() {
    Bukkit.getOnlinePlayers().forEach {
        it.sendMessage(this)
    }
}

fun Component.broadcastTitle(subTitle: Component = Component.text("")) {
    Bukkit.getOnlinePlayers().forEach {
        it.title(this, subTitle)
    }
}

fun HumanEntity.title(msg: Component, subMsg: Component = Component.text("")) {
    showTitle(Title.title(msg, subMsg))
}