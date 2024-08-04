package com.latibro.minecraft.villager.mixin;

import com.latibro.minecraft.villager.Constants;
import com.latibro.minecraft.villager.platform.Services;
import com.latibro.minecraft.villager.platform.services.RegistryService;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerDataHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Villager.class)
public abstract class VillagerMixin extends AbstractVillagerMixin implements VillagerDataHolder {

    /**
     * @author Latibro
     * @reason Replacing trade system
     */
    @Inject(method = "setOffers", at = @At("HEAD"), cancellable = true)
    public void mixinSetOffers(MerchantOffers merchantOffers, CallbackInfo ci) {
        Constants.LOG.info("Cancel set offers {}", this);
        // Do nothing
        ci.cancel();
    }

    @Override
    protected int getVillagerLevel() {
        return getVillagerData().getLevel();
    }

    /**
     * @author Latibro
     * @reason Cancelling stopTrading() if villager is unemployed, as we want unemployed villagers to be able to trade
     */
    @Redirect(
            method = "customServerAiStep",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/npc/Villager;stopTrading()V")
    )
    private void mixinCancelStopTradingUnemployedVillager(Villager villager) {
        Constants.LOG.info("Cancel stop trading for unemployed villager {}", this);
        // Cancel stopTrading() for unemployed villager
        // Allowing all villagers to trade
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
        if (itemstack.getItem() ==villagerIntentoryInspectorItem && !isTrading()) {
            cir.setReturnValue(InteractionResult.PASS);
        }
    }

    /* *************** */

    @Inject(method = "startTrading", at = @At("HEAD"))
    private void mixinStartTrading(Player player, CallbackInfo callbackInfo) {
        Constants.LOG.info("Start trading {} {}", this, player);
    }

    @Inject(method = "stopTrading", at = @At("HEAD"))
    private void mixinStopTrading(CallbackInfo info) {
        Constants.LOG.info("Stop trading {}", this);
    }

}
