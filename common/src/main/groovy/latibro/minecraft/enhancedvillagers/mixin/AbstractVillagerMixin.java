package latibro.minecraft.enhancedvillagers.mixin;

import latibro.minecraft.enhancedvillagers.inventory.VillagerInventory;
import latibro.minecraft.enhancedvillagers.trade.TradeOffersManager;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractVillager.class)
public abstract class AbstractVillagerMixin implements InventoryCarrier, Merchant {

    private final VillagerInventory villagerInventory = new VillagerInventory((AbstractVillager) (Object) this);

    /**
     * @author Latibro
     * @reason Replacing the villagers inventory
     */
    @Overwrite
    public @NotNull SimpleContainer getInventory() {
        return this.villagerInventory;
    }

    /**
     * @author Latibro
     * @reason Make suse direct field access to "inventory" is redirected to "getInventory()"
     */
    @Redirect(method = "*", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/npc/AbstractVillager;inventory:Lnet/minecraft/world/SimpleContainer;", opcode = Opcodes.GETFIELD))
    private SimpleContainer mixinFieldGetInventory(AbstractVillager villager) {
        System.out.println("XXX: Get inventory field redirect " + this);
        return villager.getInventory();
    }

    /**
     * @author Latibro
     * @reason Replacing trade system
     */
    @Overwrite
    public @NotNull MerchantOffers getOffers() {
        System.out.println("XXX: Getting offers " + this + " " + isClientSide());
        MerchantOffers offers = new TradeOffersManager((AbstractVillager) (Object) this).getOffers();
        offers.forEach(offer ->
                System.out.println("XXX: Getting offers 2 - trade " + offer.getCostA() + " " + offer.getCostB() + " " + offer.getResult())
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
        System.out.println("XXX: Notify trade " + this + this.getInventory());

        getInventory().removeItemType(offer.getResult().getItem(), offer.getResult().getCount());
        getInventory().addItem(offer.getCostA());
        getInventory().addItem(offer.getCostB());

        MerchantMenu merchantMenu = (MerchantMenu) getTradingPlayer().containerMenu;
        MerchantOffers merchantOffers = getOffers();
        getTradingPlayer().sendMerchantOffers(merchantMenu.containerId, merchantOffers, getVillagerLevel(), getVillagerXp(), showProgressBar(), canRestock());
    }

    /**
     * @author Latibro
     * @reason Replacing trade system
     */
    @Inject(method = "addOffersFromItemListings", at = @At("HEAD"), cancellable = true)
    protected void mixinAddOffersFromItemListings(MerchantOffers merchantOffers, VillagerTrades.ItemListing[] itemListings, int i, CallbackInfo ci) {
        // Cancel original code
        ci.cancel();
    }

    protected abstract int getVillagerLevel();

    @Shadow
    public abstract boolean isTrading();

}