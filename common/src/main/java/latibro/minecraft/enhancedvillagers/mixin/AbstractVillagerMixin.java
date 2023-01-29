package latibro.minecraft.enhancedvillagers.mixin;

import latibro.minecraft.enhancedvillagers.inventory.VillagerInventory;
import latibro.minecraft.enhancedvillagers.trade.TradeOffersManager;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractVillager.class)
public abstract class AbstractVillagerMixin implements InventoryCarrier, Merchant {

    private final VillagerInventory villagerInventory = new VillagerInventory((AbstractVillager) (Object) this);

    @Inject(method = "<init>", at = @At("RETURN"))
    private void mixinConstructor(EntityType entityType, Level level, CallbackInfo ci) {
    }

    @Override
    public SimpleContainer getInventory() {
        return this.villagerInventory;
    }

    @Redirect(method = "*", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/npc/AbstractVillager;inventory:Lnet/minecraft/world/SimpleContainer;", opcode = Opcodes.GETFIELD))
    private SimpleContainer mixinFieldGetInventory(AbstractVillager villager) {
        System.out.println("XXX: Get inventory field redirect " + this);
        return villager.getInventory();
    }

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
        System.out.println("XXX: Notify trade " + this + this.getInventory());

        getInventory().removeItemType(offer.getResult().getItem(), offer.getResult().getCount());
        getInventory().addItem(offer.getCostA());
        getInventory().addItem(offer.getCostB());

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