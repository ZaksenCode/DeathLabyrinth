package me.zaksen.deathLabyrinth.trading

import org.bukkit.entity.Player
import xyz.xenondevs.invui.item.ItemProvider

abstract class TradeOffer(
    var count: Int = 1,
    val price: Int = 10,
    val buy: Boolean = true
) {
    fun trade(player: Player) {
        if(buy) {
            buy(player)
        } else {
            sell(player)
        }
    }

    abstract fun buy(player: Player)
    abstract fun sell(player: Player)

    /**
     * @return ItemProvider from INVUI that will be displayed into trade menu
     */
    abstract fun displayItem(): ItemProvider
}