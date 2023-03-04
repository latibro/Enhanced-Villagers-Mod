package latibro.minecraft.enhancedvillagers.inventoryinspector.villagerinventory

import groovy.transform.CompileStatic
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.SimpleContainer
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.trading.MerchantOffer

@CompileStatic
public class InventoryMerchantOffer extends MerchantOffer {

    private final MerchantOffer originalOffer;
    private final SimpleContainer merchantInventory;

    private int uses;
    private int demand;


    public InventoryMerchantOffer(MerchantOffer originalOffer, SimpleContainer merchantInventory) {
        super(ItemStack.EMPTY, ItemStack.EMPTY, 0, 0, 0);
        this.originalOffer = originalOffer;
        this.merchantInventory = merchantInventory;
    }

    @Override
    public ItemStack getBaseCostA() {
        return originalOffer.getBaseCostA();
    }

    @Override
    public ItemStack getCostA() {
        return originalOffer.getCostA();
    }

    @Override
    public ItemStack getCostB() {
        return originalOffer.getCostB();
    }

    @Override
    public ItemStack getResult() {
        return originalOffer.getResult();
    }

    @Override
    public void updateDemand() {
        demand = demand + uses - (getMaxUses() - uses);
    }

    @Override
    public ItemStack assemble() {
        return originalOffer.assemble();
        /*
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack.sameItem(getResult())) {
                return itemStack.split(1);
            }
        }

        return ItemStack.EMPTY;
         */
    }

    @Override
    public int getUses() {
        return uses;
    }

    @Override
    public void resetUses() {
        uses = 0;
    }

    @Override
    public int getMaxUses() {
        if (offersTrade()) {
            return merchantInventory.countItem(getResult().getItem()) / getResult().getCount() as int;
        } else {
            return 0;
        }
    }

    @Override
    public void increaseUses() {
        uses++;
    }

    @Override
    public int getDemand() {
        return demand;
    }

    @Override
    public void addToSpecialPriceDiff(int i) {
        originalOffer.addToSpecialPriceDiff(i);
    }

    @Override
    public void resetSpecialPriceDiff() {
        originalOffer.resetSpecialPriceDiff();
    }

    @Override
    public int getSpecialPriceDiff() {
        return originalOffer.getSpecialPriceDiff();
    }

    @Override
    public void setSpecialPriceDiff(int i) {
        originalOffer.setSpecialPriceDiff(i);
    }

    @Override
    public float getPriceMultiplier() {
        return originalOffer.getPriceMultiplier();
    }

    @Override
    public int getXp() {
        return originalOffer.getXp();
    }

    private boolean offersTrade() {
        return merchantInventory.countItem(getResult().getItem()) >= getResult().getCount()
                && merchantInventory.canAddItem(getCostA())
                && (getCostB().isEmpty() || merchantInventory.canAddItem(getCostB()));
    }

    @Override
    public boolean isOutOfStock() {
        return !offersTrade();
    }

    @Override
    public void setToOutOfStock() {
        uses = getMaxUses();
    }

    @Override
    public boolean needsRestock() {
        return uses > 0;
    }

    @Override
    public boolean shouldRewardExp() {
        return originalOffer.shouldRewardExp();
    }

    @Override
    public CompoundTag createTag() {
        CompoundTag compoundTag = originalOffer.createTag();
        compoundTag.putInt("uses", uses);
        compoundTag.putInt("maxUses", getMaxUses());
        compoundTag.putInt("demand", demand);
        return compoundTag;
    }

    @Override
    public boolean satisfiedBy(ItemStack costAItemStack, ItemStack costBItemStack) {
        return originalOffer.satisfiedBy(costAItemStack, costBItemStack);
    }

    @Override
    public boolean take(ItemStack costAItemStack, ItemStack costBItemStack) {
        return originalOffer.take(costAItemStack, costBItemStack);
    }
}
