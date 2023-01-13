package net.examplemod.mixin;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.trading.Merchant;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Merchant.class)
public class MixinMerchant {
    @Inject(method = "openTradingScreen", at = @At("HEAD"))
    private void onOpenTradingScreen(Player player, Component component, int i, CallbackInfo info) {
        System.out.println("XXX: Open trading screen");
    }
}