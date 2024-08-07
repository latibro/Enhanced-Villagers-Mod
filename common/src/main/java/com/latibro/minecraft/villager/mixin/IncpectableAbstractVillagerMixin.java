package com.latibro.minecraft.villager.mixin;

import com.latibro.minecraft.villager.platform.Services;
import com.latibro.minecraft.villager.platform.services.RegistryService;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractVillager.class)
public abstract class IncpectableAbstractVillagerMixin {

    protected void mixinMobInteract(
            Player player,
            InteractionHand hand,
            CallbackInfoReturnable<InteractionResult> cir
    ) {
        ItemStack itemstack = player.getItemInHand(hand);

        var villagerIntentoryInspectorItem =
                Services.get(RegistryService.class).getItem("villager_inventory_inspector"); //TODO better getter

        if (itemstack.getItem() == villagerIntentoryInspectorItem && !this.isTrading()) {
            cir.setReturnValue(InteractionResult.PASS);
        }
    }

    @Shadow
    public abstract boolean isTrading();

}
