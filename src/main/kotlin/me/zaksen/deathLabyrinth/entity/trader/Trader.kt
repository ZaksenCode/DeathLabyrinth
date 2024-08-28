package me.zaksen.deathLabyrinth.entity.trader

import me.zaksen.deathLabyrinth.trading.TradeOffer

interface Trader {
    fun updateOffers(offers: List<TradeOffer>)
    fun getTraderType(): TraderType
}