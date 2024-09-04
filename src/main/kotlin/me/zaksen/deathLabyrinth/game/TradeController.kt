package me.zaksen.deathLabyrinth.game

import me.zaksen.deathLabyrinth.data.PlayerData
import me.zaksen.deathLabyrinth.entity.trader.TraderType
import me.zaksen.deathLabyrinth.item.CustomItem
import me.zaksen.deathLabyrinth.item.ItemsController
import me.zaksen.deathLabyrinth.item.weapon.WeaponItem
import me.zaksen.deathLabyrinth.trading.TradeOffer
import me.zaksen.deathLabyrinth.trading.pricing.PricingStrategies
import org.bukkit.entity.Player

object TradeController {

    private val availableItems = mutableSetOf<CustomItem>()

    fun initTrades(players: Map<Player, PlayerData>) {
        println(ItemsController.itemsMap.size)
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

    fun getOffersSpan(count: Int = 2, traderType: TraderType = TraderType.NORMAL): List<TradeOffer> {
        return getTradesSpan(count, traderType).map {
            TradeOffer(
                1,
                PricingStrategies.FIXED.strategy.scale(it.settings.tradePrice()),
                it.asItemStack()
            )
        }
    }

    fun getTradesSpan(count: Int = 2, traderType: TraderType = TraderType.NORMAL): MutableList<CustomItem> {
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

    // TODO - Make item quality valuable into random
    private fun getRandomItem(from: MutableList<CustomItem>): CustomItem {
        val toBuy = from.random()
        from.remove(toBuy)
        return toBuy
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
}