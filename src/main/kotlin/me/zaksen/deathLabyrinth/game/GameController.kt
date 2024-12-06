package me.zaksen.deathLabyrinth.game

import me.zaksen.deathLabyrinth.artifacts.ArtifactsController
import me.zaksen.deathLabyrinth.artifacts.api.ArtifactsStates
import me.zaksen.deathLabyrinth.config.ConfigContainer
import me.zaksen.deathLabyrinth.damage.DamageType
import me.zaksen.deathLabyrinth.data.PlayerData
import me.zaksen.deathLabyrinth.entity.difficulty.Scaleable
import me.zaksen.deathLabyrinth.entity.friendly.FriendlyEntity
import me.zaksen.deathLabyrinth.entity.room.RoomCycle
import me.zaksen.deathLabyrinth.entity.trader.Trader
import me.zaksen.deathLabyrinth.entity.trader.TraderType
import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.event.custom.game.PlayerBreakPotEvent
import me.zaksen.deathLabyrinth.game.hud.HudController
import me.zaksen.deathLabyrinth.game.pot.PotEntry
import me.zaksen.deathLabyrinth.game.room.Room
import me.zaksen.deathLabyrinth.game.room.RoomFloorController
import me.zaksen.deathLabyrinth.game.room.exit.RoomExitController
import me.zaksen.deathLabyrinth.item.ItemsController
import me.zaksen.deathLabyrinth.keys.PluginKeys
import me.zaksen.deathLabyrinth.keys.PluginKeys.maxHealthModifierKey
import me.zaksen.deathLabyrinth.keys.PluginKeys.speedModifierKey
import me.zaksen.deathLabyrinth.keys.PluginKeys.speedModifierShacklesKey
import me.zaksen.deathLabyrinth.menu.Menus
import me.zaksen.deathLabyrinth.trading.ItemOffer
import me.zaksen.deathLabyrinth.trading.TradeOffer
import me.zaksen.deathLabyrinth.util.*
import net.kyori.adventure.text.format.TextColor
import net.minecraft.world.entity.Entity
import org.bukkit.*
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Item
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.entity.EntityRegainHealthEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.Plugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
import java.util.*
import java.util.function.Consumer
import kotlin.concurrent.timer
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

// TODO - Decompose
object GameController {
    private lateinit var plugin: Plugin
    val players: MutableMap<Player, PlayerData> = mutableMapOf()

    private lateinit var configs: ConfigContainer
    private var status: GameStatus = GameStatus.WAITING

    private var startCooldownTask: BukkitTask? = null
    private var startCooldownTime: Short = 5

    private val hudController: HudController = HudController()

    private var random: Random = Random(System.currentTimeMillis())

    private lateinit var potLootList: WeightedRandomList<PotEntry>

    private val shieldRemovingTask: Timer = timer(period = 500) {
        players.forEach {
            countShieldRestoring(it.key)
        }
    }

    private fun countShieldRestoring(player: Player) {
        val toSet = player.persistentDataContainer.get(PluginKeys.playerAbsorptionAmountKey, PersistentDataType.INTEGER) ?: return
        val current = player.absorptionAmount

        val maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH) ?: return
        val settingAmount = maxHealth.baseValue * 0.05

