package me.zaksen.deathLabyrinth.config.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Entity(
    @SerialName("entity_name")
    val entityName: String,
    @SerialName("spawn_position")
    val spawnPosition: Position
)