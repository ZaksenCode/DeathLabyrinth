package me.zaksen.deathLabyrinth.damage

import me.zaksen.deathLabyrinth.util.asTranslate
import net.kyori.adventure.text.Component

enum class DamageType(
    val displayName: Component
) {
    GENERAL("damage_type.general.name".asTranslate()),
    FIRE("damage_type.fire.name".asTranslate()),
    WATER("damage_type.water.name".asTranslate()),
    WITHER("damage_type.wither.name".asTranslate()),
    EXPLODE("damage_type.explode.name".asTranslate()),
    SCULK("damage_type.sculk.name".asTranslate())
}