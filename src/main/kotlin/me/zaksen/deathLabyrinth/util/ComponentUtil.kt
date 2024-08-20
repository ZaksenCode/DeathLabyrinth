package me.zaksen.deathLabyrinth.util

import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.MiniMessage

fun String.asText() = MiniMessage.miniMessage().deserialize(this).decoration(TextDecoration.ITALIC, false)