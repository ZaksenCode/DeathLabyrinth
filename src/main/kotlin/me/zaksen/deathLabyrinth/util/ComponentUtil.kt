package me.zaksen.deathLabyrinth.util

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.MiniMessage
import xyz.xenondevs.inventoryaccess.component.AdventureComponentWrapper

fun String.asText() = MiniMessage.miniMessage().deserialize(this).decoration(TextDecoration.ITALIC, false)
fun String.toWrapper() = AdventureComponentWrapper(this.asText())
fun Component.toWrapper() = AdventureComponentWrapper(this)