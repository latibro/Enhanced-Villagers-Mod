package net.examplemod.mixin;

import net.minecraft.world.entity.npc.Villager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Villager.class)
public class MixinVillager {
    @Inject(method = "startTrading", at = @At("HEAD"))
    private void mixinStartTrading(CallbackInfo info) {
        System.out.println("XXX: Start trading");
    }

    @Inject(method = "updateTrades", at = @At("HEAD"))
    private void mixinUpdateTrades(CallbackInfo info) {
        System.out.println("XXX: Update trades");
    }

}