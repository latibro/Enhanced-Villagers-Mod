package latibro.minecraft.enhancedvillagers.mixin;

import latibro.minecraft.enhancedvillagers.inventoryinspector.villagerinventory.InventoryMerchantOffer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractVillager.class)
public abstract class AbstractVillagerMixin {

    @Shadow
    public abstract SimpleContainer getInventory();

    @Shadow
    @Nullable
    public abstract Player getTradingPlayer();

    @Shadow
    public abstract MerchantOffers getOffers();

    @Shadow
    public abstract int getVillagerXp();

    @Shadow
    public abstract boolean showProgressBar();

    @Inject(method = "getOffers", at = @At("RETURN"))
    private void mixinGetOffers(CallbackInfoReturnable<MerchantOffers> callbackInfo) {
        System.out.println("XXX: Getting offers");
        System.out.println("XXX: Getting offers " + this + " " + callbackInfo.getReturnValue());
        callbackInfo.getReturnValue().forEach(offer ->
                System.out.println("XXX: Getting offers - trade " + offer.getResult())
        );
        callbackInfo.getReturnValue().replaceAll(offer -> {
            if (offer instanceof InventoryMerchantOffer) {
                return offer;
            }
            return new InventoryMerchantOffer(offer, getInventory());
        });
    }

    @Inject(method = "notifyTrade", at = @At("TAIL"))
    private void mixinNotifyTrade(MerchantOffer merchantOffer, CallbackInfo callbackInfo) {
        System.out.println("XXX: notify trade");

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

}