package me.zaksen.deathLabyrinth.config.data

import kotlinx.serialization.Serializable

@Serializable
enum class RoomType {
    NORMAL,
    SHOP,
    CHALLENGE,
    ELITE,
    BOILER,
    SKILL_FORGE,
    DOUBLE_ELITE,
    TREASURE,
    BOSS_ROOM,
    FINAL_BOSS_ROOM
}