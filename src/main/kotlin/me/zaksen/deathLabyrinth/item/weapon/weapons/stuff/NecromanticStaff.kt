package me.zaksen.deathLabyrinth.item.weapon.weapons.stuff

import me.zaksen.deathLabyrinth.entity.friendly.skeleton.FriendlySkeletonArcherEntity
import me.zaksen.deathLabyrinth.entity.trader.TraderType
import me.zaksen.deathLabyrinth.event.EventManager
import me.zaksen.deathLabyrinth.item.ItemQuality
import me.zaksen.deathLabyrinth.item.settings.ItemSettings
import me.zaksen.deathLabyrinth.item.weapon.WeaponItem
import me.zaksen.deathLabyrinth.item.weapon.WeaponType
import me.zaksen.deathLabyrinth.util.asTranslate
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.event.player.PlayerInteractEvent

class NecromanticStaff(id: String): WeaponItem(
    WeaponType.MISC_STAFF,
    id,
    ItemSettings(Material.STICK)
        .customModel(108)
        .displayName("item.necromantic_staff.name".asTranslate().color(TextColor.color(128, 128, 128)))
        .abilityCooldown(60000)
        .lore(mutableListOf(
            "item.necromantic_staff.lore.0".asTranslate().color(TextColor.color(128, 0, 128)),
            "item.necromantic_staff.lore.1".asTranslate().color(TextColor.color(65,105,225))
        )).quality(ItemQuality.RARE)
        .tradePrice(90)
        .addAviableTrader(TraderType.NORMAL)
)
{
    override fun onUse(event: PlayerInteractEvent) {
        val item = event.item!!

        if(checkAndUpdateCooldown(item)) {
            val skeleton = FriendlySkeletonArcherEntity(event.player.location.add(2.0, 1.0, 1.0))
            EventManager.callPlayerSummonFriendlyEntityEvent(event.player, skeleton)
            val skeletonTwo = FriendlySkeletonArcherEntity(event.player.location.add(-2.0, 1.0, -1.0))
            EventManager.callPlayerSummonFriendlyEntityEvent(event.player, skeletonTwo)
        }
    }
}