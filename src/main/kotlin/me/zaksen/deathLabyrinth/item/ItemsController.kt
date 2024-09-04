package me.zaksen.deathLabyrinth.item

import me.zaksen.deathLabyrinth.entity.trader.TraderType
import me.zaksen.deathLabyrinth.item.settings.ItemSettings
import me.zaksen.deathLabyrinth.item.items.consume.*
import me.zaksen.deathLabyrinth.item.items.ingredient.*
import me.zaksen.deathLabyrinth.item.weapon.weapons.stuff.*
import me.zaksen.deathLabyrinth.item.weapon.weapons.sword.*
import me.zaksen.deathLabyrinth.util.ChatUtil
import me.zaksen.deathLabyrinth.util.asText
import org.bukkit.Material

// FIXME - Recode this crap, make items data-driven
object ItemsController {

    val itemsMap: MutableMap<String, CustomItem> = mutableMapOf()

    init {
        register("wooden_dagger", BaseDagger(
            "wooden_dagger",
            ItemSettings(Material.WOODEN_SWORD).customModel(1000).damage(3.0).attackSpeed(-1.6)
                .displayName(ChatUtil.format("<gray>Деревянный клинок</gray>"))
                .tradePrice(35)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("golden_dagger", BaseDagger(
            "golden_dagger",
            ItemSettings(Material.GOLDEN_SWORD).customModel(1000).damage(4.0).attackSpeed(-1.4)
                .displayName(ChatUtil.format("<gray>Золотой клинок</gray>"))
                .tradePrice(45)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("stone_dagger", BaseDagger(
            "stone_dagger",
            ItemSettings(Material.STONE_SWORD).customModel(1000).damage(6.0).attackSpeed(-1.6)
                .displayName(ChatUtil.format("<gray>Каменный клинок</gray>")).quality(ItemQuality.UNCOMMON)
                .tradePrice(75)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("iron_dagger", BaseDagger(
            "iron_dagger",
            ItemSettings(Material.IRON_SWORD).customModel(1000).damage(8.0).attackSpeed(-1.6)
                .displayName(ChatUtil.format("<gray>Железный клинок</gray>")).quality(ItemQuality.UNCOMMON)
                .tradePrice(80)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("diamond_dagger", BaseDagger(
            "diamond_dagger",
            ItemSettings(Material.DIAMOND_SWORD).customModel(1000).damage(10.0).attackSpeed(-1.6)
                .displayName(ChatUtil.format("<gray>Алмазный клинок</gray>")).quality(ItemQuality.UNCOMMON)
                .tradePrice(95)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("netherite_dagger", BaseDagger(
            "netherite_dagger",
            ItemSettings(Material.NETHERITE_SWORD).customModel(1000).damage(12.0).attackSpeed(-1.6)
                .displayName(ChatUtil.format("<gray>Незеритовый клинок</gray>")).quality(ItemQuality.RARE)
                .tradePrice(110)
                .addAviableTrader(TraderType.NORMAL)
        ))

        register("wooden_sword", BaseSword(
            "wooden_sword",
            ItemSettings(Material.WOODEN_SWORD).damage(5.0).attackSpeed(-2.3).range(1.0)
                .displayName(ChatUtil.format("<gray>Деревянный меч</gray>"))
                .tradePrice(35)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("golden_sword", BaseSword(
            "golden_sword",
            ItemSettings(Material.GOLDEN_SWORD).damage(7.0).attackSpeed(-2.1).range(1.0)
                .displayName(ChatUtil.format("<gray>Золотой меч</gray>"))
                .tradePrice(45)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("stone_sword", BaseSword(
            "stone_sword",
            ItemSettings(Material.STONE_SWORD).damage(10.0).attackSpeed(-2.3).range(1.0)
                .displayName(ChatUtil.format("<gray>Каменный меч</gray>")).quality(ItemQuality.UNCOMMON)
                .tradePrice(75)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("iron_sword", BaseSword(
            "iron_sword",
            ItemSettings(Material.IRON_SWORD).damage(13.0).attackSpeed(-2.3).range(1.0)
                .displayName(ChatUtil.format("<gray>Железный меч</gray>")).quality(ItemQuality.UNCOMMON)
                .tradePrice(80)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("diamond_sword", BaseSword(
            "diamond_sword",
            ItemSettings(Material.DIAMOND_SWORD).damage(16.0).attackSpeed(-2.3).range(1.0)
                .displayName(ChatUtil.format("<gray>Алмазный меч</gray>")).quality(ItemQuality.UNCOMMON)
                .tradePrice(95)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("netherite_sword", BaseSword(
            "netherite_sword",
            ItemSettings(Material.NETHERITE_SWORD).damage(20.0).attackSpeed(-2.3).range(1.0)
                .displayName(ChatUtil.format("<gray>Незеритовый меч</gray>")).quality(ItemQuality.RARE)
                .tradePrice(110)
                .addAviableTrader(TraderType.NORMAL)
        ))

        register("wooden_hammer", BaseHammer(
            "wooden_hammer",
            ItemSettings(Material.MACE).customModel(1000).damage(5.0).attackSpeed(-3.2).hitRange(0.5)
                .displayName(ChatUtil.format("<gray>Деревянный молот</gray>"))
                .lore(mutableListOf("Наносит урон в радиусе 0.5 блоков".asText()))
                .tradePrice(35)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("golden_hammer", BaseHammer(
            "golden_hammer",
            ItemSettings(Material.MACE).customModel(1001).damage(7.0).attackSpeed(-3.0).hitRange(0.5)
                .displayName(ChatUtil.format("<gray>Золотой молот</gray>"))
                .lore(mutableListOf("Наносит урон в радиусе 0.5 блоков".asText()))
                .tradePrice(45)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("stone_hammer", BaseHammer(
            "stone_hammer",
            ItemSettings(Material.MACE).customModel(1002).damage(10.0).attackSpeed(-3.2).hitRange(0.5)
                .displayName(ChatUtil.format("<gray>Каменный молот</gray>"))
                .lore(mutableListOf("Наносит урон в радиусе 0.5 блоков".asText()))
                .quality(ItemQuality.UNCOMMON)
                .tradePrice(75)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("iron_hammer", BaseHammer(
            "iron_hammer",
            ItemSettings(Material.MACE).customModel(1003).damage(13.0).attackSpeed(-3.2).hitRange(1.0)
                .displayName(ChatUtil.format("<gray>Железный молот</gray>"))
                .lore(mutableListOf("Наносит урон в радиусе 1 блока".asText()))
                .quality(ItemQuality.UNCOMMON)
                .tradePrice(80)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("diamond_hammer", BaseHammer(
            "diamond_hammer",
            ItemSettings(Material.MACE).customModel(1004).damage(16.0).attackSpeed(-3.2).hitRange(1.0)
                .displayName(ChatUtil.format("<gray>Алмазный молот</gray>"))
                .lore(mutableListOf("Наносит урон в радиусе 1 блока".asText()))
                .quality(ItemQuality.UNCOMMON)
                .tradePrice(95)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("netherite_hammer", BaseHammer(
            "netherite_hammer",
            ItemSettings(Material.MACE).customModel(1005).damage(20.0).attackSpeed(-3.2).hitRange(1.5)
                .displayName(ChatUtil.format("<gray>Незеритовый молот</gray>"))
                .lore(mutableListOf("Наносит урон в радиусе 1.5 блока".asText()))
                .quality(ItemQuality.RARE)
                .tradePrice(110)
                .addAviableTrader(TraderType.NORMAL)
        ))

        register("wooden_spear", BaseSpear(
            "wooden_spear",
            ItemSettings(Material.WOODEN_SWORD).customModel(1001).damage(5.0).attackSpeed(-3.0).range(2.5)
                .displayName(ChatUtil.format("<gray>Деревянное копьё</gray>"))
                .tradePrice(35)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("golden_spear", BaseSpear(
            "golden_spear",
            ItemSettings(Material.GOLDEN_SWORD).customModel(1001).damage(7.0).attackSpeed(-2.8).range(2.5)
                .displayName(ChatUtil.format("<gray>Золотое копьё</gray>"))
                .tradePrice(45)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("stone_spear", BaseSpear(
            "stone_dagger",
            ItemSettings(Material.STONE_SWORD).customModel(1001).damage(10.0).attackSpeed(-3.0).range(2.5)
                .displayName(ChatUtil.format("<gray>Каменное копьё</gray>")).quality(ItemQuality.UNCOMMON)
                .tradePrice(75)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("iron_spear", BaseSpear(
            "iron_spear",
            ItemSettings(Material.IRON_SWORD).customModel(1001).damage(13.0).attackSpeed(-3.0).range(2.5)
                .displayName(ChatUtil.format("<gray>Железное копьё</gray>")).quality(ItemQuality.UNCOMMON)
                .tradePrice(80)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("diamond_spear", BaseSpear(
            "diamond_spear",
            ItemSettings(Material.DIAMOND_SWORD).customModel(1001).damage(16.0).attackSpeed(-3.0).range(2.5)
                .displayName(ChatUtil.format("<gray>Алмазное копьё</gray>")).quality(ItemQuality.UNCOMMON)
                .tradePrice(95)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("netherite_spear", BaseSpear(
            "netherite_spear",
            ItemSettings(Material.NETHERITE_SWORD).customModel(1001).damage(20.0).attackSpeed(-3.0).range(2.5)
                .displayName(ChatUtil.format("<gray>Незеритовое копьё</gray>")).quality(ItemQuality.RARE)
                .tradePrice(110)
                .addAviableTrader(TraderType.NORMAL)
        ))


        register("shield", CustomItem("shield", ItemType.MISC, ItemSettings(Material.SHIELD).displayName("Щит".asText())))

        register("heal_stuff", HealStuff("heal_stuff"))
        register("frost_ball_stuff", FrostBallStuff("frost_ball_stuff"))
        register("big_frost_ball_stuff", BigFrostBallStuff("big_frost_ball_stuff"))
        register("fire_ball_stuff", FireBallStuff("fire_ball_stuff"))
        register("big_fire_ball_stuff", BigFireBallStuff("big_fire_ball_stuff"))
        register("big_heal_stuff", BigHealStuff("big_heal_stuff"))
        register("wither_ball_stuff", WitherBallStuff("wither_ball_stuff"))
        register("big_wither_ball_stuff", BigWitherBallStuff("big_wither_ball_stuff"))
        register("necromantic_stuff", NecromanticStuff("necromantic_stuff"))
        register("electric_stuff", ElectricStuff("electric_stuff"))
        register("laser_stuff", LaserStuff("laser_stuff"))

        register("heal_potion", HealPotion("heal_potion"))
        register("small_heal_potion", SmallHealPotion("small_heal_potion"))

        register("bone", Bone("bone"))
        register("flesh", Flesh("flesh"))
        register("gunpowder", Gunpowder("gunpowder"))
    }

    private fun register(id: String, item: CustomItem) {
        itemsMap[id] = item
    }

    fun get(id: String): CustomItem? {
        return itemsMap[id]
    }
}