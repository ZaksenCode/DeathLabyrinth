package me.zaksen.deathLabyrinth.game

import me.zaksen.deathLabyrinth.artifacts.ArtifactsController
import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import me.zaksen.deathLabyrinth.artifacts.api.ArtifactRarity
import me.zaksen.deathLabyrinth.data.PlayerData
import me.zaksen.deathLabyrinth.entity.trader.TraderType
import me.zaksen.deathLabyrinth.item.CustomItem
import me.zaksen.deathLabyrinth.item.ItemQuality
import me.zaksen.deathLabyrinth.item.ItemsController
import me.zaksen.deathLabyrinth.item.weapon.WeaponItem
import me.zaksen.deathLabyrinth.trading.ArtifactOffer
import me.zaksen.deathLabyrinth.trading.ItemOffer
import me.zaksen.deathLabyrinth.trading.TradeOffer
import me.zaksen.deathLabyrinth.trading.pricing.PricingStrategies
import me.zaksen.deathLabyrinth.util.WeightedRandomList
import org.bukkit.entity.Player

object TradeController {

    val rarityList = WeightedRandomList<ItemQuality>()
    private val availableItems = mutableSetOf<CustomItem>()

    init {
        rarityList.addEntry(ItemQuality.COMMON, 0.35)
        rarityList.addEntry(ItemQuality.UNCOMMON, 0.25)
        rarityList.addEntry(ItemQuality.RARE, 0.2)
        rarityList.addEntry(ItemQuality.EPIC, 0.1)
        rarityList.addEntry(ItemQuality.LEGENDARY, 0.08)
        rarityList.addEntry(ItemQuality.FANTASIC, 0.02)
    }

    fun initTrades(players: Map<Player, PlayerData>) {
        ItemsController.itemsMap.forEach {
            if(it.value.settings.aviableFromTraders().isNotEmpty()) {
                val item = it.value

                if(item is WeaponItem) {
                    if(isValidWeapon(item, players)) {
                        availableItems.add(it.value)
                    }
                } else {
                    availableItems.add(it.value)
                }
            }
        }
    }

    fun reload() {
        availableItems.clear()
    }

    fun getOffersSnap(count: Int = 2, traderType: TraderType = TraderType.NORMAL, isBuying: Boolean = true): List<TradeOffer> {
        return if(traderType == TraderType.ARTIFACT) {
            getArtifactsTradesSpan(count).map {
                ArtifactOffer(
                    1,
                    getArtifactPrice(it),
                    it
                )
            }
        } else {
            getTradesSpan(count, traderType).map {
                ItemOffer(
                    1,
                    it.settings.tradePriceStrategy().scale(it.settings.tradePrice()),
                    it.asItemStack(),
                    isBuying
                )
            }
        }
    }

    private fun getTradesSpan(count: Int = 2, traderType: TraderType = TraderType.NORMAL): MutableList<CustomItem> {
        val result = mutableListOf<CustomItem>()

        val validItems = availableItems.filter {
            it.settings.aviableFromTraders().contains(traderType)
        }.toMutableList()

        if(validItems.size > count) {
            for (i in 1..count) {
                result.add(getRandomItem(validItems))
            }
        }

        return result
    }

    private fun getArtifactsTradesSpan(count: Int = 2): MutableList<Artifact> {
        val result = mutableListOf<Artifact>()

        if(ArtifactsController.artifacts.size > count) {
            for (i in 1..count) {
                result.add(ArtifactsController.getRandomArtifact())
            }
        }

        return result
    }

    private fun getArtifactPrice(artifact: Artifact): Int {
        return when(artifact.rarity) {
            ArtifactRarity.COMMON -> PricingStrategies.DEFAULT.strategy.scale(100)
            ArtifactRarity.RARE -> PricingStrategies.DEFAULT.strategy.scale(400)
            ArtifactRarity.EPIC -> PricingStrategies.DEFAULT.strategy.scale(1100)
            ArtifactRarity.GODLY -> PricingStrategies.DEFAULT.strategy.scale(2000)
        }
    }

    private fun getRandomItem(from: MutableList<CustomItem>, rarity: ItemQuality = rarityList.random()!!): CustomItem {
        val toBuyMap = from.filter { it.settings.quality() == rarity }


        if(toBuyMap.isNotEmpty()) {
            val toBuy = toBuyMap.random()
            from.remove(toBuy)
            return toBuy
        }

        return ItemsController.get("small_heal_potion")!!
    }

    private fun isValidWeapon(item: CustomItem, players: Map<Player, PlayerData>): Boolean {
        players.forEach {
            val playerClass = it.value

            if(playerClass.playerClass == null) {
                return false
            }

            if(playerClass.playerClass!!.availableWeapons().contains((item as WeaponItem).getWeaponType())) {
                return true
            }
        }

        return false
    }

    fun processTrade(player: Player, offer: TradeOffer) {
        offer.trade(player)
    }
}