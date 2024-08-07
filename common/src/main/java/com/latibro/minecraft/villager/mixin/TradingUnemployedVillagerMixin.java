package com.latibro.minecraft.villager.mixin;

import com.latibro.minecraft.villager.Constants;
import net.minecraft.world.entity.npc.Villager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Villager.class)
public abstract class TradingUnemployedVillagerMixin {

    @Shadow
    protected abstract void stopTrading();

    /**
     * @author Latibro
     * @reason Cancelling stopTrading() if villager is unemployed, as we want unemployed villagers to be able to trade
     */
    @Redirect(
            method = "customServerAiStep",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/npc/Villager;stopTrading()V"
            )
    )
    private void mixinCancelStopTradingUnemployedVillager(Villager villager) {
        Constants.LOG.info("Cancel stop trading for unemployed villager {}", this);
        // Cancel stopTrading() for unemployed villager
        // Allowing all villagers to trade
    }

}
