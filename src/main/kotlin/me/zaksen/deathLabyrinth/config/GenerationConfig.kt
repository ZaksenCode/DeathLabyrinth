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
    @SerialName("shop_rooms")
    val shopRooms: Array<Int> = arrayOf(5, 15)
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GenerationConfig

        if (roomLimit != other.roomLimit) return false
        if (firstRoomEntry != other.firstRoomEntry) return false
        if (!shopRooms.contentEquals(other.shopRooms)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = roomLimit
        result = 31 * result + firstRoomEntry.hashCode()
        result = 31 * result + shopRooms.contentHashCode()
        return result
    }
}
