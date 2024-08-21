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

    @SerialName("class_choice_menu_title")
    val classChoiceMenuTitle: String = "Выберите ваш класс:",
)
