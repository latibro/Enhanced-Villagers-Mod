package com.latibro.minecraft.villager.mixin;

import com.latibro.minecraft.villager.Constants;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerDataHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.trading.MerchantOffers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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

    @Override
    protected int getVillagerLevel() {
        return getVillagerData().getLevel();
    }

    /* *************** */

    @Inject(method = "startTrading", at = @At("HEAD"))
    private void mixinStartTrading(Player player, CallbackInfo callbackInfo) {
        //TODO this is only for debugging
        Constants.LOG.info("Start trading {} {}", this, player);
    }

    @Inject(method = "stopTrading", at = @At("HEAD"))
    private void mixinStopTrading(CallbackInfo info) {
        //TODO this is only for debugging
        Constants.LOG.info("Stop trading {}", this);
    }

}
