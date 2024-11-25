package me.zaksen.deathLabyrinth.damage

import me.zaksen.deathLabyrinth.util.asTranslate
import net.kyori.adventure.text.Component

enum class DamageType(
    val displayName: Component
) {
    GENERAL("damage_type.general.name".asTranslate()),
    FIRE("damage_type.fire.name".asTranslate()),
    ICE("damage_type.ice.name".asTranslate()),
    WITHER("damage_type.wither.name".asTranslate()),
    EXPLODE("damage_type.explode.name".asTranslate()),
    SCULK("damage_type.sculk.name".asTranslate())
}