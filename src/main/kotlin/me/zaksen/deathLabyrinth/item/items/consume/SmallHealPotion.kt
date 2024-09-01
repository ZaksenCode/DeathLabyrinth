package me.zaksen.deathLabyrinth.item.items.consume

import me.zaksen.deathLabyrinth.entity.trader.TraderType
import me.zaksen.deathLabyrinth.item.CustomItem
import me.zaksen.deathLabyrinth.item.ItemType
import me.zaksen.deathLabyrinth.item.settings.ItemSettings
import me.zaksen.deathLabyrinth.util.asText
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.event.entity.EntityRegainHealthEvent
import org.bukkit.event.player.PlayerItemConsumeEvent

class SmallHealPotion(id: String): CustomItem(id, ItemType.CONSUMABLE,
    ItemSettings(Material.POTION)
        .customModel(101)
        .displayName("<color:#f54290>Зелье исцеления".asText())
        .lore(mutableListOf("Восстанавливает 25% от максимального здоровья".asText()))
        .tradePrice(25)
        .addAviableTrader(TraderType.NORMAL)
) {

    override fun onConsume(event: PlayerItemConsumeEvent) {
        val player =  event.player
        val toRegen = player.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.value * 0.25
        player.heal(toRegen, EntityRegainHealthEvent.RegainReason.REGEN)
    }
}