package com.latibro.minecraft.villager.mixin;

import com.latibro.minecraft.villager.Constants;
import net.minecraft.world.entity.npc.WanderingTrader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(WanderingTrader.class)
public abstract class WanderingTraderMixin extends AbstractVillagerMixin {

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
        return 1;
    }

}
