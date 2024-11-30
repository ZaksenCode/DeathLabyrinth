package me.zaksen.deathLabyrinth.item

import me.zaksen.deathLabyrinth.damage.DamageType
import me.zaksen.deathLabyrinth.entity.trader.TraderType
import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.game.pot.PotEntry
import me.zaksen.deathLabyrinth.item.accessory.accessories.*
import me.zaksen.deathLabyrinth.item.gear.armor.ArmorItem
import me.zaksen.deathLabyrinth.item.settings.ItemSettings
import me.zaksen.deathLabyrinth.item.items.consume.*
import me.zaksen.deathLabyrinth.item.items.ingredient.*
import me.zaksen.deathLabyrinth.item.weapon.WeaponItem
import me.zaksen.deathLabyrinth.item.weapon.WeaponType
import me.zaksen.deathLabyrinth.item.weapon.weapons.stuff.*
import me.zaksen.deathLabyrinth.item.weapon.weapons.sword.*
import me.zaksen.deathLabyrinth.util.WeightedRandomList
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
                .tradePrice(40)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("golden_dagger", BaseDagger(
            "golden_dagger",
            ItemSettings(Material.GOLDEN_SWORD).customModel(1000).damage(4.0).attackSpeed(-1.4)
                .displayName("item.golden_dagger.name".asTranslate().color(TextColor.color(128, 128, 128)))
                .tradePrice(60)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("stone_dagger", BaseDagger(
            "stone_dagger",
            ItemSettings(Material.STONE_SWORD).customModel(1000).damage(6.0).attackSpeed(-1.6)
                .displayName("item.stone_dagger.name".asTranslate().color(TextColor.color(128, 128, 128))).quality(ItemQuality.UNCOMMON)
                .tradePrice(80)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("iron_dagger", BaseDagger(
            "iron_dagger",
            ItemSettings(Material.IRON_SWORD).customModel(1000).damage(8.0).attackSpeed(-1.6)
                .displayName("item.iron_dagger.name".asTranslate().color(TextColor.color(128, 128, 128))).quality(ItemQuality.UNCOMMON)
                .tradePrice(110)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("diamond_dagger", BaseDagger(
            "diamond_dagger",
            ItemSettings(Material.DIAMOND_SWORD).customModel(1000).damage(10.0).attackSpeed(-1.6)
                .displayName("item.diamond_dagger.name".asTranslate().color(TextColor.color(128, 128, 128))).quality(ItemQuality.UNCOMMON)
                .tradePrice(145)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("netherite_dagger", BaseDagger(
            "netherite_dagger",
            ItemSettings(Material.NETHERITE_SWORD).customModel(1000).damage(12.0).attackSpeed(-1.6)
                .displayName("item.netherite_dagger.name".asTranslate().color(TextColor.color(128, 128, 128))).quality(ItemQuality.RARE)
                .tradePrice(180)
                .addAviableTrader(TraderType.NORMAL)
        ))

        register("wooden_sword", BaseSword(
            "wooden_sword",
            ItemSettings(Material.WOODEN_SWORD).damage(5.0).attackSpeed(-2.3).range(1.0)
                .displayName("item.wooden_sword.name".asTranslate().color(TextColor.color(128, 128, 128)))
                .tradePrice(40)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("golden_sword", BaseSword(
            "golden_sword",
            ItemSettings(Material.GOLDEN_SWORD).damage(7.0).attackSpeed(-2.1).range(1.0)
                .displayName("item.golden_sword.name".asTranslate().color(TextColor.color(128, 128, 128)))
                .tradePrice(60)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("stone_sword", BaseSword(
            "stone_sword",
            ItemSettings(Material.STONE_SWORD).damage(10.0).attackSpeed(-2.3).range(1.0)
                .displayName("item.stone_sword.name".asTranslate().color(TextColor.color(128, 128, 128))).quality(ItemQuality.UNCOMMON)
                .tradePrice(80)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("iron_sword", BaseSword(
            "iron_sword",
            ItemSettings(Material.IRON_SWORD).damage(13.0).attackSpeed(-2.3).range(1.0)
                .displayName("item.iron_sword.name".asTranslate().color(TextColor.color(128, 128, 128))).quality(ItemQuality.UNCOMMON)
                .tradePrice(110)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("diamond_sword", BaseSword(
            "diamond_sword",
            ItemSettings(Material.DIAMOND_SWORD).damage(16.0).attackSpeed(-2.3).range(1.0)
                .displayName("item.diamond_sword.name".asTranslate().color(TextColor.color(128, 128, 128))).quality(ItemQuality.UNCOMMON)
                .tradePrice(145)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("netherite_sword", BaseSword(
            "netherite_sword",
            ItemSettings(Material.NETHERITE_SWORD).damage(20.0).attackSpeed(-2.3).range(1.0)
                .displayName("item.netherite_sword.name".asTranslate().color(TextColor.color(128, 128, 128))).quality(ItemQuality.RARE)
                .tradePrice(180)
                .addAviableTrader(TraderType.NORMAL)
        ))

        register("wooden_hammer", BaseHammer(
            "wooden_hammer",
            ItemSettings(Material.MACE).customModel(1000).damage(5.0).attackSpeed(-3.2)
                .displayName("item.wooden_hammer.name".asTranslate().color(TextColor.color(128, 128, 128)))
                .tradePrice(40)
                .addAviableTrader(TraderType.NORMAL)
                .ability("area_hit")
        ))
        register("golden_hammer", BaseHammer(
            "golden_hammer",
            ItemSettings(Material.MACE).customModel(1001).damage(7.0).attackSpeed(-3.0)
                .displayName("item.golden_hammer.name".asTranslate().color(TextColor.color(128, 128, 128)))
                .tradePrice(60)
                .addAviableTrader(TraderType.NORMAL)
                .ability("area_hit")
        ))
        register("stone_hammer", BaseHammer(
            "stone_hammer",
            ItemSettings(Material.MACE).customModel(1002).damage(10.0).attackSpeed(-3.2)
                .displayName("item.stone_hammer.name".asTranslate().color(TextColor.color(128, 128, 128)))
                .quality(ItemQuality.UNCOMMON)
                .tradePrice(80)
                .addAviableTrader(TraderType.NORMAL)
                .ability("area_hit")
        ))
        register("iron_hammer", BaseHammer(
            "iron_hammer",
            ItemSettings(Material.MACE).customModel(1003).damage(13.0).attackSpeed(-3.2)
                .displayName("item.iron_hammer.name".asTranslate().color(TextColor.color(128, 128, 128)))
                .quality(ItemQuality.UNCOMMON)
                .tradePrice(110)
                .addAviableTrader(TraderType.NORMAL)
                .ability("big_area_hit")
        ))
        register("diamond_hammer", BaseHammer(
            "diamond_hammer",
            ItemSettings(Material.MACE).customModel(1004).damage(16.0).attackSpeed(-3.2)
                .displayName("item.diamond_hammer.name".asTranslate().color(TextColor.color(128, 128, 128)))
                .quality(ItemQuality.UNCOMMON)
                .tradePrice(145)
                .addAviableTrader(TraderType.NORMAL)
                .ability("big_area_hit")
        ))
        register("netherite_hammer", BaseHammer(
            "netherite_hammer",
            ItemSettings(Material.MACE).customModel(1005).damage(20.0).attackSpeed(-3.2)
                .displayName("item.netherite_hammer.name".asTranslate().color(TextColor.color(128, 128, 128)))
                .quality(ItemQuality.RARE)
                .tradePrice(180)
                .addAviableTrader(TraderType.NORMAL)
                .ability("huge_area_hit")
        ))

        register("wooden_spear", BaseSpear(
            "wooden_spear",
            ItemSettings(Material.WOODEN_SWORD).customModel(1001).damage(5.0).attackSpeed(-3.0).range(2.5)
                .displayName("item.wooden_spear.name".asTranslate().color(TextColor.color(128, 128, 128)))
                .tradePrice(40)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("golden_spear", BaseSpear(
            "golden_spear",
            ItemSettings(Material.GOLDEN_SWORD).customModel(1001).damage(7.0).attackSpeed(-2.8).range(2.5)
                .displayName("item.golden_spear.name".asTranslate().color(TextColor.color(128, 128, 128)))
                .tradePrice(60)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("stone_spear", BaseSpear(
            "stone_spear",
            ItemSettings(Material.STONE_SWORD).customModel(1001).damage(10.0).attackSpeed(-3.0).range(2.5)
                .displayName("item.stone_spear.name".asTranslate().color(TextColor.color(128, 128, 128)))
                .quality(ItemQuality.UNCOMMON)
                .tradePrice(80)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("iron_spear", BaseSpear(
            "iron_spear",
            ItemSettings(Material.IRON_SWORD).customModel(1001).damage(13.0).attackSpeed(-3.0).range(2.5)
                .displayName("item.iron_spear.name".asTranslate().color(TextColor.color(128, 128, 128)))
                .quality(ItemQuality.UNCOMMON)
                .tradePrice(110)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("diamond_spear", BaseSpear(
            "diamond_spear",
            ItemSettings(Material.DIAMOND_SWORD).customModel(1001).damage(16.0).attackSpeed(-3.0).range(2.5)
                .displayName("item.diamond_spear.name".asTranslate().color(TextColor.color(128, 128, 128)))
                .quality(ItemQuality.UNCOMMON)
                .tradePrice(145)
                .addAviableTrader(TraderType.NORMAL)
        ))
        register("netherite_spear", BaseSpear(
            "netherite_spear",
            ItemSettings(Material.NETHERITE_SWORD).customModel(1001).damage(20.0).attackSpeed(-3.0).range(2.5)
                .displayName("item.netherite_spear.name".asTranslate().color(TextColor.color(128, 128, 128)))
                .quality(ItemQuality.RARE)
                .tradePrice(180)
                .addAviableTrader(TraderType.NORMAL)
        ))

        register("fish_sword", BaseSword(
            "fish_sword",
            ItemSettings(Material.SALMON).damage(18.0).attackSpeed(-2.3).range(1.0)
                .customModel(100)
                .displayName("item.fish_sword.name".asTranslate().color(TextColor.color(128, 128, 128)))
                .quality(ItemQuality.RARE)
                .tradePrice(110)
                .addAviableTrader(TraderType.NORMAL)
                .abilityCooldown(10000)
                .ability("bubble_laser")
        ))

        register("fire_sword", BaseSword(
            "fire_sword",
            ItemSettings(Material.WOODEN_SWORD).damage(10.0).attackSpeed(-2.0).range(1.0)
                .customModel(1002)
                .displayName("item.fire_sword.name".asTranslate().color(TextColor.color(128, 128, 128)))
                .tradePrice(80)
                .quality(ItemQuality.UNCOMMON)
                .addAviableTrader(TraderType.NORMAL)
                .ability("fire_blade")
                .damageType(DamageType.FIRE)
        ))

        register("wind_sword", BaseSword(
            "wind_sword",
            ItemSettings(Material.WOODEN_SWORD).damage(13.0).attackSpeed(-1.8).range(1.0)
                .customModel(1003)
                .displayName("item.wind_sword.name".asTranslate().color(TextColor.color(128, 128, 128)))
                .quality(ItemQuality.UNCOMMON)
                .tradePrice(105)
                .addAviableTrader(TraderType.NORMAL)
                .abilityCooldown(1200)
                .ability("wind_gust")
        ))

        register("narwhal_horn", BaseSpear(
            "narwhal_horn",
            ItemSettings(Material.WOODEN_SWORD).damage(15.0).attackSpeed(-2.4).range(2.0)
                .customModel(1004)
                .displayName("item.narwhal_horn.name".asTranslate().color(TextColor.color(128, 128, 128)))
                .quality(ItemQuality.RARE)
                .tradePrice(110)
                .addAviableTrader(TraderType.NORMAL)
                .ability("narwhal_punch")
        ))

        register("dynamite_stick", BaseSword(
            "dynamite_stick",
            ItemSettings(Material.WOODEN_SWORD).damage(3.0).attackSpeed(-2.4).range(2.0)
                .customModel(1005)
                .displayName("item.dynamite_stick.name".asTranslate().color(TextColor.color(128, 128, 128)))
                .quality(ItemQuality.RARE)
                .tradePrice(95)
                .addAviableTrader(TraderType.NORMAL)
                .ability("explosion_punch")
        ))

        register("noble_gladius", BaseSword(
            "noble_gladius",
            ItemSettings(Material.WOODEN_SWORD).damage(14.0).attackSpeed(-2.2).range(1.2)
                .customModel(1006)
                .displayName("item.noble_gladius.name".asTranslate().color(TextColor.color(128, 128, 128)))
                .quality(ItemQuality.UNCOMMON)
                .tradePrice(110)
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

        register("wind_charge_staff", WeaponItem(WeaponType.MISC_STAFF, "wind_charge_staff", ItemSettings(Material.STICK)
            .customModel(111)
            .displayName("item.wind_charge_staff.name".asTranslate().color(TextColor.color(0, 191, 255)))
            .abilityCooldown(1200)
            .quality(ItemQuality.UNCOMMON)
            .tradePrice(75)
            .addAviableTrader(TraderType.NORMAL)
            .ability("wind_charge_cast")
        ))

        register("fire_flow_staff", FireFlowStaff("fire_flow_staff"))

        register("deep_forest_staff", WeaponItem(WeaponType.ATTACK_STAFF, "deep_forest_staff", ItemSettings(Material.STICK)
            .customModel(113)
            .displayName("item.deep_forest_staff.name".asTranslate().color(TextColor.color(0, 191, 255)))
            .abilityCooldown(1200)
            .quality(ItemQuality.UNCOMMON)
            .tradePrice(80)
            .addAviableTrader(TraderType.NORMAL)
            .ability("explosion_cast")
        ))

        register("explosion_staff", WeaponItem(WeaponType.ATTACK_STAFF, "explosion_staff", ItemSettings(Material.STICK)
            .customModel(114)
            .displayName("item.explosion_staff.name".asTranslate().color(TextColor.color(0, 191, 255)))
            .abilityCooldown(2000)
            .quality(ItemQuality.UNCOMMON)
            .tradePrice(80)
            .ability("bomb_cast")
        ))

        register("sculk_staff", WeaponItem(WeaponType.ATTACK_STAFF, "sculk_staff", ItemSettings(Material.STICK)
            .customModel(115)
            .displayName("item.sculk_staff.name".asTranslate().color(TextColor.color(0, 191, 255)))
            .abilityCooldown(1000)
            .quality(ItemQuality.UNCOMMON)
            .tradePrice(80)
            .ability("sculk_cast")
        ))

        register("heal_potion", HealPotion("heal_potion"))
        register("small_heal_potion", SmallHealPotion("small_heal_potion"))
        register("madmans_potion", MadmansPotion("madmans_potion"))

        register("bottle", CustomItem("bottle", ItemType.MISC,
            ItemSettings(Material.GLASS_BOTTLE)
                .displayName("item.bottle.name".asTranslate())
                .loreLine("item.bottle.lore.0".asTranslate().color(TextColor.color(128, 0, 128)))
                .tradePrice(20)
                .addAviableTrader(TraderType.ALCHEMIST)
        ))

        register("bone", Bone("bone"))
        register("flesh", Flesh("flesh"))
        register("gunpowder", Gunpowder("gunpowder"))

        register("leather_helmet", ArmorItem("leather_helmet", ItemSettings(Material.LEATHER_HELMET)
            .displayName("item.leather_helmet.name".asTranslate())
            .defence(2.0)
            .tradePrice(30)
            .addAviableTrader(TraderType.ARMOR)
        ))
        register("leather_chestplate", ArmorItem("leather_chestplate", ItemSettings(Material.LEATHER_CHESTPLATE)
            .displayName("item.leather_chestplate.name".asTranslate())
            .defence(4.0)
            .tradePrice(60)
            .addAviableTrader(TraderType.ARMOR)
        ))
        register("leather_leggings", ArmorItem("leather_leggings", ItemSettings(Material.LEATHER_LEGGINGS)
            .displayName("item.leather_leggings.name".asTranslate())
            .defence(3.0)
            .tradePrice(40)
            .addAviableTrader(TraderType.ARMOR)
        ))
        register("leather_boots", ArmorItem("leather_boots", ItemSettings(Material.LEATHER_BOOTS)
            .displayName("item.leather_boots.name".asTranslate())
            .defence(2.0)
            .tradePrice(30)
            .addAviableTrader(TraderType.ARMOR)
        ))

        register("golden_helmet", ArmorItem("golden_helmet", ItemSettings(Material.GOLDEN_HELMET)
            .displayName("item.golden_helmet.name".asTranslate())
            .defence(3.0)
            .tradePrice(45)
            .addAviableTrader(TraderType.ARMOR)
        ))
        register("golden_chestplate", ArmorItem("golden_chestplate", ItemSettings(Material.GOLDEN_CHESTPLATE)
            .displayName("item.golden_chestplate.name".asTranslate())
            .defence(7.0)
            .tradePrice(80)
            .addAviableTrader(TraderType.ARMOR)
        ))
        register("golden_leggings", ArmorItem("golden_leggings", ItemSettings(Material.GOLDEN_LEGGINGS)
            .displayName("item.golden_leggings.name".asTranslate())
            .defence(5.0)
            .tradePrice(60)
            .addAviableTrader(TraderType.ARMOR)
        ))
        register("golden_boots", ArmorItem("golden_boots", ItemSettings(Material.GOLDEN_BOOTS)
            .displayName("item.golden_boots.name".asTranslate())
            .defence(3.0)
            .tradePrice(45)
            .addAviableTrader(TraderType.ARMOR)
        ))

        register("chainmail_helmet", ArmorItem("chainmail_helmet", ItemSettings(Material.CHAINMAIL_HELMET)
            .displayName("item.chainmail_helmet.name".asTranslate())
            .defence(5.0)
            .tradePrice(60)
            .addAviableTrader(TraderType.ARMOR)
            .quality(ItemQuality.UNCOMMON)
        ))
        register("chainmail_chestplate", ArmorItem("chainmail_chestplate", ItemSettings(Material.CHAINMAIL_CHESTPLATE)
            .displayName("item.chainmail_chestplate.name".asTranslate())
            .defence(8.0)
            .tradePrice(110)
            .addAviableTrader(TraderType.ARMOR)
            .quality(ItemQuality.UNCOMMON)
        ))
        register("chainmail_leggings", ArmorItem("chainmail_leggings", ItemSettings(Material.CHAINMAIL_LEGGINGS)
            .displayName("item.chainmail_leggings.name".asTranslate())
            .defence(6.0)
            .tradePrice(85)
            .addAviableTrader(TraderType.ARMOR)
            .quality(ItemQuality.UNCOMMON)
        ))
        register("chainmail_boots", ArmorItem("chainmail_boots", ItemSettings(Material.CHAINMAIL_BOOTS)
            .displayName("item.chainmail_boots.name".asTranslate())
            .defence(4.0)
            .tradePrice(60)
            .addAviableTrader(TraderType.ARMOR)
            .quality(ItemQuality.UNCOMMON)
        ))

        register("iron_helmet", ArmorItem("iron_helmet", ItemSettings(Material.IRON_HELMET)
            .displayName("item.iron_helmet.name".asTranslate())
            .defence(7.0)
            .tradePrice(75)
            .addAviableTrader(TraderType.ARMOR)
            .quality(ItemQuality.UNCOMMON)
        ))
        register("iron_chestplate", ArmorItem("iron_chestplate", ItemSettings(Material.IRON_CHESTPLATE)
            .displayName("item.iron_chestplate.name".asTranslate())
            .defence(11.0)
            .tradePrice(135)
            .addAviableTrader(TraderType.ARMOR)
            .quality(ItemQuality.UNCOMMON)
        ))
        register("iron_leggings", ArmorItem("iron_leggings", ItemSettings(Material.IRON_LEGGINGS)
            .displayName("item.iron_leggings.name".asTranslate())
            .defence(8.0)
            .tradePrice(105)
            .addAviableTrader(TraderType.ARMOR)
            .quality(ItemQuality.UNCOMMON)
        ))
        register("iron_boots", ArmorItem("iron_boots", ItemSettings(Material.IRON_BOOTS)
            .displayName("item.iron_boots.name".asTranslate())
            .defence(6.0)
            .tradePrice(75)
            .addAviableTrader(TraderType.ARMOR)
            .quality(ItemQuality.UNCOMMON)
        ))

        register("diamond_helmet", ArmorItem("diamond_helmet", ItemSettings(Material.DIAMOND_HELMET)
            .displayName("item.diamond_helmet.name".asTranslate())
            .defence(8.0)
            .thoroughness(2.0)
            .tradePrice(90)
            .addAviableTrader(TraderType.ARMOR)
            .quality(ItemQuality.UNCOMMON)
        ))
        register("diamond_chestplate", ArmorItem("diamond_chestplate", ItemSettings(Material.DIAMOND_CHESTPLATE)
            .displayName("item.diamond_chestplate.name".asTranslate())
            .defence(14.0)
            .thoroughness(2.0)
            .tradePrice(150)
            .addAviableTrader(TraderType.ARMOR)
            .quality(ItemQuality.UNCOMMON)
        ))
        register("diamond_leggings", ArmorItem("diamond_leggings", ItemSettings(Material.DIAMOND_LEGGINGS)
            .displayName("item.diamond_leggings.name".asTranslate())
            .defence(11.0)
            .thoroughness(2.0)
            .tradePrice(130)
            .addAviableTrader(TraderType.ARMOR)
            .quality(ItemQuality.UNCOMMON)
        ))
        register("diamond_boots", ArmorItem("diamond_boots", ItemSettings(Material.DIAMOND_BOOTS)
            .displayName("item.diamond_boots.name".asTranslate())
            .defence(8.0)
            .thoroughness(2.0)
            .tradePrice(90)
            .addAviableTrader(TraderType.ARMOR)
            .quality(ItemQuality.UNCOMMON)
        ))

        register("netherite_helmet", ArmorItem("netherite_helmet", ItemSettings(Material.NETHERITE_HELMET)
            .displayName("item.netherite_helmet.name".asTranslate())
            .defence(9.0)
            .thoroughness(3.0)
            .knockbackResistance(0.1)
            .tradePrice(105)
            .addAviableTrader(TraderType.ARMOR)
            .quality(ItemQuality.RARE)
        ))
        register("netherite_chestplate", ArmorItem("netherite_chestplate", ItemSettings(Material.NETHERITE_CHESTPLATE)
            .displayName("item.netherite_chestplate.name".asTranslate())
            .defence(17.0)
            .thoroughness(3.0)
            .knockbackResistance(0.1)
            .tradePrice(175)
            .addAviableTrader(TraderType.ARMOR)
            .quality(ItemQuality.RARE)
        ))
        register("netherite_leggings", ArmorItem("netherite_leggings", ItemSettings(Material.NETHERITE_LEGGINGS)
            .displayName("item.netherite_leggings.name".asTranslate())
            .defence(15.0)
            .thoroughness(3.0)
            .knockbackResistance(0.1)
            .tradePrice(150)
            .addAviableTrader(TraderType.ARMOR)
            .quality(ItemQuality.RARE)
        ))
        register("netherite_boots", ArmorItem("netherite_boots", ItemSettings(Material.NETHERITE_BOOTS)
            .displayName("item.netherite_boots.name".asTranslate())
            .defence(9.0)
            .thoroughness(3.0)
            .knockbackResistance(0.1)
            .tradePrice(105)
            .addAviableTrader(TraderType.ARMOR)
            .quality(ItemQuality.RARE)
        ))

        register("amulet_of_greed", AmuletOfGreed())
        register("speedsters_amulet", SpeedstersAmulet())
        register("scoundrels_amulet", ScoundrelsAmulet())
        register("brave_mans_amulet", BraveMansAmulet())
        register("amulet_of_poverty", AmuletOfPoverty())

        loadPotLoot()
    }

    private fun register(id: String, item: CustomItem) {
        itemsMap[id] = item
    }

    private fun loadPotLoot() {
        val potLoot = WeightedRandomList<PotEntry>()

        potLoot.addEntry(PotEntry(itemsMap["small_heal_potion"]!!.asItemStack()), 0.8)
        potLoot.addEntry(PotEntry(itemsMap["heal_potion"]!!.asItemStack()), 0.5)
        potLoot.addEntry(PotEntry(itemsMap["bone"]!!.asItemStack(), true), 1.0)
        potLoot.addEntry(PotEntry(itemsMap["flesh"]!!.asItemStack(), true), 1.0)
        potLoot.addEntry(PotEntry(itemsMap["gunpowder"]!!.asItemStack(), true), 1.0)

        // Accessories
        potLoot.addEntry(PotEntry(itemsMap["amulet_of_greed"]!!.asItemStack()), 0.1)
        potLoot.addEntry(PotEntry(itemsMap["speedsters_amulet"]!!.asItemStack()), 0.1)
        potLoot.addEntry(PotEntry(itemsMap["scoundrels_amulet"]!!.asItemStack()), 0.1)
        potLoot.addEntry(PotEntry(itemsMap["brave_mans_amulet"]!!.asItemStack()), 0.1)
        potLoot.addEntry(PotEntry(itemsMap["amulet_of_poverty"]!!.asItemStack()), 0.1)

        // Other
        potLoot.addEntry(PotEntry(itemsMap["madmans_potion"]!!.asItemStack()), 0.05)

        GameController.initPotLootList(potLoot)
    }

    fun get(id: String): CustomItem? {
        return itemsMap[id]
    }
}