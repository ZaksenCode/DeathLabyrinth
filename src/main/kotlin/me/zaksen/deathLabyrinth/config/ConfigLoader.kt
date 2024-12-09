package me.zaksen.deathLabyrinth.config

import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.YamlConfiguration
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import me.zaksen.deathLabyrinth.game.room.logic.completion.AlwaysCompletion
import me.zaksen.deathLabyrinth.game.room.logic.completion.CompletionCheck
import me.zaksen.deathLabyrinth.game.room.logic.completion.EntityCompletionCheck
import me.zaksen.deathLabyrinth.game.room.logic.start.*
import me.zaksen.deathLabyrinth.game.room.logic.tags.*
import me.zaksen.deathLabyrinth.game.room.logic.tick.HeightMinLimit
import me.zaksen.deathLabyrinth.game.room.logic.tick.TickProcess
import java.io.File

val module = SerializersModule {
    polymorphic(CompletionCheck::class) {
        subclass(EntityCompletionCheck::class)
        subclass(AlwaysCompletion::class)
    }

    polymorphic(TickProcess::class) {
        subclass(HeightMinLimit::class)
    }

    polymorphic(StartProcess::class) {
        subclass(SpawnEntitiesProcess::class)
        subclass(SpawnChoiceContainer::class)
        subclass(SpawnArtifactsChain::class)
        subclass(SpawnNecromancer::class)
    }

    polymorphic(RoomTag::class) {
        subclass(StartRoomSpawnOffset::class)
        subclass(ChoiceContainerOffset::class)
        subclass(ArtifactsChainOffset::class)
        subclass(NecromancerOffset::class)
        subclass(EntitiesPool::class)
    }
}


val yaml by lazy {
    Yaml(
        configuration = YamlConfiguration(
            strictMode = false
        ),
        serializersModule = module
    )
}

fun loadFile(folder: File, fileName: String): File {
    return try {
        folder.mkdirs()
        val file = File(folder, fileName)
        if (!file.exists()) file.createNewFile()
        file
    } catch (e: Throwable) {
        throw RuntimeException(e)
    }
}

inline fun <reified T> loadConfig(configFile: File): T {
    if (configFile.length() == 0L) {
        val config = T::class.java.getDeclaredConstructor().newInstance()
        saveConfig(File(configFile.absolutePath.replace("/${configFile.name}", "")), configFile.name, config)
        return config
    }

    return yaml.decodeFromString<T>(configFile.readText())
}

inline fun <reified T> loadConfig(folder: File, configName: String): T {
    val file = loadFile(folder, configName)

    if (file.length() == 0L) {
        val config = T::class.java.getDeclaredConstructor().newInstance()
        saveConfig(folder, configName, config)
        return config
    }

    return yaml.decodeFromString<T>(file.readText())
}

inline fun <reified T> saveConfig(dataFolder: File, name: String, value: T) {
    val text = yaml.encodeToString<T>(value)
    loadFile(dataFolder, name).writeText(text)
}