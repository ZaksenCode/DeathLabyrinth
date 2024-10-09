package me.zaksen.deathLabyrinth.item.weapon

import me.zaksen.deathLabyrinth.util.asTranslate
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor

enum class WeaponType(val displayName: Component) {
    DAGGER("item.weapon.type.dagger".asTranslate().color(TextColor.color(255, 0, 255))),
    SWORD("item.weapon.type.sword".asTranslate().color(TextColor.color(255, 0, 255))),
    HAMMER("item.weapon.type.hammer".asTranslate().color(TextColor.color(255, 0, 255))),
    SPEAR("item.weapon.type.spear".asTranslate().color(TextColor.color(255, 0, 255))),

    ATTACK_STAFF("item.weapon.type.staff_attack".asTranslate().color(TextColor.color(255, 0, 255))),
    MISC_STAFF("item.weapon.type.staff_other".asTranslate().color(TextColor.color(255, 0, 255)))
}