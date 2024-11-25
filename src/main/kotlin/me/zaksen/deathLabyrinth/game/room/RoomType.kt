package me.zaksen.deathLabyrinth.game.room

import kotlinx.serialization.Serializable
import me.zaksen.deathLabyrinth.game.room.reward.RewardStrategy

@Serializable
enum class RoomType(val reward: RewardStrategy) {
    NORMAL(object: RewardStrategy{
        override fun generate(): Int {
            return 25 + (5 * RoomController.actualRoomNumber)
        }
    }),
    ELITE(object: RewardStrategy{
        override fun generate(): Int {
            return 30 + (10 * RoomController.actualRoomNumber)
        }
    }),
    DOUBLE_ELITE(object: RewardStrategy{
        override fun generate(): Int {
            return 45 + (15 * RoomController.actualRoomNumber)
        }
    }),
    SHOP(object: RewardStrategy{
        override fun generate(): Int {
            return 0
        }
    }),
    FORGE(object: RewardStrategy{
        override fun generate(): Int {
            return 0
        }
    }),
    BOILER_ROOM(object: RewardStrategy{
        override fun generate(): Int {
            return 0
        }
    }),
    SKILL_FORGE(object: RewardStrategy{
        override fun generate(): Int {
            return 0
        }
    }),
    TREASURE(object: RewardStrategy{
        override fun generate(): Int {
            return 30 + (10 * RoomController.actualRoomNumber)
        }
    }),
    CHALLENGE(object: RewardStrategy{
        override fun generate(): Int {
            return 40 + (10 * RoomController.actualRoomNumber)
        }
    }),
    BOSS(object: RewardStrategy{
        override fun generate(): Int {
            return 250 + (500 * RoomController.bossRoomCompleted)
        }
    }),
    FINAL_BOSS(object: RewardStrategy{
        override fun generate(): Int {
            return 0
        }
    })
}