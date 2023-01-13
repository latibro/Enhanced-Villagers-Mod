package latibro.minecraft.enhancedvillagers.mixin;

import latibro.minecraft.enhancedvillagers.inventoryinspector.villagerinventory.InventoryMerchantOffer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.npc.ClientSideMerchant;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientSideMerchant.class)
public abstract class MixinClientSideMerchant implements Merchant {

    @Shadow
    public abstract SimpleContainer getInventory();

    @Inject(method = "getOffers", at = @At("RETURN"), cancellable = true)
    private void mixinGetOffers(CallbackInfoReturnable<MerchantOffers> callbackInfo) {
        System.out.println("XXX client: Getting offers");
        System.out.println("XXX client: Getting offers x- " + this);
        System.out.println("XXX client: Getting offers y- " + callbackInfo.getReturnValue());
        MerchantOffers offers = new MerchantOffers();
        callbackInfo.getReturnValue().forEach((offer) -> {
            offers.add(new InventoryMerchantOffer(offer, this.getInventory()));
        });
        callbackInfo.setReturnValue(offers);
    }

}