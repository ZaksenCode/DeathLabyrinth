package me.zaksen.deathLabyrinth.item

import me.zaksen.deathLabyrinth.entity.trader.TraderType
import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.item.gear.armor.ArmorItem
import me.zaksen.deathLabyrinth.item.settings.ItemSettings
import me.zaksen.deathLabyrinth.item.items.consume.*
import me.zaksen.deathLabyrinth.item.items.ingredient.*
import me.zaksen.deathLabyrinth.item.weapon.weapons.stuff.*
import me.zaksen.deathLabyrinth.item.weapon.weapons.sword.*
import me.zaksen.deathLabyrinth.util.WeightedRandomList
import me.zaksen.deathLabyrinth.util.asText
import me.zaksen.deathLabyrinth.util.asTranslate
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

// FIXME - Recode this crap, make items data-driven
object ItemsController {

    val itemsMap: MutableMap<String, CustomItem> = mutableMapOf()

    init {
        register("wooden_dagger", BaseDagger(
            "wooden_dagger",
            ItemSettings(Material.WOODEN_SWORD).customModel(1000).damage(3.0).attackSpeed(-1.6)
                .displayName("item.wooden_dagger.name".asTranslate().color(TextColor.color(128, 128, 128)))
                .tradePrice(35)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("golden_dagger", BaseDagger(
            "golden_dagger",
            ItemSettings(Material.GOLDEN_SWORD).customModel(1000).damage(4.0).attackSpeed(-1.4)
                .displayName("item.golden_dagger.name".asTranslate().color(TextColor.color(128, 128, 128)))
                .tradePrice(45)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("stone_dagger", BaseDagger(
            "stone_dagger",
            ItemSettings(Material.STONE_SWORD).customModel(1000).damage(6.0).attackSpeed(-1.6)
                .displayName("item.stone_dagger.name".asTranslate().color(TextColor.color(128, 128, 128))).quality(ItemQuality.UNCOMMON)
                .tradePrice(75)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("iron_dagger", BaseDagger(
            "iron_dagger",
            ItemSettings(Material.IRON_SWORD).customModel(1000).damage(8.0).attackSpeed(-1.6)
                .displayName("item.iron_dagger.name".asTranslate().color(TextColor.color(128, 128, 128))).quality(ItemQuality.UNCOMMON)
                .tradePrice(85)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("diamond_dagger", BaseDagger(
            "diamond_dagger",
            ItemSettings(Material.DIAMOND_SWORD).customModel(1000).damage(10.0).attackSpeed(-1.6)
                .displayName("item.diamond_dagger.name".asTranslate().color(TextColor.color(128, 128, 128))).quality(ItemQuality.UNCOMMON)
                .tradePrice(110)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("netherite_dagger", BaseDagger(
            "netherite_dagger",
            ItemSettings(Material.NETHERITE_SWORD).customModel(1000).damage(12.0).attackSpeed(-1.6)
                .displayName("item.netherite_dagger.name".asTranslate().color(TextColor.color(128, 128, 128))).quality(ItemQuality.RARE)
                .tradePrice(130)
                .addAviableTrader(TraderType.NORMAL)
        ))

        register("wooden_sword", BaseSword(
            "wooden_sword",
            ItemSettings(Material.WOODEN_SWORD).damage(5.0).attackSpeed(-2.3).range(1.0)
                .displayName("item.wooden_sword.name".asTranslate().color(TextColor.color(128, 128, 128)))
                .tradePrice(35)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("golden_sword", BaseSword(
            "golden_sword",
            ItemSettings(Material.GOLDEN_SWORD).damage(7.0).attackSpeed(-2.1).range(1.0)
                .displayName("item.golden_sword.name".asTranslate().color(TextColor.color(128, 128, 128)))
                .tradePrice(45)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("stone_sword", BaseSword(
            "stone_sword",
            ItemSettings(Material.STONE_SWORD).damage(10.0).attackSpeed(-2.3).range(1.0)
                .displayName("item.stone_sword.name".asTranslate().color(TextColor.color(128, 128, 128))).quality(ItemQuality.UNCOMMON)
                .tradePrice(75)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("iron_sword", BaseSword(
            "iron_sword",
            ItemSettings(Material.IRON_SWORD).damage(13.0).attackSpeed(-2.3).range(1.0)
                .displayName("item.iron_sword.name".asTranslate().color(TextColor.color(128, 128, 128))).quality(ItemQuality.UNCOMMON)
                .tradePrice(85)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("diamond_sword", BaseSword(
            "diamond_sword",
            ItemSettings(Material.DIAMOND_SWORD).damage(16.0).attackSpeed(-2.3).range(1.0)
                .displayName("item.diamond_sword.name".asTranslate().color(TextColor.color(128, 128, 128))).quality(ItemQuality.UNCOMMON)
                .tradePrice(110)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("netherite_sword", BaseSword(
            "netherite_sword",
            ItemSettings(Material.NETHERITE_SWORD).damage(20.0).attackSpeed(-2.3).range(1.0)
                .displayName("item.netherite_sword.name".asTranslate().color(TextColor.color(128, 128, 128))).quality(ItemQuality.RARE)
                .tradePrice(130)
                .addAviableTrader(TraderType.NORMAL)
        ))

        register("wooden_hammer", BaseHammer(
            "wooden_hammer",
            ItemSettings(Material.MACE).customModel(1000).damage(5.0).attackSpeed(-3.2).hitRange(0.5)
                .displayName("item.wooden_hammer.name".asTranslate().color(TextColor.color(128, 128, 128)))
                .lore(mutableListOf("item.wooden_hammer.lore.0".asTranslate()))
                .tradePrice(35)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("golden_hammer", BaseHammer(
            "golden_hammer",
            ItemSettings(Material.MACE).customModel(1001).damage(7.0).attackSpeed(-3.0).hitRange(0.5)
                .displayName("item.golden_hammer.name".asTranslate().color(TextColor.color(128, 128, 128)))
                .lore(mutableListOf("item.golden_hammer.lore.0".asTranslate()))
                .tradePrice(45)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("stone_hammer", BaseHammer(
            "stone_hammer",
            ItemSettings(Material.MACE).customModel(1002).damage(10.0).attackSpeed(-3.2).hitRange(0.5)
                .displayName("item.stone_hammer.name".asTranslate().color(TextColor.color(128, 128, 128)))
                .lore(mutableListOf("item.stone_hammer.lore.0".asTranslate()))
                .quality(ItemQuality.UNCOMMON)
                .tradePrice(75)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("iron_hammer", BaseHammer(
            "iron_hammer",
            ItemSettings(Material.MACE).customModel(1003).damage(13.0).attackSpeed(-3.2).hitRange(1.0)
                .displayName("item.iron_hammer.name".asTranslate().color(TextColor.color(128, 128, 128)))
                .lore(mutableListOf("item.iron_hammer.lore.0".asTranslate()))
                .quality(ItemQuality.UNCOMMON)
                .tradePrice(85)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("diamond_hammer", BaseHammer(
            "diamond_hammer",
            ItemSettings(Material.MACE).customModel(1004).damage(16.0).attackSpeed(-3.2).hitRange(1.0)
                .displayName("item.diamond_hammer.name".asTranslate().color(TextColor.color(128, 128, 128)))
                .lore(mutableListOf("item.diamond_hammer.lore.0".asTranslate()))
                .quality(ItemQuality.UNCOMMON)
                .tradePrice(110)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("netherite_hammer", BaseHammer(
            "netherite_hammer",
            ItemSettings(Material.MACE).customModel(1005).damage(20.0).attackSpeed(-3.2).hitRange(1.5)
                .displayName("item.netherite_hammer.name".asTranslate().color(TextColor.color(128, 128, 128)))
                .lore(mutableListOf("item.netherite_hammer.lore.0".asTranslate()))
                .quality(ItemQuality.RARE)
                .tradePrice(130)
                .addAviableTrader(TraderType.NORMAL)
        ))

        register("wooden_spear", BaseSpear(
            "wooden_spear",
            ItemSettings(Material.WOODEN_SWORD).customModel(1001).damage(5.0).attackSpeed(-3.0).range(2.5)
                .displayName("item.wooden_spear.name".asTranslate().color(TextColor.color(128, 128, 128)))
                .tradePrice(35)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("golden_spear", BaseSpear(
            "golden_spear",
            ItemSettings(Material.GOLDEN_SWORD).customModel(1001).damage(7.0).attackSpeed(-2.8).range(2.5)
                .displayName("item.golden_spear.name".asTranslate().color(TextColor.color(128, 128, 128)))
                .tradePrice(45)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("stone_spear", BaseSpear(
            "stone_spear",
            ItemSettings(Material.STONE_SWORD).customModel(1001).damage(10.0).attackSpeed(-3.0).range(2.5)
                .displayName("item.stone_spear.name".asTranslate().color(TextColor.color(128, 128, 128)))
                .quality(ItemQuality.UNCOMMON)
                .tradePrice(75)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("iron_spear", BaseSpear(
            "iron_spear",
            ItemSettings(Material.IRON_SWORD).customModel(1001).damage(13.0).attackSpeed(-3.0).range(2.5)
                .displayName("item.iron_spear.name".asTranslate().color(TextColor.color(128, 128, 128)))
                .quality(ItemQuality.UNCOMMON)
                .tradePrice(85)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("diamond_spear", BaseSpear(
            "diamond_spear",
            ItemSettings(Material.DIAMOND_SWORD).customModel(1001).damage(16.0).attackSpeed(-3.0).range(2.5)
                .displayName("item.diamond_spear.name".asTranslate().color(TextColor.color(128, 128, 128)))
                .quality(ItemQuality.UNCOMMON)
                .tradePrice(110)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("netherite_spear", BaseSpear(
            "netherite_spear",
            ItemSettings(Material.NETHERITE_SWORD).customModel(1001).damage(20.0).attackSpeed(-3.0).range(2.5)
                .displayName("item.netherite_spear.name".asTranslate().color(TextColor.color(128, 128, 128)))
                .quality(ItemQuality.RARE)
                .tradePrice(130)
                .addAviableTrader(TraderType.NORMAL)
        ))

        register("shield", CustomItem(
            "shield",
            ItemType.MISC,
            ItemSettings(Material.SHIELD)
                .displayName("item.shield.name".asTranslate())
                .tradePrice(90)
                .addAviableTrader(TraderType.NORMAL)
        ))

        register("heal_stuff", HealingStaff("heal_stuff"))
        register("frost_ball_stuff", FrostballStaff("frost_ball_stuff"))
        register("big_frost_ball_stuff", BigFrostballStaff("big_frost_ball_stuff"))
        register("fire_ball_stuff", FireballStaff("fire_ball_stuff"))
        register("big_fire_ball_stuff", BigFireballStaff("big_fire_ball_stuff"))
        register("big_heal_stuff", BigHealingStaff("big_heal_stuff"))
        register("wither_ball_stuff", WitherballStaff("wither_ball_stuff"))
        register("big_wither_ball_stuff", BigWitherballStaff("big_wither_ball_stuff"))
        register("necromantic_stuff", NecromanticStaff("necromantic_stuff"))
        register("electric_stuff", ElectricStaff("electric_stuff"))
        register("laser_stuff", LaserStaff("laser_stuff"))

        register("heal_potion", HealPotion("heal_potion"))
        register("small_heal_potion", SmallHealPotion("small_heal_potion"))

        register("bone", Bone("bone"))
        register("flesh", Flesh("flesh"))
        register("gunpowder", Gunpowder("gunpowder"))

        register("leather_helmet", ArmorItem("leather_helmet", ItemSettings(Material.LEATHER_HELMET)
            .displayName("item.leather_helmet.name".asTranslate())
            .defence(1.0)
            .tradePrice(25)
            .addAviableTrader(TraderType.ARMOR)
        ))
        register("leather_chestplate", ArmorItem("leather_chestplate", ItemSettings(Material.LEATHER_CHESTPLATE)
            .displayName("item.leather_chestplate.name".asTranslate())
            .defence(3.0)
            .tradePrice(45)
            .addAviableTrader(TraderType.ARMOR)
        ))
        register("leather_leggings", ArmorItem("leather_leggings", ItemSettings(Material.LEATHER_LEGGINGS)
            .displayName("item.leather_leggings.name".asTranslate())
            .defence(2.0)
            .tradePrice(35)
            .addAviableTrader(TraderType.ARMOR)
        ))
        register("leather_boots", ArmorItem("leather_boots", ItemSettings(Material.LEATHER_BOOTS)
            .displayName("item.leather_boots.name".asTranslate())
            .defence(1.0)
            .tradePrice(25)
            .addAviableTrader(TraderType.ARMOR)
        ))

        register("golden_helmet", ArmorItem("golden_helmet", ItemSettings(Material.GOLDEN_HELMET)
            .displayName("item.golden_helmet.name".asTranslate())
            .defence(2.0)
            .tradePrice(35)
            .addAviableTrader(TraderType.ARMOR)
        ))
        register("golden_chestplate", ArmorItem("golden_chestplate", ItemSettings(Material.GOLDEN_CHESTPLATE)
            .displayName("item.golden_chestplate.name".asTranslate())
            .defence(5.0)
            .tradePrice(55)
            .addAviableTrader(TraderType.ARMOR)
        ))
        register("golden_leggings", ArmorItem("golden_leggings", ItemSettings(Material.GOLDEN_LEGGINGS)
            .displayName("item.golden_leggings.name".asTranslate())
            .defence(3.0)
            .tradePrice(45)
            .addAviableTrader(TraderType.ARMOR)
        ))
        register("golden_boots", ArmorItem("golden_boots", ItemSettings(Material.GOLDEN_BOOTS)
            .displayName("item.golden_boots.name".asTranslate())
            .defence(1.0)
            .tradePrice(35)
            .addAviableTrader(TraderType.ARMOR)
        ))

        register("chainmail_helmet", ArmorItem("chainmail_helmet", ItemSettings(Material.CHAINMAIL_HELMET)
            .displayName("item.chainmail_helmet.name".asTranslate())
            .defence(2.0)
            .tradePrice(45)
            .addAviableTrader(TraderType.ARMOR)
            .quality(ItemQuality.UNCOMMON)
        ))
        register("chainmail_chestplate", ArmorItem("chainmail_chestplate", ItemSettings(Material.CHAINMAIL_CHESTPLATE)
            .displayName("item.chainmail_chestplate.name".asTranslate())
            .defence(5.0)
            .tradePrice(75)
            .addAviableTrader(TraderType.ARMOR)
            .quality(ItemQuality.UNCOMMON)
        ))
        register("chainmail_leggings", ArmorItem("chainmail_leggings", ItemSettings(Material.CHAINMAIL_LEGGINGS)
            .displayName("item.chainmail_leggings.name".asTranslate())
            .defence(4.0)
            .tradePrice(60)
            .addAviableTrader(TraderType.ARMOR)
            .quality(ItemQuality.UNCOMMON)
        ))
        register("chainmail_boots", ArmorItem("chainmail_boots", ItemSettings(Material.CHAINMAIL_BOOTS)
            .displayName("item.chainmail_boots.name".asTranslate())
            .defence(1.0)
            .tradePrice(45)
            .addAviableTrader(TraderType.ARMOR)
            .quality(ItemQuality.UNCOMMON)
        ))

        register("iron_helmet", ArmorItem("iron_helmet", ItemSettings(Material.IRON_HELMET)
            .displayName("item.iron_helmet.name".asTranslate())
            .defence(2.0)
            .tradePrice(60)
            .addAviableTrader(TraderType.ARMOR)
            .quality(ItemQuality.UNCOMMON)
        ))
        register("iron_chestplate", ArmorItem("iron_chestplate", ItemSettings(Material.IRON_CHESTPLATE)
            .displayName("item.iron_chestplate.name".asTranslate())
            .defence(6.0)
            .tradePrice(100)
            .addAviableTrader(TraderType.ARMOR)
            .quality(ItemQuality.UNCOMMON)
        ))
        register("iron_leggings", ArmorItem("iron_leggings", ItemSettings(Material.IRON_LEGGINGS)
            .displayName("item.iron_leggings.name".asTranslate())
            .defence(5.0)
            .tradePrice(80)
            .addAviableTrader(TraderType.ARMOR)
            .quality(ItemQuality.UNCOMMON)
        ))
        register("iron_boots", ArmorItem("iron_boots", ItemSettings(Material.IRON_BOOTS)
            .displayName("item.iron_boots.name".asTranslate())
            .defence(2.0)
            .tradePrice(60)
            .addAviableTrader(TraderType.ARMOR)
            .quality(ItemQuality.UNCOMMON)
        ))

        register("diamond_helmet", ArmorItem("diamond_helmet", ItemSettings(Material.DIAMOND_HELMET)
            .displayName("item.diamond_helmet.name".asTranslate())
            .defence(3.0)
            .thoroughness(2.0)
            .tradePrice(70)
            .addAviableTrader(TraderType.ARMOR)
            .quality(ItemQuality.UNCOMMON)
        ))
        register("diamond_chestplate", ArmorItem("diamond_chestplate", ItemSettings(Material.DIAMOND_CHESTPLATE)
            .displayName("item.diamond_chestplate.name".asTranslate())
            .defence(8.0)
            .thoroughness(2.0)
            .tradePrice(120)
            .addAviableTrader(TraderType.ARMOR)
            .quality(ItemQuality.UNCOMMON)
        ))
        register("diamond_leggings", ArmorItem("diamond_leggings", ItemSettings(Material.DIAMOND_LEGGINGS)
            .displayName("item.diamond_leggings.name".asTranslate())
            .defence(6.0)
            .thoroughness(2.0)
            .tradePrice(100)
            .addAviableTrader(TraderType.ARMOR)
            .quality(ItemQuality.UNCOMMON)
        ))
        register("diamond_boots", ArmorItem("diamond_boots", ItemSettings(Material.DIAMOND_BOOTS)
            .displayName("item.diamond_boots.name".asTranslate())
            .defence(3.0)
            .thoroughness(2.0)
            .tradePrice(70)
            .addAviableTrader(TraderType.ARMOR)
            .quality(ItemQuality.UNCOMMON)
        ))

        register("netherite_helmet", ArmorItem("netherite_helmet", ItemSettings(Material.NETHERITE_HELMET)
            .displayName("item.netherite_helmet.name".asTranslate())
            .defence(3.0)
            .thoroughness(3.0)
            .knockbackResistance(0.1)
            .tradePrice(80)
            .addAviableTrader(TraderType.ARMOR)
            .quality(ItemQuality.RARE)
        ))
        register("netherite_chestplate", ArmorItem("netherite_chestplate", ItemSettings(Material.NETHERITE_CHESTPLATE)
            .displayName("item.netherite_chestplate.name".asTranslate())
            .defence(8.0)
            .thoroughness(3.0)
            .knockbackResistance(0.1)
            .tradePrice(130)
            .addAviableTrader(TraderType.ARMOR)
            .quality(ItemQuality.RARE)
        ))
        register("netherite_leggings", ArmorItem("netherite_leggings", ItemSettings(Material.NETHERITE_LEGGINGS)
            .displayName("item.netherite_leggings.name".asTranslate())
            .defence(6.0)
            .thoroughness(3.0)
            .knockbackResistance(0.1)
            .tradePrice(110)
            .addAviableTrader(TraderType.ARMOR)
            .quality(ItemQuality.RARE)
        ))
        register("netherite_boots", ArmorItem("netherite_boots", ItemSettings(Material.NETHERITE_BOOTS)
            .displayName("item.netherite_boots.name".asTranslate())
            .defence(3.0)
            .thoroughness(3.0)
            .knockbackResistance(0.1)
            .tradePrice(80)
            .addAviableTrader(TraderType.ARMOR)
            .quality(ItemQuality.RARE)
        ))

        loadPotLoot()
    }

    private fun register(id: String, item: CustomItem) {
        itemsMap[id] = item
    }

    private fun loadPotLoot() {
        val potLoot = WeightedRandomList<ItemStack>()

        potLoot.addEntry(itemsMap["small_heal_potion"]!!.asItemStack(), 1.0)
        potLoot.addEntry(itemsMap["bone"]!!.asItemStack(), 1.0)
        potLoot.addEntry(itemsMap["flesh"]!!.asItemStack(), 1.0)
        potLoot.addEntry(itemsMap["gunpowder"]!!.asItemStack(), 1.0)

        GameController.initPotLootList(potLoot)
    }

    fun get(id: String): CustomItem? {
        return itemsMap[id]
    }
}