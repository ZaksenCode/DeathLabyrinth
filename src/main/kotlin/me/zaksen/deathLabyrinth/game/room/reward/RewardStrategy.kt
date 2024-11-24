package me.zaksen.deathLabyrinth.game.room.reward

@FunctionalInterface
interface RewardStrategy {
    fun generate(): Int {
        return 0
    }
}