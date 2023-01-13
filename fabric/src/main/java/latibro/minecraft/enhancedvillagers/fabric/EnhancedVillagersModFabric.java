package latibro.minecraft.enhancedvillagers.fabric;

import latibro.minecraft.enhancedvillagers.fabriclike.EnhancedVillagersModFabricLike;
import net.fabricmc.api.ModInitializer;

public class EnhancedVillagersModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        EnhancedVillagersModFabricLike.initialize();
    }
}
