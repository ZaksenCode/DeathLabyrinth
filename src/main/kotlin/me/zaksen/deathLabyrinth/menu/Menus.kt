package me.zaksen.deathLabyrinth.menu

import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import me.zaksen.deathLabyrinth.classes.MageClass
import me.zaksen.deathLabyrinth.classes.WarriorClass
import me.zaksen.deathLabyrinth.config.ConfigContainer
import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.item.ItemsController
import me.zaksen.deathLabyrinth.menu.item.ArtifactItem
import me.zaksen.deathLabyrinth.menu.item.ShopItem
import me.zaksen.deathLabyrinth.menu.item.TabItem
import me.zaksen.deathLabyrinth.trading.TradeOffer
import me.zaksen.deathLabyrinth.util.ChatUtil
import me.zaksen.deathLabyrinth.util.asText
import me.zaksen.deathLabyrinth.util.toWrapper
import net.kyori.adventure.key.Key
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.gui.PagedGui
import xyz.xenondevs.invui.gui.structure.Markers
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.builder.setLore
import xyz.xenondevs.invui.item.impl.AbstractItem
import xyz.xenondevs.invui.item.impl.SimpleItem
import xyz.xenondevs.invui.item.impl.controlitem.PageItem
import xyz.xenondevs.invui.window.Window

object Menus {

    private lateinit var configs: ConfigContainer

    fun setup(configContainer: ConfigContainer) {
        this.configs = configContainer
    }

    fun classChoice(player: Player) {
        val gui = Gui.normal()
            .setStructure(
                "# # # # # # # # #",
                "# M W . . . . . #",
                "# # # # # # # # #"
            )
            .addIngredient('#', SimpleItem(ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)))
            .addIngredient('M', object: AbstractItem(){
                override fun getItemProvider(): ItemProvider {
                    return ItemBuilder(Material.EXPERIENCE_BOTTLE).setDisplayName("<blue>Маг</blue>".toWrapper())
                }

                override fun handleClick(cilck: ClickType, player: Player, event: InventoryClickEvent) {
                    val playerData = GameController.players[player]

                    if(playerData != null) {
                        playerData.playerClass = MageClass()

                        ChatUtil.broadcast("<green>{player} выбрал класс</green> <blue>мага!</blue>", Pair("{player}", player.name))
                        player.closeInventory()
                        GameController.checkClasses()
                    }
                }
            })
            .addIngredient('W', object: AbstractItem(){
                override fun getItemProvider(): ItemProvider {
                    return ItemBuilder(Material.IRON_SWORD).setDisplayName("<red>Воин</red>".toWrapper())
                }

                override fun handleClick(cilck: ClickType, player: Player, event: InventoryClickEvent) {
                    val playerData = GameController.players[player]
                    if(playerData != null) {
                        playerData.playerClass = WarriorClass()

                        ChatUtil.broadcast("<green>{player} выбрал класс</green> <red>война!</red>", Pair("{player}", player.name))
                        player.closeInventory()
                        GameController.checkClasses()
                    }
                }
            })
            .build()

        val window: Window = Window.single()
            .setViewer(player)
            .setTitle(configs.langConfig().classChoiceMenuTitle.toWrapper())
            .setGui(gui)
            .build()

