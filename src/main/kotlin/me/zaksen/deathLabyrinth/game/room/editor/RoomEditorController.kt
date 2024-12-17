package me.zaksen.deathLabyrinth.game.room.editor

import com.sk89q.worldedit.bukkit.BukkitWorld
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard
import com.sk89q.worldedit.extent.clipboard.io.BuiltInClipboardFormat
import com.sk89q.worldedit.function.operation.ForwardExtentCopy
import com.sk89q.worldedit.function.operation.Operations
import com.sk89q.worldedit.math.BlockVector3
import com.sk89q.worldedit.regions.CuboidRegion
import me.zaksen.deathLabyrinth.config.ConfigContainer
import me.zaksen.deathLabyrinth.config.loadConfig
import me.zaksen.deathLabyrinth.config.saveConfig
import me.zaksen.deathLabyrinth.game.room.RoomBuilder
import me.zaksen.deathLabyrinth.game.room.RoomController
import me.zaksen.deathLabyrinth.game.room.editor.operation.Operation
import me.zaksen.deathLabyrinth.game.room.editor.operation.rollback.RollbackResult
import me.zaksen.deathLabyrinth.game.room.editor.session.EditorSession
import me.zaksen.deathLabyrinth.game.room.editor.session.SessionsWrapper
import me.zaksen.deathLabyrinth.util.asTranslate
import org.bukkit.World
import org.bukkit.entity.Player
import java.io.File
import java.io.FileOutputStream

object RoomEditorController {

    private lateinit var configs: ConfigContainer
    private lateinit var directory: File
    private var sessionsWrapper: SessionsWrapper = SessionsWrapper()

    fun setup(container: ConfigContainer, directory: File) {
        this.configs = container
        this.directory = directory

        if(container.mainConfig().debug) {
            println("<DL:RE> - Room editor was setup!")
            val actualDirectory = File(directory, "sessions")

            if(!actualDirectory.exists()) {
                actualDirectory.mkdirs()
            }

            sessionsWrapper = loadConfig<SessionsWrapper>(actualDirectory, "sessions_data.yml")
        }
    }

    fun processSessionOperation(from: Player, operation: Operation) {
        val session = sessionsWrapper.sessions[from.uniqueId]

        if(session == null) {
            from.sendMessage("command.room_editor.no_session")
            return
        }

        session.processOperation(operation)
    }

    fun processSessionRollback(from: Player): RollbackResult {
        val session = sessionsWrapper.sessions[from.uniqueId] ?: return RollbackResult.NO_SESSION
        return session.rollbackLast()
    }

    fun getSession(from: Player): EditorSession? {
        return sessionsWrapper.sessions[from.uniqueId]
    }

    fun loadRoom(
        owner: Player,
        world: World,
        posX: Int,
        posY: Int,
        posZ: Int,
        name: String
    ) {
        if(!configs.mainConfig().debug) {
            owner.sendMessage("command.room_editor.new.not_in_debug".asTranslate())
            return
        }

        val oldSession = getSession(owner)

        if(oldSession != null) {
            owner.sendMessage("command.room_editor.new.need_stop_session".asTranslate())
            return
        }

        val roomEntry = RoomController.loadRoom(name)
        val toBuild = RoomBuilder.prepareRoom(roomEntry, world, posX, posY, posZ, 0)

        RoomBuilder.buildRoom(toBuild)

        val newSession = EditorSession(name, world, posX, posY, posZ)
        newSession.roomConfig = roomEntry.roomConfig
        sessionsWrapper.sessions[owner.uniqueId] = newSession
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
            val oldSession = getSession(owner)

            if(oldSession != null) {
                owner.sendMessage("command.room_editor.new.need_stop_session".asTranslate())
                return
            }

            val newSession = EditorSession(name, world, posX, posY, posZ)
            newSession.setSize(sizeX.toDouble(), sizeY.toDouble(), sizeZ.toDouble())
            sessionsWrapper.sessions[owner.uniqueId] = newSession

            owner.sendMessage("command.room_editor.new.start".asTranslate())
        } else {
            owner.sendMessage("command.room_editor.new.not_in_debug".asTranslate())
        }
    }

    fun tickSessions() {
        sessionsWrapper.sessions.forEach { it.value.draw() }
    }

    // TODO - Make saving for sessions + auto saving
    fun saveSessions() {
        saveConfig(File(directory, "sessions"), "sessions_data.yml", sessionsWrapper)
    }

    fun exportSession(from: Player) {
        val session = getSession(from)

        if(session == null) {
            from.sendMessage("command.room_editor.no_session")
            return
        }

        val directory = File(directory, "export")

        if(!directory.exists()) {
            directory.mkdirs()
        }

        saveConfig(directory, name = session.name + ".yml", session.roomConfig)

        val schemFile = File(directory, session.name + ".schem")

        val region = CuboidRegion(
            BlockVector3.at(session.x, session.y, session.z),
            BlockVector3.at(
                session.x + session.roomConfig.roomSize.x,
                session.y + session.roomConfig.roomSize.y,
                session.z + session.roomConfig.roomSize.z
            )
        )

        val clipboard = BlockArrayClipboard(region)

        val forwardExtentCopy = ForwardExtentCopy(
            BukkitWorld(session.world), region, clipboard, region.minimumPoint
        )

        // configure here
        Operations.complete(forwardExtentCopy)

        BuiltInClipboardFormat.FAST_V3.getWriter(FileOutputStream(schemFile)).use { writer ->
            writer.write(clipboard)
        }
    }

    fun stopSession(from: Player) {
        sessionsWrapper.sessions.remove(from.uniqueId)
    }
}