package me.zaksen.deathLabyrinth.entity.text_display

import me.zaksen.deathLabyrinth.game.room.exit.choice.Choice
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.Display.TextDisplay
import net.minecraft.world.entity.EntityType
import org.bukkit.Location
import org.bukkit.craftbukkit.CraftWorld

class ExitChoiceName(location: Location, choice: Choice): TextDisplay(EntityType.TEXT_DISPLAY, (location.world as CraftWorld).handle) {

    init {
        this.text = Component.translatable(PlainTextComponentSerializer.plainText().serialize(choice.name))

        this.absRotateTo(location.yaw, 0f)
        this.setPos(location.x, location.y, location.z)
    }

}