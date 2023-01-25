package latibro.minecraft.enhancedvillagers.mixin;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.item.trading.Merchant;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AbstractVillager.class)
public abstract class AbstractVillagerMixin implements Merchant {

    @Shadow
    public abstract SimpleContainer getInventory();

    /*
    @Accessor("offers")
    @Mutable
    private abstract MerchantOffers mixinFieldGetOffers() {
        System.out.println("XXX: access get offers " + this);
        return getOffers();
    }

    @Accessor("offers")
    @Mutable
    private void mixinFieldSetOffers(MerchantOffers offers) {
        System.out.println("XXX: access set offers " + this);
    }
     */

    /*
    @Redirect(method = "*", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/npc/AbstractVillager;offers:Lnet/minecraft/world/item/trading/MerchantOffers;", opcode = Opcodes.GETFIELD))
    private MerchantOffers mixinFieldGetOffers(Villager villager) {
        System.out.println("XXX: Get offer field redirect " + this);
        return getOffers();
    }

    @Redirect(method = "*", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/npc/AbstractVillager;offers:Lnet/minecraft/world/item/trading/MerchantOffers;", opcode = Opcodes.PUTFIELD))
    private void mixinFieldSetOffers(MerchantOffers offers) {
        System.out.println("XXX: Set offer field redirect " + this);
        setOffers(offers);
    }
     */


/*
    @Inject(method = "getOffers", at = @At("HEAD"), cancellable = true)
    private void mixinGetOffers(CallbackInfoReturnable<MerchantOffers> callbackInfo) {
        System.out.println("XXX: Getting offers " + this + " " + isClientSide());
        //if (!isClientSide()) {
        callbackInfo.setReturnValue(new TradeOffersManager(null).getOffers());
        callbackInfo.getReturnValue().forEach(offer ->
                System.out.println("XXX: Getting offers 2 - trade " + offer.getCostA() + " " + offer.getCostB() + " " + offer.getResult())
        );
        //}
    }
*/

    /*
    @Inject(method = "notifyTrade", at = @At("TAIL"))
    private void mixinNotifyTrade(MerchantOffer merchantOffer, CallbackInfo callbackInfo) {
        System.out.println("XXX: notify trade " + this + " " + merchantOffer);

        getInventory().removeItemType(merchantOffer.getResult().getItem(), merchantOffer.getResult().getCount());
        getInventory().addItem(merchantOffer.getCostA());
        if (!merchantOffer.getCostA().isEmpty()) {
            getInventory().addItem(merchantOffer.getCostB());
        }

        MerchantMenu merchantMenu = (MerchantMenu) getTradingPlayer().containerMenu;
        boolean canRestock = false;
        int villagerLevel = 1; //villager.getVillagerDate().getLevel();

        MerchantOffers merchantOffers = getOffers();
        getTradingPlayer().sendMerchantOffers(merchantMenu.containerId, merchantOffers, villagerLevel, getVillagerXp(), showProgressBar(), canRestock);
        //getTradingPlayer().sendMerchantOffers(merchantMenu.containerId, merchantOffers, merchantMenu.getTraderLevel(), merchantMenu.getTraderXp(), merchantMenu.showProgressBar(), merchantMenu.canRestock());
    }
     */

}