package me.zaksen.deathLabyrinth.game.room.editor

import me.zaksen.deathLabyrinth.config.ConfigContainer
import me.zaksen.deathLabyrinth.game.room.editor.operation.Operation
import me.zaksen.deathLabyrinth.game.room.editor.operation.rollback.RollbackResult
import me.zaksen.deathLabyrinth.game.room.editor.session.EditorSession
import me.zaksen.deathLabyrinth.util.asTranslate
import org.bukkit.World
import org.bukkit.entity.Player
import java.io.File
import java.util.*

object RoomEditorController {

    private lateinit var configs: ConfigContainer
    private lateinit var directory: File
    private var sessions: MutableMap<UUID, EditorSession> = mutableMapOf()

    fun setup(container: ConfigContainer, directory: File) {
        this.configs = container
        this.directory = directory

        if(container.mainConfig().debug) {
            println("<DL:RE> - Room editor was setup!")
            // TODO - Load old sessions, that wasn't closed

        }
    }

    fun processSessionOperation(from: Player, operation: Operation) {
        val session = sessions[from.uniqueId]

        if(session == null) {
            from.sendMessage("command.room_editor.no_session")
            return
        }

        session.processOperation(operation)
    }

    fun processSessionRollback(from: Player): RollbackResult {
        val session = sessions[from.uniqueId] ?: return RollbackResult.NO_SESSION
        return session.rollbackLast()
    }

    fun getSession(from: Player): EditorSession? {
        return sessions[from.uniqueId]
    }

    fun startNewRoom(
        owner: Player,
        world: World,
        posX: Int,
        posY: Int,
        posZ: Int,
        sizeX: Int,
        sizeY: Int,
        sizeZ: Int,
        name: String
    ) {
        if(configs.mainConfig().debug) {
            val oldSession = sessions[owner.uniqueId]

            if(oldSession != null) {
                owner.sendMessage("command.room_editor.new.need_stop_session".asTranslate())
                return
            }

            val newSession = EditorSession(name, world, posX, posY, posZ)
            newSession.setSize(sizeX.toDouble(), sizeY.toDouble(), sizeZ.toDouble())
            sessions[owner.uniqueId] = newSession

            owner.sendMessage("command.room_editor.new.start".asTranslate())
        } else {
            owner.sendMessage("command.room_editor.new.not_in_debug".asTranslate())
        }
    }

    fun tickSessions() {
        sessions.forEach { it.value.draw() }
    }

    // TODO - Make saving for sessions + auto saving
    fun saveSessions() {

    }
}