        if(current > toSet) {
            val toRemove = (current - toSet).coerceAtMost(settingAmount)
            removePlayerShield(player, toRemove)
        } else {
            val toRestore = (toSet - current).coerceAtMost(settingAmount)
            addPlayerShield(player, toRestore)
        }
    }

    fun initPotLootList(list: WeightedRandomList<PotEntry>) {
        potLootList = list
    }

    fun getRandomPotLoot(): PotEntry {
        val entry = potLootList.random()!!
        return PotEntry(entry.stack.clone(), entry.isStackable)
    }

    fun getStatus(): GameStatus {
        return status
    }

    fun hasDeadPlayers(): Boolean {
        return getDeadPlayers().isNotEmpty()
    }

    fun getDeadPlayers(): Map<Player, PlayerData> {
        return players.filter { !it.value.isAlive }
    }

    fun revivePlayer(player: Player, reviewer: Player? = null) {
        val data = players[player]

        player.gameMode = GameMode.SURVIVAL

        if(data != null) {
            players[player]!!.isAlive = true
        }

        if(reviewer != null) {
            player.teleport(reviewer)
        }
    }

    fun reload() {
        val world = Bukkit.getWorld(configs.mainConfig().world)!!

        world.worldBorder.setCenter(configs.mainConfig().playerSpawnLocation.x, configs.mainConfig().playerSpawnLocation.z)
        world.worldBorder.size = 500.0

        players.forEach {
            it.value.stats.displayStats(it.key)
        }

        hudController.stopDrawingTask()
        hudController.clearDrawers()
        players.clear()
        ArtifactsController.despawnArtifacts()
        RoomExitController.despawnChoices()
        RoomFloorController.reload()
        TradeController.reload()
        ArtifactsStates.cache.clear()

        world.getEntitiesByClass(Item::class.java).forEach {
            it.remove()
        }

        status = GameStatus.WAITING
        random = Random(System.currentTimeMillis())
        Bukkit.getOnlinePlayers().forEach { join(it) }
    }

    fun setup(plugin: Plugin, configs: ConfigContainer) {
        this.plugin = plugin
        this.configs = configs
    }

    fun join(player: Player) {
        if(status == GameStatus.WAITING) {
            players[player] = PlayerData()
            hudController.addPlayerToDraw(player, players[player]!!)

            setupPlayer(player)
        } else {
            player.gameMode = GameMode.SPECTATOR

            // TODO - Make more soft
            Bukkit.getWorld(configs.mainConfig().world).let { world ->
                if(world == null) return
                player.teleport(configs.mainConfig().playerSpawnLocation.location(world))
            }

        }
    }

    private fun clearAttributeModifier(player: Player) {
        player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)?.removeModifier(speedModifierKey)
        player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)?.removeModifier(speedModifierShacklesKey)
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH)?.removeModifier(maxHealthModifierKey)
    }

    private fun setupPlayer(player: Player) {
        clearAttributeModifier(player)
        player.gameMode = GameMode.SURVIVAL

        player.updateMaxHealth(40.0)

        player.persistentDataContainer.set(PluginKeys.playerAbsorptionAmountKey, PersistentDataType.INTEGER, 0)
        player.persistentDataContainer.set(PluginKeys.playerAbsorptionDefaultAmountKey, PersistentDataType.INTEGER, 0)

        player.heal(40.0, EntityRegainHealthEvent.RegainReason.REGEN)
        player.saturation = 20.0f

        player.addPotionEffect(PotionEffect(
            PotionEffectType.SATURATION,
            -1,
            255,
            false,
            false,
            false
        ))

        // TODO - Make more soft
        Bukkit.getWorld(configs.mainConfig().world).let { world ->
            if(world == null) return
            player.teleport(configs.mainConfig().playerSpawnLocation.location(world))
        }

        player.inventory.clear()
        player.inventory.setItem(4, ItemStack(Material.PAPER).name(
            "item.not_ready.name".asTranslate().color(TextColor.color(240,128,128))
        ).customModel(200))

        setupInvButtons(player)
    }

    fun setupInvButtons(player: Player) {
        player.inventory.setItem(17, ItemStack(Material.PAPER).name(
            "ui.artifacts.name".asTranslate().color(TextColor.color(124,242,80))
        ).customModel(100))

        player.inventory.setItem(26, ItemStack(Material.PAPER).name(
            "ui.accessories.name".asTranslate().color(TextColor.color(124,242,80))
        ).customModel(100))
    }


    fun leave(player: Player) {
        // FIXME - Probably didn't remove player (not sure)
        players.remove(player)

        checkAlivePlayers()
        // TODO - Find a better way, not sure that will be working in multiplayer
        if(Bukkit.getOnlinePlayers().size == 1) {
            players.clear()
        }
    }

    fun toggleReadyState(player: Player) {
        val playerData = players[player]
        if(playerData != null) {
            playerData.isReady = !playerData.isReady

            if(playerData.isReady) {
                player.inventory.setItemInMainHand(ItemStack(Material.PAPER).customModel(201).name(
                    "item.ready.name".asTranslate().color(TextColor.color(50,205,50))
                ))
                startGameCooldown()
            } else {
                player.inventory.setItemInMainHand(ItemStack(Material.PAPER).customModel(200).name(
                    "item.not_ready.name".asTranslate().color(TextColor.color(240,128,128))
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
                        "ui.game.starting".asTranslate(startCooldownTime.toString().asText()).color(TextColor.color(50,205,50)).broadcastTitle()
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

            "ui.game.starting.stopped".asTranslate().color(TextColor.color(220,20,60)).broadcastTitle()
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

        hudController.initDrawingTask()

        "text.game.close_class_menu".asTranslate().color(TextColor.color(32,178,170)).broadcast()
        players.forEach {
            it.key.inventory.clear()
            Menus.classChoice(it.key)
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
            it.value.playerClass?.launchSetup(it.key, it.value)
        }

        TradeController.initTrades(players)

        // TODO - Add seeds
        RoomFloorController.startSubFloor(RoomExitController.getStartChoice(), 0)
    }

    fun endGameWin() {
        status = GameStatus.GAME_END

        "ui.game.win".asTranslate().color(TextColor.color(50,205,50)).broadcastTitle()

        reload()
    }

    private fun endGameLose() {
        status = GameStatus.GAME_END

        "ui.game.lose".asTranslate().color(TextColor.color(220,20,60)).broadcastTitle()

        reload()
    }

    fun processPlayerDeath(player: Player) {
        val playerData = players[player]

        player.gameMode = GameMode.SPECTATOR
        player.title("ui.game.death".asTranslate().color(TextColor.color(220,20,60)))

        if(playerData != null) {
            players[player]!!.isAlive = false

            playerData.stats.totalDeathTimes++
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
            TraderType.NORMAL -> {
                TradeController.getOffersSnap(players.size * Random.Default.nextInt(2, 3), traderType).forEach {
                    result.add(it)
                }

                val healPotion = ItemsController.get("heal_potion")!!

                result.add(ItemOffer(
                    players.size,
                    healPotion.settings.tradePriceStrategy().scale(healPotion.settings.tradePrice()),
                    healPotion.asItemStack()
                ))
            }

            TraderType.ALCHEMIST -> {

                TradeController.getOffersSnap(players.size * 3, traderType, false).forEach {
                    result.add(it)
                }
            }

            else -> {
                TradeController.getOffersSnap(players.size * 3, traderType).forEach {
                    result.add(it)
                }
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

    fun processPotBreaking(event: PlayerBreakPotEvent) {
        println("Try to spawn: ${event.output.stack}")
        event.decoratedPot.location.world.dropItemNaturally(event.decoratedPot.location, event.output.stack)

        val data = players[event.player] ?: return
        data.stats.totalPotsBreak++
    }

    fun processAnyEvent(event: Event) {
        players.forEach { player ->
            player.value.artifacts.forEach {
                it.processAnyEvent(event)
            }
            player.value.accessories.processAccessories(event, player.key)
        }
    }

    fun addPlayerShield(player: Player, amount: Double) {
        val maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH) ?: return
        val maxShieldAmount = maxHealth.value / 2.0
        maxHealth.defaultValue

        val newAmount = player.absorptionAmount + amount
        player.absorptionAmount = Math.clamp(newAmount, 0.0, maxShieldAmount)
    }

    fun removePlayerShield(player: Player, amount: Double) {
        val newAmount = 0.0.coerceAtLeast(player.absorptionAmount - amount)
        player.absorptionAmount = newAmount
    }

    fun makeExplode(
        player: Player? = null,
        pos: Location,
        range: Double,
        damage: Double,
        drawParticles: Boolean = true,
        playSound: Boolean = true,
        entityConsumer: Consumer<LivingEntity> = Consumer{},
        damageType: DamageType = DamageType.EXPLODE
    ) {
        val toDamage = pos.getNearbyEntities(range, range, range).filter {
            it is LivingEntity && it !is Player && it !is FriendlyEntity
        }

        toDamage.forEach {
            entityConsumer.accept(it as LivingEntity)

            if(player != null) {
                EventManager.callPlayerSpellEntityDamageEvent(player, it, damage, damageType = damageType)
            } else {
                EventManager.callSpellEntityDamageEvent(it, damage, damageType = damageType)
            }
        }

        if(drawParticles) {
            var phi = 0.0
            while (phi <= Math.PI) {
                var theta = 0.0
                while (theta <= 2 * Math.PI) {
                    val x = range * cos(theta) * sin(phi)
                    val y = range * cos(phi) + 1.5
                    val z = range * sin(theta) * sin(phi)

                    pos.add(x, y, z)
                    pos.world.spawnParticle(Particle.EXPLOSION, pos, 1, 0.0, 0.0, 0.0, 0.001)
                    pos.subtract(x, y, z)
                    theta += Math.PI / 8
                }
                phi += Math.PI / 4
            }
        }

        if(playSound) {
            pos.world.playSound(pos, Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 1.0f, 1.0f)
        }
    }

    fun checkChance(successChance: Int): Boolean {
        val testedValue = random.nextInt(0, 100 + 1)
        return testedValue <= successChance
    }

    fun addMoney(player: Player, amount: Int) {
        val data = players[player] ?: return
        data.money += amount
        players[player] = data

        data.stats.totalMoneyEarned += amount
    }

    fun removeMoney(player: Player, amount: Int) {
        val data = players[player] ?: return
        data.money -= amount
        players[player] = data

        data.stats.totalMoneySpend += amount
    }

    fun processEntitySpawn(room: Room, entity: Entity, requireKill: Boolean) {
        if (entity is net.minecraft.world.entity.LivingEntity) {
            (entity.bukkitEntity as LivingEntity).maximumNoDamageTicks = 1
            if(requireKill) {
                room.livingEntities.add(entity)
            }
        }

        room.world.tryAddEntity(entity)

        if(entity is Trader) {
            entity.updateOffers(generateTradeOffers(entity.getTraderType()))
        }

        if(entity is Scaleable) {
            entity.scale()
        }

        if(entity is RoomCycle) {
            room.otherEntities.add(entity)
        }
    }

    fun runTask(runnable: Runnable) {
        if(!plugin.isEnabled) return
        Bukkit.getScheduler().runTask(plugin, runnable)
    }

    fun runTaskLater(runnable: Runnable, delay: Long) {
        Bukkit.getScheduler().runTaskLater(plugin, runnable, delay)
    }
}