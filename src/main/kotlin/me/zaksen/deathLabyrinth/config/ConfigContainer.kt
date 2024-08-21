package me.zaksen.deathLabyrinth.config

interface ConfigContainer {

    fun reloadConfigs()

    fun mainConfig(): MainConfig
    fun langConfig(): LangConfig
}