        window.open()
    }

    fun itemTab(player: Player) {
        val items = ItemsController.itemsMap.values.map { TabItem(it) }

        val gui = PagedGui.items()
            .setStructure(
                "X X X X X X X X X",
                "X X X X X X X X X",
                "X X X X X X X X X",
                "X X X X X X X X X",
                "X X X X X X X X X",
                ". < . . . . . > ."
            )
            .addIngredient('X', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
            .addIngredient('<', object: PageItem(false) {
                override fun getItemProvider(gui: PagedGui<*>): ItemProvider {
                    val builder = ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                    builder.setDisplayName("<green>Предыдущая страница</green>".toWrapper()).addLoreLines(
                        if (gui.hasPreviousPage())
                            "Перейти на страницу: " + gui.currentPage + "/" + gui.pageAmount
                        else "Больше нет страниц"
                    )
                    return builder
                }
            })
            .addIngredient('>', object: PageItem(true) {
                override fun getItemProvider(gui: PagedGui<*>): ItemProvider {
                    val builder = ItemBuilder(Material.GREEN_STAINED_GLASS_PANE)
                    builder.setDisplayName("<green>Следующая страница</green>".toWrapper()).addLoreLines(
                        if (gui.hasNextPage())
                            "Перейти на страницу: " + (gui.currentPage + 2) + "/" + gui.pageAmount
                        else "Больше нет страниц"
                    )
                    return builder
                }
            })
            .setContent(items)
            .build()

        val window: Window = Window.single()
            .setViewer(player)
            .setTitle(configs.langConfig().itemsMenuTitle.toWrapper())
            .setGui(gui)
            .build()

        window.open()
    }

    fun traderMenu(player: Player, tradeOffers: List<TradeOffer>) {
        val items = tradeOffers.map {
            ShopItem(it)
        }

        val gui = PagedGui.items()
            .setStructure(
                "T T T T T T T T T",
                "T T T T T T T T T",
                ". < . . . . . > ."
            )
            .addIngredient('#', SimpleItem(ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName("")))
            .addIngredient('T', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
            .addIngredient('<', object: PageItem(false) {
                override fun getItemProvider(gui: PagedGui<*>): ItemProvider {
                    val builder = ItemBuilder(Material.PAPER)
                    builder.setDisplayName("<green>Предыдущая страница</green>".toWrapper())

                    if(gui.hasPreviousPage()) {
                        builder.setCustomModelData(250)
                        builder.addLoreLines("Перейти на страницу: " + gui.currentPage + "/" + gui.pageAmount)
                    } else {
                        builder.setCustomModelData(251)
                        builder.addLoreLines("Вы на первой странице")
                    }

                    return builder
                }
            })
            .addIngredient('>', object: PageItem(true) {
                override fun getItemProvider(gui: PagedGui<*>): ItemProvider {
                    val builder = ItemBuilder(Material.PAPER)
                    builder.setDisplayName("<green>Следующая страница</green>".toWrapper())

                    if(gui.hasNextPage()) {
                        builder.setCustomModelData(252)
                        builder.addLoreLines("Перейти на страницу: " + (gui.currentPage + 2) + "/" + gui.pageAmount)
                    } else {
                        builder.setCustomModelData(253)
                        builder.addLoreLines("Вы на последней странице")
                    }

                    return builder
                }
            })
            .setContent(items)
            .build()

        val window: Window = Window.single()
            .setViewer(player)
            .setTitle(configs.langConfig().traderMenuTitle.toWrapper())
            .setGui(gui)
            .build()

        window.open()
    }

    fun artifactsMenu(player: Player, artifacts: List<Artifact>) {
        val items = artifacts.map {
            ArtifactItem(it)
        }

        val gui = PagedGui.items()
            .setStructure(
                "T T T T T T T T T",
                "T T T T T T T T T",
                "T T T T T T T T T",
                "T T T T T T T T T",
                "T T T T T T T T T",
                ". < . . . . . > ."
            )
            .addIngredient('T', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
            .addIngredient('<', object: PageItem(false) {
                override fun getItemProvider(gui: PagedGui<*>): ItemProvider {
                    val builder = ItemBuilder(Material.PAPER)
                    builder.setDisplayName("<green>Предыдущая страница</green>".toWrapper())

                    if(gui.hasPreviousPage()) {
                        builder.setCustomModelData(250)
                        builder.addLoreLines("Перейти на страницу: " + gui.currentPage + "/" + gui.pageAmount)
                    } else {
                        builder.setCustomModelData(251)
                        builder.addLoreLines("Вы на первой странице")
                    }

                    return builder
                }
            })
            .addIngredient('>', object: PageItem(true) {
                override fun getItemProvider(gui: PagedGui<*>): ItemProvider {
                    val builder = ItemBuilder(Material.PAPER)
                    builder.setDisplayName("<green>Следующая страница</green>".toWrapper())

                    if(gui.hasNextPage()) {
                        builder.setCustomModelData(252)
                        builder.addLoreLines("Перейти на страницу: " + (gui.currentPage + 2) + "/" + gui.pageAmount)
                    } else {
                        builder.setCustomModelData(253)
                        builder.addLoreLines("Вы на последней странице")
                    }

                    return builder
                }
            })
            .setContent(items)
            .build()

        val window: Window = Window.single()
            .setViewer(player)
            .setTitle(configs.langConfig().artifactsMenuTitle.toWrapper())
            .setGui(gui)
            .build()

        window.open()
    }
}