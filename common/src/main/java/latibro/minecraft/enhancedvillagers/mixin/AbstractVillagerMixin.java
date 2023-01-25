package latibro.minecraft.enhancedvillagers.mixin;

import latibro.minecraft.enhancedvillagers.trade.TradeOffersManager;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractVillager.class)
public abstract class AbstractVillagerMixin implements InventoryCarrier, Merchant {

    @Override
    //@Overwrite()
    public MerchantOffers getOffers() {
        System.out.println("XXX: Getting offers " + this + " " + isClientSide());
        MerchantOffers offers = new TradeOffersManager((AbstractVillager) (Object) this).getOffers();
        offers.forEach(offer ->
                System.out.println("XXX: Getting offers 2 - trade " + offer.getCostA() + " " + offer.getCostB() + " " + offer.getResult())
        );
        //setOffers(offers); // Done to satisfy original code that access offers direct
        return offers;
    }

    @Inject(method = "notifyTrade", at = @At("TAIL"))
    private void mixinNotifyTrade(MerchantOffer offer, CallbackInfo callbackInfo) {
        System.out.println("XXX: Notify trade " + this);

        getInventory().removeItemType(offer.getResult().getItem(), offer.getResult().getCount());
        getInventory().addItem(offer.getCostA());
        if (!offer.getCostA().isEmpty()) {
            getInventory().addItem(offer.getCostB());
        }

        MerchantMenu merchantMenu = (MerchantMenu) getTradingPlayer().containerMenu;
        MerchantOffers merchantOffers = getOffers();
        getTradingPlayer().sendMerchantOffers(merchantMenu.containerId, merchantOffers, getVillagerLevel(), getVillagerXp(), showProgressBar(), canRestock());
    }

    @Shadow
    protected abstract void updateTrades();

    @Inject(method = "addOffersFromItemListings", at = @At("HEAD"), cancellable = true)
    protected void mixinAddOffersFromItemListings(MerchantOffers merchantOffers, VillagerTrades.ItemListing[] itemListings, int i, CallbackInfo ci) {
        // Cancel original code
        ci.cancel();
    }

    protected abstract int getVillagerLevel();

    @Shadow
    public abstract boolean isTrading();

}