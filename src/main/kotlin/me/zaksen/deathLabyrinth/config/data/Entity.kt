package me.zaksen.deathLabyrinth.config.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Entity(
    @SerialName("entity_name")
    val entityName: String = "bone_wolf",
    @SerialName("require_kill")
    val requireKill: Boolean = true,
    @SerialName("spawn_position")
    val spawnPosition: Position = Position(0.0, 0.0, 0.0),
    @SerialName("object_type")
    val objectType: ObjectType = ObjectType.ENTITY
)