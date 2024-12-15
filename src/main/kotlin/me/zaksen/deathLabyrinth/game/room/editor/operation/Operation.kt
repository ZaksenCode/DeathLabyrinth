package me.zaksen.deathLabyrinth.game.room.editor.operation

import me.zaksen.deathLabyrinth.game.room.editor.session.EditorSession

interface Operation {
    fun process(session: EditorSession)
    fun rollback(session: EditorSession)
}