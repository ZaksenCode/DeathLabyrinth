package me.zaksen.deathLabyrinth.artifacts

import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import me.zaksen.deathLabyrinth.classes.MageClass
import me.zaksen.deathLabyrinth.classes.PlayerClass
import me.zaksen.deathLabyrinth.classes.WarriorClass
import me.zaksen.deathLabyrinth.event.custom.game.RoomCompleteEvent
import me.zaksen.deathLabyrinth.util.asText
import me.zaksen.deathLabyrinth.util.customModel
import me.zaksen.deathLabyrinth.util.loreLine
import me.zaksen.deathLabyrinth.util.name
import org.bukkit.Material
import org.bukkit.event.entity.EntityRegainHealthEvent
import org.bukkit.inventory.ItemStack

class GreenHeart: Artifact("Зелёное сердце") {

    init {
        abilityContainer.add {
            if(it !is RoomCompleteEvent) return@add
            it.player.heal(6.0 * count, EntityRegainHealthEvent.RegainReason.REGEN)
        }
    }

    override fun asItemStack(): ItemStack {
        return ItemStack(Material.APPLE)
            .customModel(100)
            .name("<green>$name</green>".asText())
            .loreLine("<gray>Восстанавливает</gray> <gold>${6.0 * count}</gold> <gray>здоровья за каждую комнату</gray>".asText())
    }

    override fun usableFor(): Set<Class<out PlayerClass>> {
        return setOf(MageClass::class.java, WarriorClass::class.java)
    }
}