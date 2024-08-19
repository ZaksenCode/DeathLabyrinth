package me.zaksen.deathLabyrinth.item.weapon.weapons.sword

import me.zaksen.deathLabyrinth.item.settings.ItemSettings
import me.zaksen.deathLabyrinth.item.weapon.WeaponType
import me.zaksen.deathLabyrinth.item.weapon.weapons.SwordLike
import org.bukkit.entity.LivingEntity
import org.bukkit.event.entity.EntityDamageByEntityEvent

open class BaseHammer(id: String, settings: ItemSettings): SwordLike(WeaponType.HAMMER, id, settings) {

    // FIXME - Не наносит урон ближайшим мобам. (видимо даже не вызывает ивент)
    override fun onHit(event: EntityDamageByEntityEvent) {
        val affectedEntities = event.entity.getNearbyEntities(settings.hitRange(), settings.hitRange(), settings.hitRange())
        affectedEntities.remove(event.damager)

        for(entity in affectedEntities) {
            if(entity is LivingEntity) {
                entity.damage(settings.damage() + 0.5)
            }
        }
    }
}