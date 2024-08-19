package me.zaksen.deathLabyrinth.game

import me.zaksen.deathLabyrinth.config.MainConfig
import me.zaksen.deathLabyrinth.data.PlayerData
import me.zaksen.deathLabyrinth.menu.MenuController
import me.zaksen.deathLabyrinth.menu.custom.ClassChoiceMenu
import me.zaksen.deathLabyrinth.util.ChatUtil
import me.zaksen.deathLabyrinth.util.ChatUtil.title
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

    private lateinit var config: MainConfig
    private var status: GameStatus = GameStatus.WAITING

    private var startCooldownTask: BukkitTask? = null
    private var startCooldownTime: Short = 5

    fun getStatus(): GameStatus {
        return status
    }

    fun setup(plugin: Plugin, config: MainConfig) {
        this.plugin = plugin
        this.config = config
    }

    fun join(player: Player) {
        players[player] = PlayerData()

        setupPlayer(player)
    }

    private fun setupPlayer(player: Player) {
        player.teleport(
            Location(
                Bukkit.getWorld(config.playerSpawnLocation.world),
                config.playerSpawnLocation.x,
                config.playerSpawnLocation.y,
                config.playerSpawnLocation.z
            )
        )

        player.inventory.clear()

        player.inventory.setItem(4, ItemStack(Material.PAPER).name(ChatUtil.format("<red>Не готов!</red>")).customModel(200))
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
                    ChatUtil.format("<green>Готов!</green>"))
                )
                startGameCooldown()
            } else {
                player.inventory.setItemInMainHand(ItemStack(Material.PAPER).customModel(200).name(
                    ChatUtil.format("<red>Не готов!</red>"))
                )
                stopGameCooldown()
            }

            players[player] = playerData
        }
    }

    private fun startGameCooldown() {
        if(players.size >= config.minimalPlayers && isPlayersReady() && status == GameStatus.WAITING) {
            status = GameStatus.PREPARE

            startCooldownTask = object: BukkitRunnable() {
                override fun run() {
                    if(startCooldownTime <= 0) {
                        startGame()
                        cancel()
                        startCooldownTask = null
                    } else {
                        players.forEach {
                            it.key.title("<green>Начало через: {time}</green>", "", Pair("{time}", startCooldownTime.toString()))
                        }
                    }

                    startCooldownTime--
                }
            }.runTaskTimer(plugin, 0, 20)
        }
    }

    private fun stopGameCooldown() {
        if(players.size >= config.minimalPlayers && isPlayersReady() && status == GameStatus.PREPARE) {
            status = GameStatus.WAITING

            startCooldownTask?.cancel()
            startCooldownTask = null
            startCooldownTime = 5

            players.forEach {
                it.key.title("<red>Ожидание прервано!</red>")
            }
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

        players.forEach {
            it.key.inventory.clear()
            ChatUtil.broadcast("<aqua>Если вы закрыли меню выбора класса до его выбора, его можно открыть снова командой /class</aqua>")
            MenuController.openMenu(it.key, ClassChoiceMenu(config))
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