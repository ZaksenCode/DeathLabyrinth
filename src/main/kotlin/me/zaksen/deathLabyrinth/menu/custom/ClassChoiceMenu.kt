package me.zaksen.deathLabyrinth.menu.custom

import me.zaksen.deathLabyrinth.classes.MageClass
import me.zaksen.deathLabyrinth.classes.WarriorClass
import me.zaksen.deathLabyrinth.config.ConfigContainer
import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.menu.MenuController
import me.zaksen.deathLabyrinth.menu.api.Menu
import me.zaksen.deathLabyrinth.menu.api.MenuType
import me.zaksen.deathLabyrinth.util.ChatUtil
import me.zaksen.deathLabyrinth.util.asText
import me.zaksen.deathLabyrinth.util.name
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class ClassChoiceMenu(private val configs: ConfigContainer): Menu(MenuType.BASE_27, configs.langConfig().classChoiceMenuTitle.asText()) {

    init {
        fill(ItemStack(Material.LIME_STAINED_GLASS_PANE))
        setItem(10, ItemStack(Material.IRON_SWORD).name(ChatUtil.format("<red>Воин</red>"))) {
            val playerData = GameController.players[it.whoClicked]
            if(playerData != null) {
                playerData.playerClass = WarriorClass()

                ChatUtil.broadcast("<green>{player} выбрал класс</green> <red>война!</red>", Pair("{player}", it.whoClicked.name))
                MenuController.closeMenu(it.whoClicked)
                GameController.checkClasses()
            }
        }
        setItem(11, ItemStack(Material.EXPERIENCE_BOTTLE).name(ChatUtil.format("<blue>Маг</blue>"))) {
            val playerData = GameController.players[it.whoClicked]

            if(playerData != null) {
                playerData.playerClass = MageClass()

                ChatUtil.broadcast("<green>{player} выбрал класс</green> <blue>мага!</blue>", Pair("{player}", it.whoClicked.name))
                MenuController.closeMenu(it.whoClicked)
                GameController.checkClasses()
            }
        }
        updateInventory()
    }
}