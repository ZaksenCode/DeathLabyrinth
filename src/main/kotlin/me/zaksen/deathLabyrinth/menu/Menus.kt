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
import me.zaksen.deathLabyrinth.menu.item.util.NextPageItem
import me.zaksen.deathLabyrinth.menu.item.util.PreviousPageItem
import me.zaksen.deathLabyrinth.trading.TradeOffer
import me.zaksen.deathLabyrinth.util.*
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.format.TextColor
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
                    return ItemBuilder(Material.EXPERIENCE_BOTTLE).setDisplayName(
                        "class.mage.name".asTranslate().color(TextColor.color(30,144,255)).toWrapper()
                    )
                }

                override fun handleClick(cilck: ClickType, player: Player, event: InventoryClickEvent) {
                    val playerData = GameController.players[player]

                    if(playerData != null) {
                        playerData.playerClass = MageClass()

                        "text.game.choice_class".asTranslate(player.name(), playerData.playerClass!!.getClassName())
                            .color(TextColor.color(50,205,50))
                            .broadcast()
                        player.closeInventory()
                        GameController.checkClasses()
                    }
                }
            })
            .addIngredient('W', object: AbstractItem(){
                override fun getItemProvider(): ItemProvider {
                    return ItemBuilder(Material.IRON_SWORD).setDisplayName(
                        "class.warrior.name".asTranslate().color(TextColor.color(220,20,60)).toWrapper()
                    )
                }

                override fun handleClick(cilck: ClickType, player: Player, event: InventoryClickEvent) {
                    val playerData = GameController.players[player]
                    if(playerData != null) {
                        playerData.playerClass = WarriorClass()

                        "text.game.choice_class".asTranslate(player.name(), playerData.playerClass!!.getClassName())
                            .color(TextColor.color(50,205,50))
                            .broadcast()
                        player.closeInventory()
                        GameController.checkClasses()
                    }
                }
            })
            .build()

        val window: Window = Window.single()
            .setViewer(player)
            .setTitle("ui.class_choice.title".asTranslate().toWrapper())
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
            .addIngredient('<', PreviousPageItem())
            .addIngredient('>', NextPageItem())
            .setContent(items)
            .build()

        val window: Window = Window.single()
            .setViewer(player)
            .setTitle("ui.item_tab.title".asTranslate().toWrapper())
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
            .addIngredient('<', PreviousPageItem())
            .addIngredient('>', NextPageItem())
            .setContent(items)
            .build()

        val window: Window = Window.single()
            .setViewer(player)
            .setTitle("ui.trader_menu.title".asTranslate().color(TextColor.color(255, 255, 255)).font(Key.key("dl:menus")).toWrapper())
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
            .addIngredient('<', PreviousPageItem())
            .addIngredient('>', NextPageItem())
            .setContent(items)
            .build()

        val window: Window = Window.single()
            .setViewer(player)
            .setTitle("ui.artifacts_menu.title".asTranslate().color(TextColor.color(255, 255, 255)).font(Key.key("dl:menus")).toWrapper())
            .setGui(gui)
            .build()

        window.open()
    }
}