package latibro.minecraft.enhancedvillagers.mixin;

import latibro.minecraft.enhancedvillagers.EnhancedVillagersMod;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WanderingTrader.class)
public abstract class WanderingTraderMixin extends AbstractVillagerMixin {

    @Override
    protected int getVillagerLevel() {
        return 1;
    }

    @Override
    protected void updateTrades() {
        System.out.println("XXX: Update trades " + this);
        // Cancel original code
        // Do nothing
    }

    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    protected void mixinMobInteract(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.getItem() == EnhancedVillagersMod.VILLAGER_INVENTORY_INSPECTOR_ITEM.get() && !isTrading()) {
            cir.setReturnValue(InteractionResult.PASS);
        }
    }

}