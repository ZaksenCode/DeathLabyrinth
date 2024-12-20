package me.zaksen.deathLabyrinth.util

fun String.isNumeric(): Boolean {
    return all { char -> char.isDigit() }
}