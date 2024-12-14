package me.zaksen.deathLabyrinth.entity.difficulty

import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.game.room.RoomFloorController

enum class ScalingStrategies(val strategy: ScalingStrategy) {
    DEFAULT(ScalingStrategy { base ->
        RoomFloorController.countDifficultyScale(base)
    }),
    BY_PLAYERS(ScalingStrategy { base ->
        DEFAULT.strategy.scale(base) * GameController.players.size
    }),
    BY_COMPLETED_BOSSES(ScalingStrategy { base ->
        RoomFloorController.countDifficultyFloorScale(base)
    }),
}