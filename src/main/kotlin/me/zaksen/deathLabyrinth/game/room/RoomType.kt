package me.zaksen.deathLabyrinth.game.room

import kotlinx.serialization.Serializable
import me.zaksen.deathLabyrinth.game.room.reward.RewardStategy

@Serializable
enum class RoomType(val reward: RewardStategy) {
    NORMAL(object: RewardStategy{
        override fun generate(): Int {
            return 10 + (4 * RoomController.actualRoomNumber)
        }
    }),
    ELITE(object: RewardStategy{
        override fun generate(): Int {
            return 12 + (6 * RoomController.actualRoomNumber)
        }
    }),
    DOUBLE_ELITE(object: RewardStategy{
        override fun generate(): Int {
            return 15 + (8 * RoomController.actualRoomNumber)
        }
    }),
    SHOP(object: RewardStategy{
        override fun generate(): Int {
            return 0
        }
    }),
    FORGE(object: RewardStategy{
        override fun generate(): Int {
            return 0
        }
    }),
    BOILER_ROOM(object: RewardStategy{
        override fun generate(): Int {
            return 0
        }
    }),
    SKILL_FORGE(object: RewardStategy{
        override fun generate(): Int {
            return 0
        }
    }),
    TREASURE(object: RewardStategy{
        override fun generate(): Int {
            return 20 + (10 * RoomController.actualRoomNumber)
        }
    }),
    CHALLENGE(object: RewardStategy{
        override fun generate(): Int {
            return 10 + (6 * RoomController.actualRoomNumber)
        }
    }),
    BOSS(object: RewardStategy{
        override fun generate(): Int {
            return 32 + (64 * RoomController.bossRoomCompleted)
        }
    }),
    FINAL_BOSS(object: RewardStategy{
        override fun generate(): Int {
            return 0
        }
    })
}