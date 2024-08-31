package me.zaksen.deathLabyrinth.item.weapon.weapons.stuff

import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.item.ItemQuality
import me.zaksen.deathLabyrinth.item.settings.ItemSettings
import me.zaksen.deathLabyrinth.item.weapon.WeaponItem
import me.zaksen.deathLabyrinth.item.weapon.WeaponType
import me.zaksen.deathLabyrinth.util.ChatUtil
import me.zaksen.deathLabyrinth.util.asText
import me.zaksen.deathLabyrinth.util.particleLine
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.craftbukkit.entity.CraftLivingEntity
import org.bukkit.entity.LivingEntity
import org.bukkit.event.player.PlayerInteractEvent

class LaserStuff(id: String): WeaponItem(
    WeaponType.ATTACK_STUFF,
    id,
    ItemSettings(Material.STICK)
        .customModel(110)
        .displayName(ChatUtil.format("<gray>Лазерный посох</gray>"))
        .abilityCooldown(1000)
        .lore(mutableListOf(
            "<dark_purple>Выпускает лазер</dark_purple>".asText(),
            "<green>Урон: 12</green>".asText()
        )).quality(ItemQuality.UNCOMMON)
        .tradePrice(60)
)
{
    override fun onUse(event: PlayerInteractEvent) {
        val item = event.item!!

        val rayCastEntity = event.player.rayTraceEntities(64)

        if(rayCastEntity != null && rayCastEntity.hitEntity != null && checkAndUpdateCooldown(item)) {
            if(rayCastEntity.hitEntity!! !is LivingEntity) {
                return
            }
            EventManager.callPlayerSpellEntityDamageEvent(event.player, rayCastEntity.hitEntity as CraftLivingEntity, 12.0)

            event.player.eyeLocation.particleLine(Particle.WITCH, rayCastEntity.hitPosition.toLocation(event.player.world))
        }
    }
}