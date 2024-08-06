package com.latibro.minecraft.villager.inventory;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.npc.AbstractVillager;

public class VillagerInventory extends SimpleContainer {

    private static final int INVENTORY_SIZE = 36; // 3 x 9
    private final AbstractVillager villager;

    public VillagerInventory(AbstractVillager villager) {
        super(INVENTORY_SIZE);
        this.villager = villager;
    }

}
