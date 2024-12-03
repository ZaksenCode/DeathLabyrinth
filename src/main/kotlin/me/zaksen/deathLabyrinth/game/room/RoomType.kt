package me.zaksen.deathLabyrinth.game.room

import kotlinx.serialization.Serializable
import me.zaksen.deathLabyrinth.game.room.reward.RewardStrategy

@Serializable
enum class RoomType(val reward: RewardStrategy) {
    NORMAL(object: RewardStrategy{
        override fun generate(): Int {
            // FIXME - Room controller now didn't operate this
            // return 25 + (5 * RoomController.actualRoomNumber)
            return 25
        }
    }),
    ELITE(object: RewardStrategy{
        override fun generate(): Int {
            // FIXME - Room controller now didn't operate this
            // 30 + (10 * RoomController.actualRoomNumber)
            return 30
        }
    }),
    DOUBLE_ELITE(object: RewardStrategy{
        override fun generate(): Int {
            // FIXME - Room controller now didn't operate this
            // 45 + (15 * RoomController.actualRoomNumber)
            return 45
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
            // FIXME - Room controller now didn't operate this
            // 30 + (10 * RoomController.actualRoomNumber)
            return 30
        }
    }),
    CHALLENGE(object: RewardStrategy{
        override fun generate(): Int {
            // FIXME - Room controller now didn't operate this
            // 40 + (10 * RoomController.actualRoomNumber)
            return 40
        }
    }),
    BOSS(object: RewardStrategy{
        override fun generate(): Int {
            // FIXME - Room controller now didn't operate this
            // 250 + (500 * RoomController.bossRoomCompleted)
            return 250
        }
    }),
    FINAL_BOSS(object: RewardStrategy{
        override fun generate(): Int {
            return 0
        }
    })
}