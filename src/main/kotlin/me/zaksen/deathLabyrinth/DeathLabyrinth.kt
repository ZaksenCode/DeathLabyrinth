package me.zaksen.deathLabyrinth

import me.zaksen.deathLabyrinth.command.ClassCommand
import me.zaksen.deathLabyrinth.command.GameStatusCommand
import me.zaksen.deathLabyrinth.command.GiveItemCommand
import me.zaksen.deathLabyrinth.config.MainConfig
import me.zaksen.deathLabyrinth.config.loadConfig
import me.zaksen.deathLabyrinth.event.CustomItemEvents
import me.zaksen.deathLabyrinth.event.GameEvents
import me.zaksen.deathLabyrinth.event.MenuEvents
import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.game.room.RoomController
import me.zaksen.deathLabyrinth.keys.PluginKeys
import org.bukkit.plugin.java.JavaPlugin

class DeathLabyrinth : JavaPlugin() {

    private lateinit var mainConfig: MainConfig

    override fun onEnable() {
        loadConfigs()
        PluginKeys.setup(this)
        GameController.setup(this, mainConfig)
        RoomController.setup(mainConfig)
        registerEvents()
        registerCommands()
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    private fun loadConfigs() {
        mainConfig = loadConfig(dataFolder, "main-config.yml")
    }

    private fun registerEvents() {
        server.pluginManager.registerEvents(MenuEvents(), this)
        server.pluginManager.registerEvents(CustomItemEvents(), this)
        server.pluginManager.registerEvents(GameEvents(mainConfig), this)
    }

    private fun registerCommands() {
        getCommand("game_status")?.setExecutor(GameStatusCommand())
        getCommand("class")?.setExecutor(ClassCommand(mainConfig))
        getCommand("give_item")?.setExecutor(GiveItemCommand())
        getCommand("give_item")?.tabCompleter = GiveItemCommand()
    }
}
