package latibro.minecraft.enhancedvillagers.trade;

import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;

public class TradeOffersManager {

    private final Villager villager;

    public TradeOffersManager(Villager villager) {
        this.villager = villager;
    }

    public MerchantOffers getOffers() {
        MerchantOffers offers = new MerchantOffers();

        //TODO find items to buy, based on needed items not already in inventory
        offers.add(new MerchantOffer(new ItemStack(Items.BREAD, 6), new ItemStack(Items.EMERALD, 1), 1, 0, 0f));

        //TODO find items to sell, based on not needed items in inventory
        offers.add(new MerchantOffer(new ItemStack(Items.EMERALD, 10), new ItemStack(Items.IRON_PICKAXE, 1), 1, 0, 0f));

        return offers;
    }


}
