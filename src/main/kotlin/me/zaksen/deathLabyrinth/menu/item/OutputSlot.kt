package me.zaksen.deathLabyrinth.menu.item

import me.zaksen.deathLabyrinth.item.CustomItem
import me.zaksen.deathLabyrinth.item.ability.ItemAbilityManager
import me.zaksen.deathLabyrinth.item.weapon.weapons.updateWeaponStats
import me.zaksen.deathLabyrinth.keys.PluginKeys
import me.zaksen.deathLabyrinth.util.*
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.builder.addLoreLines
import xyz.xenondevs.invui.item.builder.setDisplayName
import xyz.xenondevs.invui.item.impl.AbstractItem

class OutputSlot(val firstSlot: SlotItem, val secondSlot: SlotItem): AbstractItem() {
    private var hasItem = false
    private var isRecipeRight = false
    private var slotItem: ItemStack = ItemStack.of(Material.GRAY_STAINED_GLASS_PANE)

    override fun getItemProvider(): ItemProvider {
        return if(isRecipeRight) {
            if(slotItem.lore() != null) {
                ItemBuilder(slotItem).setLore(slotItem.lore()!!.map { it.toWrapper() })
            } else {
                ItemBuilder(slotItem)
            }
        } else {
            ItemBuilder(Material.BARRIER).setDisplayName("text.blacksmith.wrong_recipe.name".asTranslate().color(TextColor.color(240,128,128)))
                .addLoreLines("text.blacksmith.wrong_recipe.description".asTranslate().color(TextColor.color(208,96,96)))
        }
    }

    override fun handleClick(click: ClickType, player: Player, event: InventoryClickEvent) {
        if(hasItem) {
            if(player.itemOnCursor.type == Material.AIR) {
                val temp = slotItem.clone()

                slotItem = ItemStack.of(Material.GRAY_STAINED_GLASS_PANE)
                player.setItemOnCursor(temp)

                firstSlot.clearItem()
                secondSlot.clearItem()

                hasItem = false
            }
        } else {
            if(player.itemOnCursor.type != Material.AIR) {
                slotItem = player.itemOnCursor.clone()
                player.setItemOnCursor(ItemStack.of(Material.AIR))
                hasItem = true
            }
        }
        notifyWindows()
    }

    fun setItem(stack: ItemStack) {
        slotItem = stack
        hasItem = true
        notifyWindows()
    }

    fun tryToMixItems() {
        val sourceItem = firstSlot.getCustomItem()
        val inputItem = secondSlot.getCustomItem()

        if(sourceItem == null || inputItem == null) return
        if(sourceItem.type != inputItem.type) return

        mixItems(firstSlot.slotItem, secondSlot.slotItem, sourceItem, inputItem)
    }

    fun clearRecipe() {
        hasItem = false
        isRecipeRight = false
        slotItem = ItemStack.of(Material.GRAY_STAINED_GLASS_PANE)
        notifyWindows()
    }

