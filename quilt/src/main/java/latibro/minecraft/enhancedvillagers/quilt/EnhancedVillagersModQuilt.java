package latibro.minecraft.enhancedvillagers.quilt;

import latibro.minecraft.enhancedvillagers.fabriclike.EnhancedVillagersModFabricLike;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

public class EnhancedVillagersModQuilt implements ModInitializer {
    @Override
    public void onInitialize(ModContainer mod) {
        EnhancedVillagersModFabricLike.initialize();
    }
}
