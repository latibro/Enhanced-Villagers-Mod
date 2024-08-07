package com.latibro.minecraft.villager.mixin;

import com.latibro.minecraft.villager.Constants;
import com.latibro.minecraft.villager.platform.Services;
import com.latibro.minecraft.villager.platform.services.RegistryService;
import com.latibro.minecraft.villager.trade.TradeOffersManager;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Villager.class)
public abstract class IncpectableVillagerMixin extends IncpectableAbstractVillagerMixin {

    /**
     * @author Latibro
     * @reason Cancel start trade if used item is inventory inspector
     */
    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    protected void mixinMobInteract(
            Player player,
            InteractionHand hand,
            CallbackInfoReturnable<InteractionResult> cir
    ) {
        super.mixinMobInteract(player, hand, cir);
    }

}
