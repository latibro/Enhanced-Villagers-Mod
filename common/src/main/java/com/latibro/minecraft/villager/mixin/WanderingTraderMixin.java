package com.latibro.minecraft.villager.mixin;

import com.latibro.minecraft.villager.Constants;
import com.latibro.minecraft.villager.platform.Services;
import com.latibro.minecraft.villager.platform.services.RegistryService;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WanderingTrader.class)
public abstract class WanderingTraderMixin extends AbstractVillagerMixin {

    @Override
    protected int getVillagerLevel() {
        return 1;
    }

    /**
     * @author Latibro
     * @reason Replacing trade system
     */
    @Overwrite
    public void updateTrades() {
        Constants.LOG.info("Update trades {}", this);
        // Cancel original code
        // Do nothing
    }

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
        ItemStack itemstack = player.getItemInHand(hand);
        var villagerIntentoryInspectorItem = Services.get(RegistryService.class).getItem("villager_inventory_inspector"); //TODO better getter
        if (itemstack.getItem() == villagerIntentoryInspectorItem && !isTrading()) {
            cir.setReturnValue(InteractionResult.PASS);
        }
    }

}
