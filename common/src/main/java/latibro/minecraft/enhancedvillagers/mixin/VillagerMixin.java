package latibro.minecraft.enhancedvillagers.mixin;

import latibro.minecraft.enhancedvillagers.trade.TradeOffersManager;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerDataHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Villager.class)
public abstract class VillagerMixin extends AbstractVillager implements Merchant, VillagerDataHolder {

    protected VillagerMixin(EntityType<? extends AbstractVillager> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "setOffers", at = @At("HEAD"), cancellable = true)
    public void mixinSetOffers(MerchantOffers merchantOffers, CallbackInfo ci) {
        System.out.println("XXX: Cancel set offers " + this);
        ci.cancel();
    }

    @Override
    public MerchantOffers getOffers() {
        System.out.println("XXX: Getting offers " + this + " " + isClientSide());
        MerchantOffers offers = new TradeOffersManager(null).getOffers();
        offers.forEach(offer ->
                System.out.println("XXX: Getting offers 2 - trade " + offer.getCostA() + " " + offer.getCostB() + " " + offer.getResult())
        );
        //setOffers(offers); // Done to satisfy original code that access offers direct
        return offers;
    }

    @Override
    public void notifyTrade(MerchantOffer offer) {
        System.out.println("XXX: Notify trade " + this);
        super.notifyTrade(offer);

        getInventory().removeItemType(offer.getResult().getItem(), offer.getResult().getCount());
        getInventory().addItem(offer.getCostA());
        if (!offer.getCostA().isEmpty()) {
            getInventory().addItem(offer.getCostB());
        }

        MerchantMenu merchantMenu = (MerchantMenu) getTradingPlayer().containerMenu;
        MerchantOffers merchantOffers = getOffers();
        getTradingPlayer().sendMerchantOffers(merchantMenu.containerId, merchantOffers, getVillagerData().getLevel(), getVillagerXp(), showProgressBar(), canRestock());
    }

    @Override
    protected void updateTrades() {
        System.out.println("XXX: Update trades " + this);
    }

    @Inject(method = "startTrading", at = @At("HEAD"))
    private void mixinStartTrading(Player player, CallbackInfo callbackInfo) {
        System.out.println("XXX: Start trading " + this + " " + player);
    }

    @Inject(method = "stopTrading", at = @At("HEAD"))
    private void mixinStopTrading(CallbackInfo info) {
        System.out.println("XXX: Stop trading " + this);
    }

    @Redirect(method = "*", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/npc/Villager;offers:Lnet/minecraft/world/item/trading/MerchantOffers;", opcode = Opcodes.GETFIELD))
    private MerchantOffers mixinFieldGetOffers(Villager villager) {
        System.out.println("XXX: Get offer field redirect " + this);
        return villager.getOffers();
    }

    @Redirect(method = "*", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/npc/Villager;offers:Lnet/minecraft/world/item/trading/MerchantOffers;", opcode = Opcodes.PUTFIELD))
    private void mixinFieldSetOffers(Villager villager, MerchantOffers offers) {
        System.out.println("XXX: Set offer field redirect " + this);
        villager.setOffers(offers);
    }

    @Redirect(method = "customServerAiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/npc/Villager;stopTrading()V"))
    private void mixinCancelStopTradingUnemployedVillager(Villager villager) {
        System.out.println("XXX: Cancel stop trading for unemployed villager " + this);
        // Cancel stopTrading() for unemployed villager
        // Allowing all villagers to trade
    }

}