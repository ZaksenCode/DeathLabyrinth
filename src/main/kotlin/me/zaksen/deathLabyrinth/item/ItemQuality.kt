package me.zaksen.deathLabyrinth.item

const val star = "\uE001"
const val grayStar = "\uE002"

enum class ItemQuality(val visualText: String) {
    COMMON("<white>$star$grayStar$grayStar$grayStar$grayStar</white>"),
    UNCOMMON("<white>$star$star$grayStar$grayStar$grayStar</white>"),
    RARE("<white>$star$star$star$grayStar$grayStar</white>"),
    EPIC("<white>$star$star$star$star$grayStar</white>"),
    LEGENDARY("<white>$star$star$star$star$grayStar</white>"),
    FANTASIC("<white></white>")
}