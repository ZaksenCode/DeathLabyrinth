package me.zaksen.deathLabyrinth.game.room

import kotlinx.serialization.Serializable

@Serializable
enum class RoomType {
    NORMAL,
    ELITE,
    DOUBLE_ELITE,
    SHOP,
    BOILER_ROOM,
    SKILL_FORGE,
    TREASURE,
    CHALLENGE,
    BOSS,
    FINAL_BOSS
}