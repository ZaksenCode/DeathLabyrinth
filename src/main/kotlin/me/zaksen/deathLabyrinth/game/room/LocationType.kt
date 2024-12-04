package me.zaksen.deathLabyrinth.game.room

enum class LocationType(val floor: Int) {
    SHAFT(1);

    companion object {
        fun getLocationFor(floor: Int): LocationType? {
            return entries.filter { it.floor == floor }.randomOrNull()
        }
    }
}