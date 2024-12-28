package me.zaksen.deathLabyrinth.config.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Entity(
    @SerialName("entity_name")
    var entityName: String = "bone_wolf",
    @SerialName("require_kill")
    var requireKill: Boolean = true,
    @SerialName("spawn_position")
    var spawnPosition: Position = Position(0.0, 0.0, 0.0)
)