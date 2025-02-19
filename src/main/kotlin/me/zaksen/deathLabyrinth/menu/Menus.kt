package me.zaksen.deathLabyrinth.menu

import me.zaksen.deathLabyrinth.artifacts.api.Artifact
import me.zaksen.deathLabyrinth.classes.MageClass
import me.zaksen.deathLabyrinth.classes.WarriorClass
import me.zaksen.deathLabyrinth.config.ConfigContainer
import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.item.ItemsController
import me.zaksen.deathLabyrinth.item.ability.ItemAbility
import me.zaksen.deathLabyrinth.item.ability.ItemAbilityManager
import me.zaksen.deathLabyrinth.item.ability.recipe.Synergy
import me.zaksen.deathLabyrinth.keys.PluginKeys
import me.zaksen.deathLabyrinth.menu.item.*
import me.zaksen.deathLabyrinth.menu.item.util.NextPageItem
import me.zaksen.deathLabyrinth.menu.item.util.PreviousPageItem
import me.zaksen.deathLabyrinth.util.*
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.gui.PagedGui
import xyz.xenondevs.invui.gui.structure.Markers
import xyz.xenondevs.invui.item.Item
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.AbstractItem
import xyz.xenondevs.invui.item.impl.SimpleItem
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

    fun synergies(player: Player, sourceItem: ItemStack) {
        if(!sourceItem.hasItemMeta()) return
        if(!sourceItem.itemMeta.persistentDataContainer.has(PluginKeys.customItemAbilitiesKey)) return
        
        val meta = sourceItem.itemMeta
        
        val items = mutableListOf<Item>()
        
        meta.persistentDataContainer.get(PluginKeys.customItemAbilitiesKey, PersistentDataType.STRING)!!.stringList().forEach {
            val ability = ItemAbilityManager.abilityMap[it] ?: return

            ability.getSynergies().forEach { synergy: Synergy ->
                val inputAbility = ItemAbilityManager.abilityMap[synergy.with] ?: return
                val outputAbility = ItemAbilityManager.abilityMap[synergy.output] ?: return
                items.add(SynergyItem(sourceItem.type, sourceItem.itemMeta.customModelData, ability, inputAbility, outputAbility))
            }
        }

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
            .setTitle("ui.synergies.title".asTranslate().toWrapper())
            .setGui(gui)
            .build()

        window.open()
    }

    fun synergies(player: Player, ability: ItemAbility) {
        val items = mutableListOf<Item>()

        ability.getSynergies().forEach { synergy: Synergy ->
            val inputAbility = ItemAbilityManager.abilityMap[synergy.with] ?: return
            val outputAbility = ItemAbilityManager.abilityMap[synergy.output] ?: return

            items.add(SynergyItem(Material.NETHER_STAR, 0, ability, inputAbility, outputAbility))
        }

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
            .setTitle("ui.synergies.title".asTranslate().toWrapper())
            .setGui(gui)
            .build()

        window.open()
    }

    fun traderMenu(player: Player, items: List<ShopItem>) {

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

    fun blacksmithMenu(player: Player) {
        val sourceSlot = SlotItem()
        val inputSlot = SlotItem()
        val outputSlot = OutputSlot(sourceSlot, inputSlot)

        sourceSlot.setupOutputSlot(outputSlot)
        inputSlot.setupOutputSlot(outputSlot)

        val gui = Gui.normal()
            .setStructure(
                ". . . . . . . . .",
                ". S . . I . . O .",
                ". . . . . . . . ."
            )
            .addIngredient('S', sourceSlot)
            .addIngredient('I', inputSlot)
            .addIngredient('O', outputSlot)
            .build()

        val window: Window = Window.single()
            .setViewer(player)
            .setTitle("ui.blacksmith_menu.title".asTranslate().color(TextColor.color(255, 255, 255)).font(Key.key("dl:menus")).toWrapper())
            .setGui(gui)
            .build()

        window.open()
    }

    fun necromancerMenu(player: Player) {
        val gui = Gui.normal()
            .setStructure(
                ". . . . . . . . .",
                ". . . . R . . . .",
                ". . . . . . . . ."
            )
            .addIngredient('R', ReviewItem())
            .build()

        val window: Window = Window.single()
            .setViewer(player)
            .setTitle("ui.necromancer_menu.title".asTranslate().color(TextColor.color(255, 255, 255)).font(Key.key("dl:menus")).toWrapper())
            .setGui(gui)
            .build()

        window.open()
    }

    fun accessoryMenu(player: Player) {
        val data = GameController.players[player] ?: return

        val gui = Gui.normal()
            .setStructure(
                ". . . . . . . . .",
                ". A . B . C D E .",
                ". . . . . . . . ."
            )
            .addIngredient('A', AccessorySlotItem(10, data).setItemAndCheck(data.accessories.items[10]))
            .addIngredient('B', AccessorySlotItem(12, data).setItemAndCheck(data.accessories.items[12]))
            .addIngredient('C', AccessorySlotItem(14, data).setItemAndCheck(data.accessories.items[14]))
            .addIngredient('D', AccessorySlotItem(15, data).setItemAndCheck(data.accessories.items[15]))
            .addIngredient('E', AccessorySlotItem(16, data).setItemAndCheck(data.accessories.items[16]))
            .build()

        val window: Window = Window.single()
            .setViewer(player)
            .setTitle("ui.accessory.title".asTranslate().color(TextColor.color(255, 255, 255)).font(Key.key("dl:menus")).toWrapper())
            .setGui(gui)
            .build()

        window.open()
    }
}