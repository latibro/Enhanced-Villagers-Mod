package latibro.minecraft.enhancedvillagers.forge;

import dev.architectury.platform.forge.EventBuses;
import latibro.minecraft.enhancedvillagers.EnhancedVillagersMod;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(EnhancedVillagersMod.MOD_ID)
public class EnhancedVillagersModForge {
    public EnhancedVillagersModForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(EnhancedVillagersMod.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        new EnhancedVillagersMod();
    }
}
