package com.latibro.minecraft.villager.mixin;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WanderingTrader.class)
public abstract class IncpectableWanderingTraderMixin extends IncpectableAbstractVillagerMixin {

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
