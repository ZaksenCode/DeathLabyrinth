package me.zaksen.deathLabyrinth.item.items.consume

import me.zaksen.deathLabyrinth.item.CustomItem
import me.zaksen.deathLabyrinth.item.ItemQuality
import me.zaksen.deathLabyrinth.item.ItemType
import me.zaksen.deathLabyrinth.item.settings.ItemSettings
import me.zaksen.deathLabyrinth.util.asText
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.event.entity.EntityRegainHealthEvent
import org.bukkit.event.player.PlayerItemConsumeEvent

class HealPotion(id: String): CustomItem(id, ItemType.CONSUMABLE,
    ItemSettings(Material.POTION)
        .customModel(100)
        .displayName("<color:#f54290>Зелье исцеления".asText())
        .lore(mutableListOf("Восстанавливает 60% от максимального здоровья".asText()))
        .quality(ItemQuality.UNCOMMON)
        .tradePrice(40)
) {

    override fun onConsume(event: PlayerItemConsumeEvent) {
        val player =  event.player
        val toRegen = player.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.value * 0.6
        player.heal(toRegen, EntityRegainHealthEvent.RegainReason.REGEN)
    }
}