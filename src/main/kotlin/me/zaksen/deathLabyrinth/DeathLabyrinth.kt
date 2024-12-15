package me.zaksen.deathLabyrinth

import kotlinx.serialization.encodeToString
import me.zaksen.deathLabyrinth.command.*
import me.zaksen.deathLabyrinth.config.*
import me.zaksen.deathLabyrinth.event.CustomItemEvents
import me.zaksen.deathLabyrinth.event.GameEvents
import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.game.room.RoomBuilder
import me.zaksen.deathLabyrinth.game.room.RoomController
import me.zaksen.deathLabyrinth.game.room.RoomFloorController
import me.zaksen.deathLabyrinth.game.room.editor.RoomEditorController
import me.zaksen.deathLabyrinth.keys.PluginKeys
import me.zaksen.deathLabyrinth.menu.Menus
import me.zaksen.deathLabyrinth.util.LaserManager
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class DeathLabyrinth : JavaPlugin(), ConfigContainer {

    private val roomDirectory = File(dataFolder, "rooms")
    private lateinit var mainConfig: MainConfig
    private lateinit var generationConfig: GenerationConfig

    override fun onEnable() {
        reloadConfigs()
        LaserManager.setup(this)
        Menus.setup(this)
        PluginKeys.setup(this)
        RoomBuilder.setup(this)
        RoomController.reload(roomDirectory)
        GameController.setup(this, this)
        RoomFloorController.setup(this)
        RoomEditorController.setup(this)
        registerEvents()
        registerCommands()

        println(yaml.encodeToString<RoomConfig>(RoomConfig()))
    }

    override fun onDisable() {
        GameController.reload()
    }

    private fun registerEvents() {
        server.pluginManager.registerEvents(CustomItemEvents(), this)
        server.pluginManager.registerEvents(GameEvents(mainConfig), this)
    }

    private fun registerCommands() {
        getCommand("game_status")?.setExecutor(GameStatusCommand())
        getCommand("game_join")?.setExecutor(GameJoinCommand())
        getCommand("game_join")?.tabCompleter = GameJoinCommand()
        getCommand("class")?.setExecutor(ClassCommand(this))
        getCommand("give_item")?.setExecutor(GiveItemCommand())
        getCommand("give_item")?.tabCompleter = GiveItemCommand()
        getCommand("reload_game")?.setExecutor(GameReloadCommand(this, roomDirectory))
        getCommand("reload_game")?.tabCompleter = GameReloadCommand(this, roomDirectory)
        getCommand("item_tab")?.setExecutor(ItemTabCommand())
        getCommand("summon_custom")?.setExecutor(CustomSummonCommand())
        getCommand("summon_custom")?.tabCompleter = CustomSummonCommand()
        getCommand("build_room")?.setExecutor(BuildRoomCommand())
        getCommand("build_room")?.tabCompleter = BuildRoomCommand()
        getCommand("start_room")?.setExecutor(StartRoomCommand())
        getCommand("start_generation")?.setExecutor(StartGenerationCommand())
        getCommand("clear_generation")?.setExecutor(ClearGenerationCommand())
        getCommand("money")?.setExecutor(MoneyCommand())
        getCommand("money")?.tabCompleter = MoneyCommand()
        getCommand("artifacts")?.setExecutor(ArtifactsCommand())
        getCommand("accessory")?.setExecutor(AccessoryCommand())
        getCommand("summon_artifact")?.setExecutor(SummonArtifactCommand())
        getCommand("summon_artifact")?.tabCompleter = SummonArtifactCommand()
        getCommand("despawn_artifacts")?.setExecutor(DespawnArtifactsCommand())
        getCommand("synergies")?.setExecutor(SynergiesCommand())
        getCommand("synergies")?.tabCompleter = SynergiesCommand()
        getCommand("room_editor")?.setExecutor(RoomEditor())
        getCommand("room_editor")?.tabCompleter = RoomEditor()
    }

    override fun reloadConfigs() {
        mainConfig = loadConfig(dataFolder, "main-config.yml")
        generationConfig = loadConfig(dataFolder, "generation-config.yml")
    }

    override fun mainConfig(): MainConfig {
        return mainConfig
    }

    override fun generationConfig(): GenerationConfig {
        return generationConfig
    }
}
