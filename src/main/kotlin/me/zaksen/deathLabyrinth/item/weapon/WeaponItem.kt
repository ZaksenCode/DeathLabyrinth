package me.zaksen.deathLabyrinth.item.weapon

import me.zaksen.deathLabyrinth.item.CustomItem
import me.zaksen.deathLabyrinth.item.ItemType
import me.zaksen.deathLabyrinth.item.settings.ItemSettings
import me.zaksen.deathLabyrinth.keys.PluginKeys
import me.zaksen.deathLabyrinth.util.*
import net.kyori.adventure.key.Key
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

open class WeaponItem(private val weaponType: WeaponType, id: String, settings: ItemSettings): CustomItem(id, ItemType.WEAPON, settings) {

    fun getWeaponType(): WeaponType {
        return weaponType
    }

    override fun asItemStack(): ItemStack {
        val stack = ItemStack(settings.material)
            .customModel(settings.customModel())
            .name(settings.displayName())
            .loreLine(settings.quality().visualText.asText().font(Key.key("dl:icons")))
            .loreLine("<dark_purple>Тип:</dark_purple> <light_purple>${getWeaponType().displayName}</light_purple>".asText())
            .loreMap(settings.lore())

        if(settings.damage() > 0) {
            stack.loreLine("<green>Урон: ${settings.damage()}</green>".asText())
        }

        if(settings.abilityCooldown() > 0) {
            stack.loreLine("<green>Перезарядка: ${settings.abilityCooldown() / 1000.0} c.</green>".asText())
        }

        val meta = stack.itemMeta
        meta.persistentDataContainer.set(PluginKeys.customItemKey, PersistentDataType.STRING, id)
        meta.isUnbreakable = true
        stack.itemMeta = meta

        return stack
    }
}