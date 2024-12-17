package me.zaksen.deathLabyrinth.util.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bukkit.Bukkit
import org.bukkit.World

object WorldSerializer : KSerializer<World> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("World", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): World {
        return Bukkit.getWorld(decoder.decodeString())!!
    }

    override fun serialize(encoder: Encoder, value: World) {
        encoder.encodeString(value.name)
    }
}