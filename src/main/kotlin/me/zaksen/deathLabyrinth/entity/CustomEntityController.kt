package me.zaksen.deathLabyrinth.entity

import net.minecraft.world.entity.Entity

object CustomEntityController {

    val entities: MutableMap<String, Class<out Entity>> = mutableMapOf()

    init {
        entities["bone_wolf"] = BoneWolfEntity::class.java
        entities["big_bone_wolf"] = BigBoneWolfEntity::class.java
    }
}