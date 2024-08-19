package me.zaksen.deathLabyrinth.item

import me.zaksen.deathLabyrinth.item.settings.ItemSettings
import me.zaksen.deathLabyrinth.item.weapon.weapons.sword.BaseDagger
import me.zaksen.deathLabyrinth.item.weapon.weapons.stuff.HealStuff
import me.zaksen.deathLabyrinth.item.weapon.weapons.sword.BaseHammer
import me.zaksen.deathLabyrinth.item.weapon.weapons.sword.BaseSpear
import me.zaksen.deathLabyrinth.item.weapon.weapons.sword.BaseSword
import me.zaksen.deathLabyrinth.util.ChatUtil
import org.bukkit.Material

object ItemsController {

    val itemsMap: MutableMap<String, CustomItem> = mutableMapOf()

    init {
        register("heal_stuff", HealStuff("heal_stuff"))

        register("wooden_dagger", BaseDagger(
            "wooden_dagger",
            ItemSettings(Material.WOODEN_SWORD).customModel(1000).damage(2.0).attackSpeed(-1.6)
                .displayName(ChatUtil.format("<gray>Деревянный клинок</gray>"))
        ))
        register("golden_dagger", BaseDagger(
            "golden_dagger",
            ItemSettings(Material.GOLDEN_SWORD).customModel(1000).damage(2.0).attackSpeed(-1.4)
                .displayName(ChatUtil.format("<gray>Золотой клинок</gray>"))
        ))
        register("stone_dagger", BaseDagger(
            "stone_dagger",
            ItemSettings(Material.STONE_SWORD).customModel(1000).damage(3.0).attackSpeed(-1.6)
                .displayName(ChatUtil.format("<gray>Каменный клинок</gray>"))
        ))
        register("iron_dagger", BaseDagger(
            "iron_dagger",
            ItemSettings(Material.IRON_SWORD).customModel(1000).damage(4.0).attackSpeed(-1.6)
                .displayName(ChatUtil.format("<gray>Железный клинок</gray>"))
        ))
        register("diamond_dagger", BaseDagger(
            "diamond_dagger",
            ItemSettings(Material.DIAMOND_SWORD).customModel(1000).damage(5.0).attackSpeed(-1.6)
                .displayName(ChatUtil.format("<gray>Алмазный клинок</gray>"))
        ))
        register("netherite_dagger", BaseDagger(
            "netherite_dagger",
            ItemSettings(Material.NETHERITE_SWORD).customModel(1000).damage(6.0).attackSpeed(-1.6)
                .displayName(ChatUtil.format("<gray>Незеритовый клинок</gray>"))
        ))

        register("wooden_sword", BaseSword(
            "wooden_sword",
            ItemSettings(Material.WOODEN_SWORD).damage(4.0).attackSpeed(-2.3).range(1.0)
                .displayName(ChatUtil.format("<gray>Деревянный меч</gray>"))
        ))
        register("golden_sword", BaseSword(
            "golden_sword",
            ItemSettings(Material.GOLDEN_SWORD).damage(4.0).attackSpeed(-2.1).range(1.0)
                .displayName(ChatUtil.format("<gray>Золотой меч</gray>"))
        ))
        register("stone_sword", BaseSword(
            "stone_sword",
            ItemSettings(Material.STONE_SWORD).damage(5.0).attackSpeed(-2.3).range(1.0)
                .displayName(ChatUtil.format("<gray>Каменный меч</gray>"))
        ))
        register("iron_sword", BaseSword(
            "iron_sword",
            ItemSettings(Material.IRON_SWORD).damage(6.0).attackSpeed(-2.3).range(1.0)
                .displayName(ChatUtil.format("<gray>Железный меч</gray>"))
        ))
        register("diamond_sword", BaseSword(
            "diamond_sword",
            ItemSettings(Material.DIAMOND_SWORD).damage(7.0).attackSpeed(-2.3).range(1.0)
                .displayName(ChatUtil.format("<gray>Алмазный меч</gray>"))
        ))
        register("netherite_sword", BaseSword(
            "netherite_sword",
            ItemSettings(Material.NETHERITE_SWORD).damage(8.0).attackSpeed(-2.3).range(1.0)
                .displayName(ChatUtil.format("<gray>Незеритовый меч</gray>"))
        ))

        register("wooden_hammer", BaseHammer(
            "wooden_hammer",
            ItemSettings(Material.MACE).customModel(1000).damage(4.0).attackSpeed(-3.2).hitRange(0.5)
                .displayName(ChatUtil.format("<gray>Деревянный молот</gray>"))
        ))
        register("golden_hammer", BaseHammer(
            "golden_hammer",
            ItemSettings(Material.MACE).customModel(1001).damage(4.0).attackSpeed(-3.0).hitRange(0.5)
                .displayName(ChatUtil.format("<gray>Золотой молот</gray>"))
        ))
        register("stone_hammer", BaseHammer(
            "stone_hammer",
            ItemSettings(Material.MACE).customModel(1002).damage(5.0).attackSpeed(-3.2).hitRange(0.5)
                .displayName(ChatUtil.format("<gray>Каменный молот</gray>"))
        ))
        register("iron_hammer", BaseHammer(
            "iron_hammer",
            ItemSettings(Material.MACE).customModel(1003).damage(6.0).attackSpeed(-3.2).hitRange(1.0)
                .displayName(ChatUtil.format("<gray>Железный молот</gray>"))
        ))
        register("diamond_hammer", BaseHammer(
            "diamond_hammer",
            ItemSettings(Material.MACE).customModel(1004).damage(7.0).attackSpeed(-3.2).hitRange(1.0)
                .displayName(ChatUtil.format("<gray>Алмазный молот</gray>"))
        ))
        register("netherite_hammer", BaseHammer(
            "netherite_hammer",
            ItemSettings(Material.MACE).customModel(1005).damage(8.0).attackSpeed(-3.2).hitRange(1.5)
                .displayName(ChatUtil.format("<gray>Незеритовый молот</gray>"))
        ))

        register("wooden_spear", BaseSpear(
            "wooden_spear",
            ItemSettings(Material.WOODEN_SWORD).customModel(1001).damage(5.0).attackSpeed(-3.0).range(2.5)
                .displayName(ChatUtil.format("<gray>Деревянное копьё</gray>"))
        ))
        register("golden_spear", BaseSpear(
            "golden_spear",
            ItemSettings(Material.GOLDEN_SWORD).customModel(1001).damage(5.0).attackSpeed(-2.8).range(2.5)
                .displayName(ChatUtil.format("<gray>Золотое копьё</gray>"))
        ))
        register("stone_spear", BaseSpear(
            "stone_dagger",
            ItemSettings(Material.STONE_SWORD).customModel(1001).damage(6.0).attackSpeed(-3.0).range(2.5)
                .displayName(ChatUtil.format("<gray>Каменное копьё</gray>"))
        ))
        register("iron_spear", BaseSpear(
            "iron_spear",
            ItemSettings(Material.IRON_SWORD).customModel(1001).damage(7.0).attackSpeed(-3.0).range(2.5)
                .displayName(ChatUtil.format("<gray>Железное копьё</gray>"))
        ))
        register("diamond_spear", BaseSpear(
            "diamond_spear",
            ItemSettings(Material.DIAMOND_SWORD).customModel(1001).damage(8.0).attackSpeed(-3.0).range(2.5)
                .displayName(ChatUtil.format("<gray>Алмазное копьё</gray>"))
        ))
        register("netherite_spear", BaseSpear(
            "netherite_spear",
            ItemSettings(Material.NETHERITE_SWORD).customModel(1001).damage(9.0).attackSpeed(-3.0).range(2.5)
                .displayName(ChatUtil.format("<gray>Незеритовое копьё</gray>"))
        ))
    }

    private fun register(id: String, item: CustomItem) {
        itemsMap[id] = item
    }

    fun get(id: String): CustomItem? {
        return itemsMap[id]
    }
}