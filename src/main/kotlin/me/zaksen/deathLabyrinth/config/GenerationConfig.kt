package me.zaksen.deathLabyrinth.config

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.zaksen.deathLabyrinth.config.data.Position

@Serializable
data class GenerationConfig(
    @SerialName("room_limit")
    val roomLimit: Int = 32,
    @SerialName("first_room_entry")
    val firstRoomEntry: Position = Position("world", -18.0, 2.0, 3.0),
    @SerialName("custom_rooms")
    val customRooms: Map<Int, List<String>> = mapOf(
        Pair(4, listOf("SHOP")),
        Pair(8, listOf("SHOP")),
        Pair(12, listOf("SHOP")),
        Pair(16, listOf("SHOP")),
        Pair(20, listOf("BOSS")),
        Pair(24, listOf("SHOP")),
        Pair(28, listOf("SHOP")),
        Pair(32, listOf("SHOP")),
        Pair(36, listOf("SHOP")),
        Pair(40, listOf("FINAL_BOSS")),
    )
)
