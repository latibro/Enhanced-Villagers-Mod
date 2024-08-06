package com.latibro.minecraft.villager;

import com.latibro.minecraft.villager.inventoryinspector.villagerinventory.VillagerInventoryScreen;
import com.latibro.minecraft.villager.platform.Services;
import com.latibro.minecraft.villager.platform.services.RegistryService;

public class CommonClientClass {

    public static void init() {
        Constants.LOG.info("Villager Mod initializing client side");

        var registryService = Services.get(RegistryService.class);

        var villagerInventoryScreenResourceName = "villager_inventory";
        registryService.registerMenuScreen(villagerInventoryScreenResourceName, () -> VillagerInventoryScreen::new);
    }
}
