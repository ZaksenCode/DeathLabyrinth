package me.zaksen.deathLabyrinth.entity

import me.zaksen.deathLabyrinth.entity.creeper.SizzleEntity
import me.zaksen.deathLabyrinth.entity.pig.PiggyBankEntity
import me.zaksen.deathLabyrinth.entity.silverfish.BigMouseEntity
import me.zaksen.deathLabyrinth.entity.silverfish.MouseEntity
import me.zaksen.deathLabyrinth.entity.skeleton.SkeletonArcherEntity
import me.zaksen.deathLabyrinth.entity.skeleton.SkeletonMinerEntity
import me.zaksen.deathLabyrinth.entity.skeleton.SkeletonTraderEntity
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
        entities["skeleton_miner"] = SkeletonMinerEntity::class.java
        entities["skeleton_trader"] = SkeletonTraderEntity::class.java
        entities["mouse"] = MouseEntity::class.java
        entities["big_mouse"] = BigMouseEntity::class.java
        entities["sizzle"] = SizzleEntity::class.java
        entities["piggy_bank"] = PiggyBankEntity::class.java
    }
}