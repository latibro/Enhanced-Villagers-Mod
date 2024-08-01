package com.latibro.minecraft.villager;


import com.latibro.minecraft.villager.platform.Services;
import com.latibro.minecraft.villager.platform.services.RegistryService;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class VillagerMod {

    public VillagerMod(IEventBus eventBus) {
        // This method is invoked by the NeoForge mod loader when it is ready
        // to load your mod. You can access NeoForge and Common code in this
        // project.

        eventBus.register(Services.get(RegistryService.class));

        // Use NeoForge to bootstrap the Common mod.
        CommonClass.init();
    }
}