package me.zaksen.deathLabyrinth.artifacts.api

import me.zaksen.deathLabyrinth.artifacts.ability.Ability
import me.zaksen.deathLabyrinth.util.Cache
import java.util.*

object ArtifactsStates: Cache<UUID, Ability, Any>() {
    override val cache: MutableMap<UUID, MutableMap<Ability, Any>> = mutableMapOf()


}