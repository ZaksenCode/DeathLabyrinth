package me.zaksen.deathLabyrinth.entity.text_display

import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.Display.TextDisplay
import net.minecraft.world.entity.EntityType
import org.bukkit.Location
import org.bukkit.craftbukkit.CraftWorld

class ArtifactsCardName(location: Location, artifact: Artifact): TextDisplay(EntityType.TEXT_DISPLAY, (location.world as CraftWorld).handle) {

    init {
        this.text = Component.literal(artifact.name)

        this.absRotateTo(location.yaw, 0f)
        this.setPos(location.x, location.y, location.z)
    }

}