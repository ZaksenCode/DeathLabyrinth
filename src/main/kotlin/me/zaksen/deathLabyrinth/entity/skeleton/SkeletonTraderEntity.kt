package me.zaksen.deathLabyrinth.entity.skeleton

import me.zaksen.deathLabyrinth.entity.Trader
import me.zaksen.deathLabyrinth.shop.TradeOffer
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal
import net.minecraft.world.entity.monster.Skeleton
import net.minecraft.world.entity.player.Player
import org.bukkit.Location
import org.bukkit.craftbukkit.CraftWorld

class SkeletonTraderEntity(location: Location): Skeleton(EntityType.SKELETON, (location.getWorld() as CraftWorld).handle), Trader {

    private var tradeOffers: List<TradeOffer> = listOf()

    init {
        this.getAttribute(Attributes.MAX_HEALTH)?.baseValue = 50.0
        this.health = 50.0f
        this.customName = Component.literal("Скелет-торговец")
        this.isCustomNameVisible = true

        this.setPos(location.x, location.y, location.z)
    }

    override fun registerGoals() {
        goalSelector.addGoal(
            1, LookAtPlayerGoal(
                this,
                Player::class.java, 8.0f
            )
        )
    }

    override fun mobInteract(player: Player, hand: InteractionHand): InteractionResult {

        return super.mobInteract(player, hand)
    }

    override fun updateOffers(offers: List<TradeOffer>) {
        tradeOffers = offers
    }

}