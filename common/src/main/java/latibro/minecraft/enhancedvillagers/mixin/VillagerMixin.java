package latibro.minecraft.enhancedvillagers.mixin;

import latibro.minecraft.enhancedvillagers.EnhancedVillagersMod;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerDataHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffers;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Villager.class)
public abstract class VillagerMixin extends AbstractVillagerMixin implements VillagerDataHolder {

    /**
     * @author Latibro
     * @reason Make suse direct field access to "offers" is redirected to "getOffers()"
     */
    @Redirect(method = "*", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/npc/Villager;offers:Lnet/minecraft/world/item/trading/MerchantOffers;", opcode = Opcodes.GETFIELD))
    private MerchantOffers mixinFieldGetOffers(Villager villager) {
        System.out.println("XXX: Get offer field redirect " + this);
        return villager.getOffers();
    }

    /**
     * @author Latibro
     * @reason Make suse direct field access to "offers" is redirected to "setOffers()"
     */
    @Redirect(method = "*", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/npc/Villager;offers:Lnet/minecraft/world/item/trading/MerchantOffers;", opcode = Opcodes.PUTFIELD))
    private void mixinFieldSetOffers(Villager villager, MerchantOffers offers) {
        System.out.println("XXX: Set offer field redirect " + this);
        villager.setOffers(offers);
    }

    /**
     * @author Latibro
     * @reason Replacing trade system
     */
    @Inject(method = "setOffers", at = @At("HEAD"), cancellable = true)
    public void mixinSetOffers(MerchantOffers merchantOffers, CallbackInfo ci) {
        System.out.println("XXX: Cancel set offers " + this);
        // Do nothing
        ci.cancel();
    }

    @Override
    protected int getVillagerLevel() {
        return getVillagerData().getLevel();
    }

    /**
     * @author Latibro
     * @reason Cancelling stopTrading() if villager is unemployed, as we want unemployed villagers to be able to trade
     */
    @Redirect(method = "customServerAiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/npc/Villager;stopTrading()V"))
    private void mixinCancelStopTradingUnemployedVillager(Villager villager) {
        //System.out.println("XXX: Cancel stop trading for unemployed villager " + this);
        // Cancel stopTrading() for unemployed villager
        // Allowing all villagers to trade
    }

    /**
     * @author Latibro
     * @reason Replacing trade system
     */
    @Overwrite
    public void updateTrades() {
        System.out.println("XXX: Update trades " + this);
        // Cancel original code
        // Do nothing
    }

    /**
     * @author Latibro
     * @reason Cancel start trade if used item is inventory inspector
     */
    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    protected void mixinMobInteract(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.getItem() == EnhancedVillagersMod.VILLAGER_INVENTORY_INSPECTOR_ITEM.get() && !isTrading()) {
            cir.setReturnValue(InteractionResult.PASS);
        }
    }

    /* *************** */

    @Inject(method = "startTrading", at = @At("HEAD"))
    private void mixinStartTrading(Player player, CallbackInfo callbackInfo) {
        System.out.println("XXX: Start trading " + this + " " + player);
    }

    @Inject(method = "stopTrading", at = @At("HEAD"))
    private void mixinStopTrading(CallbackInfo info) {
        System.out.println("XXX: Stop trading " + this);
    }

}