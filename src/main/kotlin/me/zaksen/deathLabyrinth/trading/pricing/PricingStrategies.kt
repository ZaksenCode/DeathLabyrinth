package me.zaksen.deathLabyrinth.trading.pricing

import me.zaksen.deathLabyrinth.game.room.RoomController
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
            // FIXME - Room controller now didn't operate this
            // val result = (base * (1 + (RoomController.actualRoomNumber * 0.1))).toInt()
            val result = base
            val maxRange = result / 4
            return result + Random.Default.nextInt(-maxRange, maxRange)
        }
    }),
    MULTIPLY_BY_ROOM_COMPLIED(object: PricingStrategy{
        override fun scale(base: Int): Int {
            // FIXME - Room controller now didn't operate this
            // base * RoomController.actualRoomNumber
            val result = base
            val maxRange = result / 4
            return result + Random.Default.nextInt(-maxRange, maxRange)
        }
    }),
    MULTIPLY_BY_BOSS_COMPLIED(object: PricingStrategy{
        override fun scale(base: Int): Int {
            // FIXME - Room controller now didn't operate this
            // base + RoomController.bossRoomCompleted + 1
            val result = base
            val maxRange = result / 4
            return result + Random.Default.nextInt(-maxRange, maxRange)
        }
    })
}