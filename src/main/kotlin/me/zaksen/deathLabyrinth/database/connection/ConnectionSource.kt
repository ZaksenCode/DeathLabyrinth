package me.zaksen.deathLabyrinth.database.connection

interface ConnectionSource {
    fun buildTables()

    fun loadCache()
}