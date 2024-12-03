package me.zaksen.deathLabyrinth.entity.difficulty

import me.zaksen.deathLabyrinth.game.room.RoomController

enum class ScalingStrategies(val strategy: ScalingStrategy) {
    DEFAULT(ScalingStrategy { base ->

        // FIXME - Room controller now didn't operate this
        // (base * (RoomController.bossRoomCompleted + 1)) + (base * (0.1 * (RoomController.actualRoomNumber + 1)))
        base
    }),
    BY_PLAYERS(ScalingStrategy { base ->
        DEFAULT.strategy.scale(base) * org.bukkit.Bukkit.getOnlinePlayers().size
    }),
    BY_COMPLETED_BOSSES(ScalingStrategy { base ->
        // FIXME - Room controller now didn't operate this
        // (base * (RoomController.bossRoomCompleted + 1))
        base
    }),
}