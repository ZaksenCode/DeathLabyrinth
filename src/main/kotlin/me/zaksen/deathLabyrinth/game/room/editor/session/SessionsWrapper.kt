package me.zaksen.deathLabyrinth.game.room.editor.session

import kotlinx.serialization.Serializable
import me.zaksen.deathLabyrinth.util.serialization.UUIDSerializer
import java.util.UUID

@Serializable
class SessionsWrapper {
    var sessions: MutableMap<@Serializable(UUIDSerializer::class) UUID, EditorSession> = mutableMapOf()
}