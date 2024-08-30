package me.zaksen.deathLabyrinth.game.room.reward

@FunctionalInterface
interface RewardStategy {
    fun generate(): Int {
        return 0
    }
}