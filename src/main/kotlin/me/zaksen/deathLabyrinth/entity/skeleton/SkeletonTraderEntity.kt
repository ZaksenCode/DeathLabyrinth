package me.zaksen.deathLabyrinth.entity.skeleton

import me.zaksen.deathLabyrinth.entity.trader.Trader
import me.zaksen.deathLabyrinth.entity.trader.TraderType
import me.zaksen.deathLabyrinth.menu.Menus
import me.zaksen.deathLabyrinth.trading.TradeOffer
import net.kyori.adventure.text.format.TextColor
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
import net.minecraft.world.entity.monster.Skeleton
import net.minecraft.world.entity.player.Player
import org.bukkit.Location
import org.bukkit.craftbukkit.CraftWorld

class SkeletonTraderEntity(location: Location): Skeleton(EntityType.SKELETON, (location.getWorld() as CraftWorld).handle),
    Trader {

    private var tradeOffers: List<TradeOffer> = listOf()

    init {
        this.getAttribute(Attributes.MAX_HEALTH)?.baseValue = 50.0
        this.health = 50.0f
        this.customName = Component.literal("Скелет-торговец").withColor(TextColor.color(124, 242, 81).value())
        this.isCustomNameVisible = true

        this.setPos(location.x, location.y, location.z)
    }

    override fun registerGoals() {
        goalSelector.addGoal(1, MeleeAttackGoal(this, 1.0, false))
        goalSelector.addGoal(
            2, LookAtPlayerGoal(
                this,
                Player::class.java, 8.0f
            )
        )
        targetSelector.addGoal(1, HurtByTargetGoal(this,
            Skeleton::class.java,
            SkeletonArcherEntity::class.java,
            SkeletonWarriorEntity::class.java
        ))
    }

    override fun mobInteract(player: Player, hand: InteractionHand): InteractionResult {
        if(target == null) {
            Menus.traderMenu(player.bukkitEntity as org.bukkit.entity.Player, tradeOffers)
        }
        return super.mobInteract(player, hand)
    }

    override fun updateOffers(offers: List<TradeOffer>) {
        tradeOffers = offers
    }

    override fun getTraderType(): TraderType {
        return TraderType.NORMAL
    }

    override fun checkDespawn() { }
}