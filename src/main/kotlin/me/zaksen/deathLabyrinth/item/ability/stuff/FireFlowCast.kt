import me.zaksen.deathLabyrinth.damage.DamageType
import me.zaksen.deathLabyrinth.entity.friendly.FriendlyEntity
import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.event.item.ItemUseEvent
import me.zaksen.deathLabyrinth.item.ability.ItemAbility
import me.zaksen.deathLabyrinth.util.particleLine
import net.kyori.adventure.text.Component
import org.bukkit.Particle
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.Event

class FireFlowCast: ItemAbility(
    Component.translatable("ability.fire_flow_cast.name"),
    Component.translatable("ability.fire_flow_cast.description"),
    2.0,
    0.75
) {
    override fun invoke(event: Event) {
        if(event !is ItemUseEvent) return

        val stack = event.stack!!
        val item = event.item!!

        if(item.checkCooldown(stack)) {
            val shotDir = event.player.location.direction.multiply(2).normalize().multiply(7)
            event.player.eyeLocation.add(event.player.location.direction.multiply(2).normalize()).particleLine(Particle.FLAME, event.player.eyeLocation.add(shotDir), 0.35, 0.35)

            val rayCast = event.player.rayTraceEntities(7)

            if(rayCast == null || rayCast.hitEntity == null) {
                return
            }

            val rayCastEntity = rayCast.hitEntity
            val affectedEntities = rayCastEntity?.getNearbyEntities(0.75, 0.75, 0.75) ?: return

            for(entity in affectedEntities) {
                if(entity is LivingEntity && entity !is Player && entity !is FriendlyEntity) {
                    entity.fireTicks = 60
                    EventManager.callPlayerSpellEntityDamageEvent(event.player, entity, 2.0, DamageType.FIRE)
                }
            }

            if(rayCastEntity is LivingEntity) {
                rayCastEntity.fireTicks = 60
                EventManager.callPlayerSpellEntityDamageEvent(event.player, rayCastEntity, 2.0, DamageType.FIRE)
            }
        }
    }
}