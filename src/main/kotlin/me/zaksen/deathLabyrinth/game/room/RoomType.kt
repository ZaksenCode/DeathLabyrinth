package me.zaksen.deathLabyrinth.game.room

import kotlinx.serialization.Serializable
import me.zaksen.deathLabyrinth.game.room.reward.RewardStrategy

@Serializable
enum class RoomType(val reward: RewardStrategy) {
    START_ROOM(object : RewardStrategy{
        override fun generate(): Int {
            return 0
        }
    }),
    NORMAL(object: RewardStrategy{
        override fun generate(): Int {
            return RoomFloorController.countReward(25)
        }
    }),
    END_ROOM(object : RewardStrategy{
        override fun generate(): Int {
            return 0
        }
    }),
    ELITE(object: RewardStrategy{
        override fun generate(): Int {
            return RoomFloorController.countReward(35)
        }
    }),
    SHOP_START(object: RewardStrategy{
        override fun generate(): Int {
            return 0
        }
    }),
    SHOP_END(object: RewardStrategy{
        override fun generate(): Int {
            return 0
        }
    }),
    FORGE(object: RewardStrategy{
        override fun generate(): Int {
            return 0
        }
    }),
    TREASURE(object: RewardStrategy{
        override fun generate(): Int {
            return RoomFloorController.countReward(25)
        }
    }),
    CHALLENGE(object: RewardStrategy{
        override fun generate(): Int {
            return RoomFloorController.countReward(50)
        }
    }),
    BOSS(object: RewardStrategy{
        override fun generate(): Int {
            return RoomFloorController.countReward(250)
        }
    }),
    FINAL_BOSS(object: RewardStrategy{
        override fun generate(): Int {
            return 0
        }
    })
}