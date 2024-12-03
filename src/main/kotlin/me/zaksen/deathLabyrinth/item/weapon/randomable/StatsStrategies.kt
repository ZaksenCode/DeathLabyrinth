package me.zaksen.deathLabyrinth.item.weapon.randomable

import me.zaksen.deathLabyrinth.entity.difficulty.ScalingStrategy
import me.zaksen.deathLabyrinth.game.room.RoomController

enum class StatsStrategies(val strategy: ScalingStrategy) {
    DAMAGE(ScalingStrategy { base ->
        val damageLimit = base.toInt() / 4
        kotlin.random.Random.Default.nextInt(
            base.toInt() - damageLimit,
            // FIXME - Room controller now didn't operate this
            // base.toInt() + (damageLimit * (RoomController.actualRoomNumber + 1) / 2)
            base.toInt()
        ).toDouble()
    }),
    ATTACK_SPEED(ScalingStrategy { base ->
        val attackSpeedLimit = base.toInt() / 6
        kotlin.random.Random.Default.nextDouble(
            base - attackSpeedLimit,
            base + attackSpeedLimit
        )
    }),
}