package com.latibro.minecraft.villager.trade;

import com.latibro.minecraft.villager.Constants;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;

// Inpiration net.minecraft.world.entity.npc.VillagerTrades
public class TradeOffersManager {

    private final AbstractVillager villager;

    public TradeOffersManager(AbstractVillager villager) {
        this.villager = villager;
    }

    public MerchantOffers getOffers() {
        MerchantOffers offers = new MerchantOffers();

        if (villager instanceof Villager) {
           Constants.LOG.info(
                    "requested items {} {}", villager,
                    ((Villager) villager).getVillagerData().getProfession().requestedItems()
            );
        }

        //TODO find items to buy, based on needed items not already in inventory

        if ((villager instanceof Villager) && ((Villager) villager).wantsMoreFood()) {
            // Will buy bread if wants more food, and has payments to pay for it
            boolean hasPayment = villager.getInventory().countItem(Items.EMERALD) > 0;

            offers.add(
                    new MerchantOffer(
                            new ItemCost(Items.BREAD, 6),
                            new ItemStack(Items.EMERALD, 1),
                            hasPayment ? 1 : 0,
                            0,
                            0f
                    )
            );
        }

        //TODO find items to sell, based on not needed items in inventory
        SimpleContainer inventory = villager.getInventory();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack.isEmpty()) {
                continue;
            }

            if (itemStack.is(Items.EMERALD)) {
                // Will not sell emeralds
                continue;
            }

            if (itemStack.is(Items.BREAD) && villager instanceof Villager && !((Villager) villager).hasExcessFood()) {
                // Will only sell bread if has excess food
                continue;
            }

            //TODO better pricing system
            ItemStack sellItemStack = itemStack.copy();
            sellItemStack.setCount(1);
            offers.add(new MerchantOffer(new ItemCost(Items.EMERALD, 1), sellItemStack, 1, 0, 0f));
        }

        return offers;
    }


}