package me.zaksen.deathLabyrinth.data

import me.zaksen.deathLabyrinth.event.custom.game.PlayerDamageEntityEvent
import me.zaksen.deathLabyrinth.event.custom.game.PlayerDamagedByEntityEvent
import me.zaksen.deathLabyrinth.event.custom.game.PlayerHealingEvent
import me.zaksen.deathLabyrinth.util.asText
import me.zaksen.deathLabyrinth.util.asTranslate
import net.kyori.adventure.text.format.TextColor
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player

data class PlayerStats(
    var totalDamage: Long = 0,
    var totalDamageReceived: Long = 0,

    var maxOneTimeDamageDealt: Long = 0,
    var maxOneTimeDamageTaken: Long = 0,

    var totalHealthHealedSelf: Long = 0,
    var totalHealthHealedOther: Long = 0,

    var totalEntityKilled: Long = 0,

    var totalCollectedArtifacts: Long = 0,

    var totalDeathTimes: Long = 0,

    var totalMoneyEarned: Long = 0,
    var totalMoneySpend: Long = 0,

    var totalPotsBreak: Long = 0,

    var totalMobsSummoned: Long = 0,

    var totalItemConsumed: Long = 0
) {
    fun processDamage(event: PlayerDamageEntityEvent) {
        val damage = event.damage.toLong()

        totalDamage += damage

        if(damage > maxOneTimeDamageDealt) {
            maxOneTimeDamageDealt = damage
        }
    }

    fun processReceivedDamage(event: PlayerDamagedByEntityEvent) {
        val damage = event.damage.toLong()

        totalDamageReceived += damage

        if(damage > maxOneTimeDamageTaken) {
            maxOneTimeDamageTaken = damage
        }
    }

    fun processHealing(event: PlayerHealingEvent) {
        val healAmount = event.amount.toLong()

        val maxHealth = event.entity.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.value
        val health = event.entity.health

        if(event.player.uniqueId == event.entity.uniqueId) {
            totalHealthHealedSelf += healAmount.coerceAtMost((maxHealth - health).toLong())
        } else {
            totalHealthHealedOther += healAmount.coerceAtMost((maxHealth - health).toLong())
        }
    }

    fun displayStats(player: Player) {
        player.sendMessage("stats.first.message".asTranslate().color(TextColor.color(129, 85, 201)))

        player.sendMessage("stats.total_damage_dealt.message".asTranslate(
            "$totalDamage".asText().color(TextColor.color(255,165,0))
        ).color(TextColor.color(201, 44, 81)))
        player.sendMessage("stats.total_damage_taken.message".asTranslate(
            "$totalDamageReceived".asText().color(TextColor.color(255,165,0))
        ).color(TextColor.color(201, 44, 81)))

        player.sendMessage("stats.max_onetime_damage_dealt.message".asTranslate(
            "$maxOneTimeDamageDealt".asText().color(TextColor.color(255,165,0))
        ).color(TextColor.color(209, 75, 107)))
        player.sendMessage("stats.max_onetime_damage_taken.message".asTranslate(
            "$maxOneTimeDamageTaken".asText().color(TextColor.color(255,165,0))
        ).color(TextColor.color(209, 75, 107)))

        player.sendMessage("stats.total_healing_self.message".asTranslate(
            "$totalHealthHealedSelf".asText().color(TextColor.color(255,165,0))
        ).color(TextColor.color(75, 209, 97)))
        player.sendMessage("stats.total_healing_other.message".asTranslate(
            "$totalHealthHealedOther".asText().color(TextColor.color(255,165,0))
        ).color(TextColor.color(75, 209, 97)))

        player.sendMessage("stats.total_entity_killed.message".asTranslate(
            "$totalEntityKilled".asText().color(TextColor.color(255,165,0))
        ).color(TextColor.color(166, 66, 131)))

        player.sendMessage("stats.total_collected_artifacts.message".asTranslate(
            "$totalCollectedArtifacts".asText().color(TextColor.color(255,165,0))
        ).color(TextColor.color(227, 217, 82)))

        player.sendMessage("stats.total_death_times.message".asTranslate(
            "$totalDeathTimes".asText().color(TextColor.color(255,165,0))
        ).color(TextColor.color(152, 82, 199)))

        player.sendMessage("stats.total_money_earned.message".asTranslate(
            "$totalMoneyEarned".asText().color(TextColor.color(255,165,0))
        ).color(TextColor.color(201, 227, 95)))
        player.sendMessage("stats.total_money_spend.message".asTranslate(
            "$totalMoneySpend".asText().color(TextColor.color(255,165,0))
        ).color(TextColor.color(201, 227, 95)))

        player.sendMessage("stats.total_pots_break.message".asTranslate(
            "$totalPotsBreak".asText().color(TextColor.color(255,165,0))
        ).color(TextColor.color(209, 146, 52)))

        player.sendMessage("stats.total_mobs_summoned.message".asTranslate(
            "$totalMobsSummoned".asText().color(TextColor.color(255,165,0))
        ).color(TextColor.color(87, 160, 212)))

        player.sendMessage("stats.total_item_consumed.message".asTranslate(
            "$totalItemConsumed".asText().color(TextColor.color(255,165,0))
        ).color(TextColor.color(80, 230, 215)))
    }
}