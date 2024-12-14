package me.zaksen.deathLabyrinth.trading.pricing

import me.zaksen.deathLabyrinth.game.room.RoomFloorController
import kotlin.random.Random

enum class PricingStrategies(val strategy: PricingStrategy) {
    FIXED(object: PricingStrategy{
        override fun scale(base: Int): Int {
            val maxRange = base / 4
            return base + Random.Default.nextInt(-maxRange, maxRange)
        }
    }),
    DEFAULT(object: PricingStrategy{
        override fun scale(base: Int): Int {
            val result = RoomFloorController.countPrice(base)
            val maxRange = result / 4
            return result + Random.Default.nextInt(-maxRange, maxRange)
        }
    }),
    MULTIPLY_BY_ROOM_COMPLIED(object: PricingStrategy{
        override fun scale(base: Int): Int {
            val result = base * RoomFloorController.floor + RoomFloorController.subFloor
            val maxRange = result / 4
            return result + Random.Default.nextInt(-maxRange, maxRange)
        }
    })
}