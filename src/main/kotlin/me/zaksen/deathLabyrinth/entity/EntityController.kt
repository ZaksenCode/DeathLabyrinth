package me.zaksen.deathLabyrinth.entity

import me.zaksen.deathLabyrinth.entity.boss.skeleton.BomberEntity
import me.zaksen.deathLabyrinth.entity.creeper.SizzleEntity
import me.zaksen.deathLabyrinth.entity.enderman.EndermanEntity
import me.zaksen.deathLabyrinth.entity.enderman.SpeedyEndermanEntity
import me.zaksen.deathLabyrinth.entity.enderman.StrongEndermanEntity
import me.zaksen.deathLabyrinth.entity.final_boss.warden.AncientEntity
import me.zaksen.deathLabyrinth.entity.husk.DeceasedEntity
import me.zaksen.deathLabyrinth.entity.iron_golem.BigIronGolemEntity
import me.zaksen.deathLabyrinth.entity.iron_golem.IronGolemEntity
import me.zaksen.deathLabyrinth.entity.teleport.TeleportPoint
import me.zaksen.deathLabyrinth.entity.pig.PiggyBankEntity
import me.zaksen.deathLabyrinth.entity.pillager.PillagerEntity
import me.zaksen.deathLabyrinth.entity.ravager.BigRavagerEntity
import me.zaksen.deathLabyrinth.entity.ravager.RavagerEntity
import me.zaksen.deathLabyrinth.entity.silverfish.BigMouseEntity
import me.zaksen.deathLabyrinth.entity.silverfish.MouseEntity
import me.zaksen.deathLabyrinth.entity.skeleton.*
import me.zaksen.deathLabyrinth.entity.teleport.BomberReturnPoint
import me.zaksen.deathLabyrinth.entity.villager.VillagerTraderEntity
import me.zaksen.deathLabyrinth.entity.vindicator.VindicatorOneEntity
import me.zaksen.deathLabyrinth.entity.vindicator.VindicatorTwoEntity
import me.zaksen.deathLabyrinth.entity.wolf.BigBoneWolfEntity
import me.zaksen.deathLabyrinth.entity.wolf.BoneWolfEntity
import net.minecraft.world.entity.Entity

object EntityController {

    val entities: MutableMap<String, Class<out Entity>> = mutableMapOf()

    init {
        // Util
        entities["teleport_point"] = TeleportPoint::class.java
        entities["bomber_return_point"] = BomberReturnPoint::class.java

        // Common
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
        entities["pillager"] = PillagerEntity::class.java
        entities["vindicator_1"] = VindicatorOneEntity::class.java
        entities["vindicator_2"] = VindicatorTwoEntity::class.java
        entities["ravager"] = RavagerEntity::class.java
        entities["big_ravager"] = BigRavagerEntity::class.java
        entities["skeleton"] = SkeletonEntity::class.java
        entities["deceased"] = DeceasedEntity::class.java
        entities["druid"] = DruidEntity::class.java

        // Traders
        entities["skeleton_trader"] = SkeletonTraderEntity::class.java
        entities["villager_trader"] = VillagerTraderEntity::class.java

        // NPC

        // Bosses
        entities["bomber_boss"] = BomberEntity::class.java

        // Final bosses
        entities["ancient_final_boss"] = AncientEntity::class.java
    }
}