package me.zaksen.deathLabyrinth.item.settings

import me.zaksen.deathLabyrinth.entity.trader.TraderType
import me.zaksen.deathLabyrinth.item.ItemQuality
import me.zaksen.deathLabyrinth.trading.pricing.PricingStrategies
import me.zaksen.deathLabyrinth.trading.pricing.PricingStrategy
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

open class ItemSettings(val material: Material) {

    private var customModelData: Int = 0
    private var displayName: Component = Component.empty()
    private var lore: MutableList<Component> = mutableListOf()
    private var abilityCooldown: Int = 0
    private var damage: Double = 0.0
    private var attackSpeed: Double = 0.0
    private var range: Double = 0.0
    private var hitRange: Double = 0.0
    private var quality: ItemQuality = ItemQuality.COMMON

    private var defence: Double = 0.0
    private var thoroughness: Double = 0.0
    private var knockbackResistance: Double = 0.0

    private var tradePrice: Int = 0
    private var tradeItems: MutableSet<ItemStack> = mutableSetOf()
    private var tradePriceStrategy: PricingStrategy = PricingStrategies.DEFAULT.strategy
    private var aviableFromTraders: MutableSet<TraderType> = mutableSetOf()

    fun customModel(model: Int): ItemSettings {
        this.customModelData = model
        return this
    }

    fun displayName(name: Component): ItemSettings {
        this.displayName = name
        return this
    }

    fun lore(lore: MutableList<Component>): ItemSettings {
        this.lore = lore
        return this
    }

    fun loreLine(lore: Component): ItemSettings {
        this.lore.add(lore)
        return this
    }

    fun abilityCooldown(cooldown: Int): ItemSettings {
        this.abilityCooldown = cooldown
        return this
    }

    fun damage(damage: Double): ItemSettings {
        this.damage = damage
        return this
    }

    fun attackSpeed(attackSpeed: Double): ItemSettings {
        this.attackSpeed = attackSpeed
        return this
    }

    fun range(range: Double): ItemSettings {
        this.range = range
        return this
    }

    fun hitRange(hitRange: Double): ItemSettings {
        this.hitRange = hitRange
        return this
    }

    fun quality(quality: ItemQuality): ItemSettings {
        this.quality = quality
        return this
    }

    fun tradePrice(tradePrice: Int): ItemSettings {
        this.tradePrice = tradePrice
        return this
    }

    fun tradeItems(tradeItems: MutableSet<ItemStack>): ItemSettings {
        this.tradeItems = tradeItems
        return this
    }

    fun addTradeItem(item: ItemStack): ItemSettings {
        this.tradeItems.add(item)
        return this
    }

    fun tradePriceStrategy(pricingStrategy: PricingStrategy): ItemSettings {
        this.tradePriceStrategy = pricingStrategy
        return this
    }

    fun aviableFromTraders(traderTypes: MutableSet<TraderType>): ItemSettings {
        this.aviableFromTraders = traderTypes
        return this
    }

    fun addAviableTrader(traderType: TraderType): ItemSettings {
        this.aviableFromTraders.add(traderType)
        return this
    }

    fun defence(value: Double): ItemSettings {
        this.defence = value
        return this
    }

    fun thoroughness(value: Double): ItemSettings {
        this.thoroughness = value
        return this
    }

    fun knockbackResistance(value: Double): ItemSettings {
        this.knockbackResistance = value
        return this
    }

    // Getters

    fun customModel(): Int {
        return customModelData
    }

    fun displayName(): Component {
        return displayName
    }

    fun lore(): MutableList<Component> {
        return lore
    }

    fun abilityCooldown(): Int {
        return abilityCooldown
    }

    fun damage(): Double {
        return damage
    }

    fun attackSpeed(): Double {
        return attackSpeed
    }

    fun range(): Double {
        return range
    }

    fun hitRange(): Double {
        return hitRange
    }

    fun quality(): ItemQuality {
        return quality
    }

    fun tradePrice(): Int {
        return tradePrice
    }

    fun tradeItems(): MutableSet<ItemStack> {
        return tradeItems
    }

    fun tradePriceStrategy(): PricingStrategy {
        return tradePriceStrategy
    }

    fun aviableFromTraders(): MutableSet<TraderType> {
        return aviableFromTraders
    }

    fun defence(): Double {
        return defence
    }

    fun thoroughness(): Double {
        return thoroughness
    }

    fun knockbackResistance(): Double {
        return knockbackResistance
    }
}