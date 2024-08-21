package me.zaksen.deathLabyrinth.game

import me.zaksen.deathLabyrinth.config.ConfigContainer
import me.zaksen.deathLabyrinth.data.PlayerData
import me.zaksen.deathLabyrinth.menu.MenuController
import me.zaksen.deathLabyrinth.menu.custom.ClassChoiceMenu
import me.zaksen.deathLabyrinth.util.ChatUtil
import me.zaksen.deathLabyrinth.util.asText
import me.zaksen.deathLabyrinth.util.customModel
import me.zaksen.deathLabyrinth.util.name
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask

object GameController {
    private lateinit var plugin: Plugin
    val players: MutableMap<Player, PlayerData> = mutableMapOf()

    private lateinit var configs: ConfigContainer
    private var status: GameStatus = GameStatus.WAITING

    private var startCooldownTask: BukkitTask? = null
    private var startCooldownTime: Short = 5

    fun getStatus(): GameStatus {
        return status
    }

    fun setup(plugin: Plugin, configs: ConfigContainer) {
        this.plugin = plugin
        this.configs = configs
    }

    fun join(player: Player) {
        players[player] = PlayerData()

        setupPlayer(player)
    }

    private fun setupPlayer(player: Player) {
        player.teleport(
            Location(
                Bukkit.getWorld(configs.mainConfig().playerSpawnLocation.world),
                configs.mainConfig().playerSpawnLocation.x,
                configs.mainConfig().playerSpawnLocation.y,
                configs.mainConfig().playerSpawnLocation.z
            )
        )

        player.inventory.clear()
        player.inventory.setItem(4, ItemStack(Material.PAPER).name(configs.langConfig().notReady.asText()).customModel(200))
    }

    fun leave(player: Player) {
        players.remove(player)
    }

    fun toggleReadyState(player: Player) {
        val playerData = players[player]
        if(playerData != null) {
            playerData.isReady = !playerData.isReady

            if(playerData.isReady) {
                player.inventory.setItemInMainHand(ItemStack(Material.PAPER).customModel(201).name(
                    configs.langConfig().ready.asText()
                ))
                startGameCooldown()
            } else {
                player.inventory.setItemInMainHand(ItemStack(Material.PAPER).customModel(200).name(
                    configs.langConfig().notReady.asText()
                ))
                stopGameCooldown()
            }

            players[player] = playerData
        }
    }

    private fun startGameCooldown() {
        if(players.size >= configs.mainConfig().minimalPlayers && isPlayersReady() && status == GameStatus.WAITING) {
            status = GameStatus.PREPARE

            startCooldownTask = object: BukkitRunnable() {
                override fun run() {
                    if(startCooldownTime <= 0) {
                        startGame()
                        cancel()
                        startCooldownTask = null
                    } else {
                        ChatUtil.broadcastTitle(configs.langConfig().gameStartingTitle, "", Pair("{time}", startCooldownTime.toString()))
                    }

                    startCooldownTime--
                }
            }.runTaskTimer(plugin, 0, 20)
        }
    }

    private fun stopGameCooldown() {
        if(players.size >= configs.mainConfig().minimalPlayers && isPlayersReady() && status == GameStatus.PREPARE) {
            status = GameStatus.WAITING

            startCooldownTask?.cancel()
            startCooldownTask = null
            startCooldownTime = 5

            ChatUtil.broadcastTitle(configs.langConfig().gameStartingStopTitle)
        }
    }

    private fun isPlayersReady(): Boolean {
        var result = true

        for(player in players) {
            if(!player.value.isReady) {
                result = false
            }
        }

        return result
    }

    fun startGame() {
        status = GameStatus.PRE_PROCESS

        ChatUtil.broadcast(configs.langConfig().gameStartingCloseClassMenu)
        players.forEach {
            it.key.inventory.clear()
            MenuController.openMenu(it.key, ClassChoiceMenu(configs))
        }
    }

    fun checkClasses() {
        var result = true

        for(player in players) {
            if(player.value.playerClass == null) {
                result = false
            }
        }

        if(result) {
            launchGame()
        }
    }

    private fun launchGame() {
        status = GameStatus.PROCESS

        players.forEach {
            it.value.playerClass?.launchSetup(it.key)
        }
    }
}