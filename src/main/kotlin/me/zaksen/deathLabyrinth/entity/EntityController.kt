package me.zaksen.deathLabyrinth.entity

import me.zaksen.deathLabyrinth.entity.boss.skeleton.BomberEntity
import me.zaksen.deathLabyrinth.entity.creeper.SizzleEntity
import me.zaksen.deathLabyrinth.entity.enderman.EndermanEntity
import me.zaksen.deathLabyrinth.entity.enderman.SpeedyEndermanEntity
import me.zaksen.deathLabyrinth.entity.enderman.StrongEndermanEntity
import me.zaksen.deathLabyrinth.entity.husk.DeceasedEntity
import me.zaksen.deathLabyrinth.entity.iron_golem.BigIronGolemEntity
import me.zaksen.deathLabyrinth.entity.iron_golem.IronGolemEntity
import me.zaksen.deathLabyrinth.entity.pig.PiggyBankEntity
import me.zaksen.deathLabyrinth.entity.silverfish.BigMouseEntity
import me.zaksen.deathLabyrinth.entity.silverfish.MouseEntity
import me.zaksen.deathLabyrinth.entity.skeleton.*
import me.zaksen.deathLabyrinth.entity.villager.VillagerOneEntity
import me.zaksen.deathLabyrinth.entity.villager.VillagerThreeEntity
import me.zaksen.deathLabyrinth.entity.villager.VillagerTwoEntity
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
        entities["mouse"] = MouseEntity::class.java
        entities["big_mouse"] = BigMouseEntity::class.java
        entities["sizzle"] = SizzleEntity::class.java
        entities["piggy_bank"] = PiggyBankEntity::class.java
        entities["enderman"] = EndermanEntity::class.java
        entities["speedy_enderman"] = SpeedyEndermanEntity::class.java
        entities["strong_enderman"] = StrongEndermanEntity::class.java
        entities["iron_golem"] = IronGolemEntity::class.java
        entities["big_iron_golem"] = BigIronGolemEntity::class.java
        entities["villager_1"] = VillagerOneEntity::class.java
        entities["villager_2"] = VillagerTwoEntity::class.java
        entities["villager_3"] = VillagerThreeEntity::class.java
        entities["skeleton"] = SkeletonEntity::class.java
        entities["deceased"] = DeceasedEntity::class.java
        entities["druid"] = DruidEntity::class.java

        // Traders
        entities["skeleton_trader"] = SkeletonTraderEntity::class.java

        // NPC

        // Bosses
        entities["bomber_boss"] = BomberEntity::class.java

        // Final bosses
    }
}