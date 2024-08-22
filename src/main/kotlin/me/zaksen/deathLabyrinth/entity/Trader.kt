package me.zaksen.deathLabyrinth.entity

import me.zaksen.deathLabyrinth.shop.TradeOffer

interface Trader {
    fun updateOffers(offers: List<TradeOffer>)
}