package me.zaksen.deathLabyrinth.item.weapon.weapons.sword

import me.zaksen.deathLabyrinth.item.settings.ItemSettings
import me.zaksen.deathLabyrinth.item.weapon.WeaponType
import me.zaksen.deathLabyrinth.item.weapon.weapons.SwordLike
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent

open class BaseHammer(id: String, settings: ItemSettings): SwordLike(WeaponType.HAMMER, id, settings) {

    override fun onHit(event: EntityDamageByEntityEvent) {
        val affectedEntities = event.entity.getNearbyEntities(settings.hitRange(), settings.hitRange(), settings.hitRange())

        for(entity in affectedEntities) {
            if(entity is LivingEntity && entity !is Player) {
                entity.damage(event.damage)
            }
        }
    }
}