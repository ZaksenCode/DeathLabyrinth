package me.zaksen.deathLabyrinth.entity

import me.zaksen.deathLabyrinth.entity.skeleton.SkeletonArcherEntity
import me.zaksen.deathLabyrinth.entity.skeleton.SkeletonWarriorEntity
import me.zaksen.deathLabyrinth.entity.wolf.BigBoneWolfEntity
import me.zaksen.deathLabyrinth.entity.wolf.BoneWolfEntity
import net.minecraft.world.entity.Entity

object EntityController {

    val entities: MutableMap<String, Class<out Entity>> = mutableMapOf()

    init {
        entities["bone_wolf"] = BoneWolfEntity::class.java
        entities["big_bone_wolf"] = BigBoneWolfEntity::class.java
        entities["skeleton_archer"] = SkeletonArcherEntity::class.java
        entities["skeleton_warrior"] = SkeletonWarriorEntity::class.java
    }
}