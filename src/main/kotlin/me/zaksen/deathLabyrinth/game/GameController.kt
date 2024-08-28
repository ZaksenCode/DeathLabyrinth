package me.zaksen.deathLabyrinth.game

import me.zaksen.deathLabyrinth.config.ConfigContainer
import me.zaksen.deathLabyrinth.data.PlayerData
import me.zaksen.deathLabyrinth.entity.trader.TraderType
import me.zaksen.deathLabyrinth.game.room.RoomController
import me.zaksen.deathLabyrinth.menu.Menus
import me.zaksen.deathLabyrinth.trading.TradeOffer
import me.zaksen.deathLabyrinth.trading.pricing.PricingStrategy
import me.zaksen.deathLabyrinth.util.*
import me.zaksen.deathLabyrinth.util.ChatUtil.title
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityRegainHealthEvent
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

    fun reload() {
        players.clear()
        Bukkit.getOnlinePlayers().forEach { join(it) }

        RoomController.clearGeneration()

        status = GameStatus.WAITING
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
        player.gameMode = GameMode.ADVENTURE

        player.heal(20.0, EntityRegainHealthEvent.RegainReason.REGEN)
        player.saturation = 20.0f

        player.teleport(locationOf(configs.mainConfig().playerSpawnLocation))

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
                        startCooldownTime = 5
                    } else {
                        ChatUtil.broadcastTitle(configs.langConfig().gameStartingTitle, "", Pair("{time}", startCooldownTime.toString()))
                    }

                    startCooldownTime--
                }
            }.runTaskTimer(plugin, 0, 20)
        }
    }

    private fun stopGameCooldown() {
        if(!isPlayersReady() && status == GameStatus.PREPARE) {
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
            Menus.classChoice(it.key)
        }

        RoomController.startGeneration()
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

    fun endGameWin() {
        status = GameStatus.GAME_END

        ChatUtil.broadcast("<aqua>Вы выиграли!<aqua>")
        reload()
    }

    private fun endGameLose() {
        status = GameStatus.GAME_END

        ChatUtil.broadcast("<red>Игра окончена!<red>")
        reload()
    }

    fun processPlayerDeath(player: Player) {
        val playerData = players[player]

        player.gameMode = GameMode.SPECTATOR
        player.title("<red>Вы умерли!</red>")

        if(playerData != null) {
            players[player]!!.isAlive = false
        }

        checkAlivePlayers()
    }

    private fun checkAlivePlayers() {
        var result = true

        for(player in players) {
            if(player.value.isAlive) {
                result = false
            }
        }

        if(result) {
            endGameLose()
        }
    }

    fun generateTradeOffers(traderType: TraderType): List<TradeOffer> {
        val result: MutableList<TradeOffer> = mutableListOf()

        when(traderType) {
            TraderType.NORMAL ->  {
                TradeOffer(players.size, 10, ItemStack(Material.STONE_AXE), object: PricingStrategy {
                    override fun scale(base: Int): Int {
                        return base * ((RoomController.roomGenerated * 0.4).toInt() + 1)
                    }
                })
            }
        }

        return result
    }

    fun processEntityHit(entity: LivingEntity) {
        val maxHealth = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH) ?: return
        val scale = entity.health.toFloat() / maxHealth.baseValue.toFloat()

        entity.customName(entity.customName()?.color(
            TextColor.lerp(
                scale,
                TextColor.color(242, 81, 81),
                TextColor.color(124, 242, 81)
            )
        ))
    }
}