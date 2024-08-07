package com.latibro.minecraft.villager.mixin;

import com.latibro.minecraft.villager.Constants;
import com.latibro.minecraft.villager.trade.TradeOffersManager;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractVillager.class)
public abstract class AbstractVillagerMixin implements InventoryCarrier, Merchant {

    /**
     * @author Latibro
     * @reason Replacing trade system
     */
    @Overwrite
    public @NotNull MerchantOffers getOffers() {
        Constants.LOG.info("Getting offers {} {}", this, isClientSide());
        MerchantOffers offers = new TradeOffersManager((AbstractVillager) (Object) this).getOffers();
        offers.forEach(offer -> Constants.LOG.info("Getting offers 2 - trade {} {} {}",
                                                   offer.getCostA(),
                                                   offer.getCostB(),
                                                   offer.getResult()
                       )
        );
        //setOffers(offers); // Done to satisfy original code that access offers direct
        return offers;
    }

    /**
     * @author Latibro
     * @reason Replacing trade system
     */
    @Inject(method = "notifyTrade", at = @At("TAIL"))
    private void mixinNotifyTrade(MerchantOffer offer, CallbackInfo callbackInfo) {
        Constants.LOG.info("Notify trade {} {}", this, this.getInventory());

        getInventory().removeItemType(offer.getResult().getItem(), offer.getResult().getCount());
        getInventory().addItem(offer.getCostA());
        getInventory().addItem(offer.getCostB());

        MerchantMenu merchantMenu = (MerchantMenu) getTradingPlayer().containerMenu;
        MerchantOffers merchantOffers = getOffers();
        getTradingPlayer().sendMerchantOffers(
                merchantMenu.containerId,
                merchantOffers,
                getVillagerLevel(),
                getVillagerXp(),
                showProgressBar(),
                canRestock()
        );
    }

    /**
     * @author Latibro
     * @reason Replacing trade system
     */
    @Inject(method = "addOffersFromItemListings", at = @At("HEAD"), cancellable = true)
    protected void mixinAddOffersFromItemListings(
            MerchantOffers merchantOffers,
            VillagerTrades.ItemListing[] itemListings,
            int i,
            CallbackInfo ci
    ) {
        // Cancel original code
        ci.cancel();
    }

    protected abstract int getVillagerLevel();

}
