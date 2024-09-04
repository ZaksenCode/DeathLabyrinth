package me.zaksen.deathLabyrinth.config

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LangConfig(
    @SerialName("reload_success_text")
    val reloadSuccessText: String = "<green>Перезагрузка выполнена!</green>",

    @SerialName("class_game_not_started")
    val classGameNotStarted: String = "<red>Сначала начните игру!</red>",
    @SerialName("class_already_selected")
    val classAlreadySelected: String = "<green>Ваш класс: {class_name}</green>",

    @SerialName("ready")
    val ready: String = "<green>Готов!</green>",
    @SerialName("not_ready")
    val notReady: String = "<red>Не готов!</red>",

    @SerialName("game_starting_title")
    val gameStartingTitle: String = "<green>Начало через: {time}</green>",
    @SerialName("game_starting_stop_title")
    val gameStartingStopTitle: String = "<red>Ожидание прервано!</red>",
    @SerialName("game_Starting_close_class_menu")
    val gameStartingCloseClassMenu: String = "<aqua>Если вы закрыли меню выбора класса до его выбора, его можно открыть снова командой /class</aqua>",

    @SerialName("items_menu_title")
    val itemsMenuTitle: String = "Список предметов",
    @SerialName("class_choice_menu_title")
    val classChoiceMenuTitle: String = "Выберите ваш класс:",
    @SerialName("trader_menu_title")
    val traderMenuTitle: String = "\uE203<white>\uE000",
    @SerialName("artifacts_menu_title")
    val artifactsMenuTitle: String = "\uE203<white>\uE000",
)
