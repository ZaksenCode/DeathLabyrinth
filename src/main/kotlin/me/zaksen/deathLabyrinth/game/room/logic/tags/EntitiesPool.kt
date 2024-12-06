package me.zaksen.deathLabyrinth.game.room.logic.tags

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.zaksen.deathLabyrinth.config.data.Entity

@Serializable
data class EntitiesPool(
    @SerialName("room_entities")
    val roomEntities: List<List<Entity>> = listOf(
        listOf(
            Entity(),
            Entity("big_bone_wolf")
        )
    ),
): RoomTag