    fun mixItems(source: ItemStack, input: ItemStack, sourceCustom: CustomItem, inputCustom: CustomItem) {
        val result = source.clone()
        if(!source.hasItemMeta() || !input.hasItemMeta()) return

        val sourceAbilities = getStackAbilities(source).toMutableSet()
        val inputAbilities = getStackAbilities(input).toMutableSet()

        sourceAbilities.forEach {
            val ability = ItemAbilityManager.abilityMap[it] ?: return@forEach

            ability.getSynergies().forEach { synergy ->
                if(inputAbilities.contains(synergy.with)) {

                    sourceAbilities.remove(it)
                    inputAbilities.remove(synergy.with)

                    sourceAbilities.add(synergy.output)

                    val newAbility = ItemAbilityManager.abilityMap[synergy.output]
                    newAbility?.getConflictAbilities()?.forEach { confAbility ->
                        sourceAbilities.remove(confAbility)
                        inputAbilities.remove(confAbility)
                    }
                }
            }

            ability.getConflictAbilities().forEach { confAbility ->
                sourceAbilities.remove(confAbility)
                inputAbilities.remove(confAbility)
            }
        }

        inputAbilities.forEach {
            val ability = ItemAbilityManager.abilityMap[it] ?: return@forEach

            ability.getConflictAbilities().forEach { confAbility ->
                sourceAbilities.remove(confAbility)
                inputAbilities.remove(confAbility)
            }
        }

        sourceAbilities.addAll(inputAbilities)

        val meta = result.itemMeta
        meta.persistentDataContainer.set(PluginKeys.customItemAbilitiesKey, PersistentDataType.STRING, sourceAbilities.string())

        if(meta.hasLore()) {
            // TODO - Need to find a better way to removing old abilities lore
            meta.lore(meta.lore()!!.filter {
                val comp = it.toString()
                return@filter !comp.contains("abilit") && !comp.contains("cooldown")
            })
        }
        val sourceMeta = source.itemMeta

        if(source.itemMeta.persistentDataContainer.has(PluginKeys.customItemCooldownTimeKey) &&
            input.itemMeta.persistentDataContainer.has(PluginKeys.customItemCooldownTimeKey)) {
            val sourceCooldownTime = sourceMeta.persistentDataContainer.get(PluginKeys.customItemCooldownTimeKey, PersistentDataType.INTEGER)!!
            meta.persistentDataContainer.set(PluginKeys.customItemCooldownTimeKey, PersistentDataType.INTEGER, sourceCooldownTime +
                    (input.persistentDataContainer.get(PluginKeys.customItemCooldownTimeKey, PersistentDataType.INTEGER)!! * 0.5).toInt())
        }

        if(source.itemMeta.persistentDataContainer.has(PluginKeys.customItemDamageKey)&&
            input.itemMeta.persistentDataContainer.has(PluginKeys.customItemDamageKey)) {
            val sourceDamage = sourceMeta.persistentDataContainer.get(PluginKeys.customItemDamageKey, PersistentDataType.DOUBLE)!!
            meta.persistentDataContainer.set(PluginKeys.customItemDamageKey, PersistentDataType.DOUBLE, sourceDamage +
                    (input.persistentDataContainer.get(PluginKeys.customItemDamageKey, PersistentDataType.DOUBLE)!! * 0.3))
        }

        if(source.itemMeta.persistentDataContainer.has(PluginKeys.customItemAttackSpeedKey)&&
            input.itemMeta.persistentDataContainer.has(PluginKeys.customItemAttackSpeedKey)) {
            val sourceAttackSpeed = sourceMeta.persistentDataContainer.get(PluginKeys.customItemAttackSpeedKey, PersistentDataType.DOUBLE)!!
            meta.persistentDataContainer.set(PluginKeys.customItemAttackSpeedKey, PersistentDataType.DOUBLE, sourceAttackSpeed +
                    (input.persistentDataContainer.get(PluginKeys.customItemAttackSpeedKey, PersistentDataType.DOUBLE)!! * 0.05))
        }

        result.itemMeta = meta
        result.updateWeaponStats()

        if(sourceAbilities.isNotEmpty()) result.loreLine(
            Component.translatable("text.item.abilities").decoration(TextDecoration.ITALIC, false).color(
            TextColor.color(
                222, 146, 47
            )
        ))

        sourceAbilities.forEach {
            val ability = ItemAbilityManager.abilityMap[it] ?: return@forEach
            ability.addAbilityDescription(result)
        }

        if(result.itemMeta.persistentDataContainer.has(PluginKeys.customItemCooldownTimeKey)) {
            result.loreLine("item.lore.cooldown".asTranslate(
                (result.itemMeta.persistentDataContainer.get(PluginKeys.customItemCooldownTimeKey, PersistentDataType.INTEGER)!! / 1000.0).toString().asText())
                .color(TextColor.color(65,105,225))
            )
        }

        slotItem = result
        hasItem = true
        isRecipeRight = true
        notifyWindows()
    }

    private fun getStackAbilities(stack: ItemStack): List<String> {
        if(stack.itemMeta.persistentDataContainer.has(PluginKeys.customItemAbilitiesKey)) {
            return stack.itemMeta.persistentDataContainer.get(PluginKeys.customItemAbilitiesKey, PersistentDataType.STRING)!!.stringList()
        } else {
            val meta = stack.itemMeta
            meta.persistentDataContainer.set(PluginKeys.customItemAbilitiesKey, PersistentDataType.STRING, "")
            stack.itemMeta = meta
            return listOf()
        }
    }
}