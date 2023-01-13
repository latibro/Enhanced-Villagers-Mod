package latibro.minecraft.enhancedvillagers.fabric;

import latibro.minecraft.enhancedvillagers.EnhancedVillagersExpectPlatform;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class EnhancedVillagersExpectPlatformImpl {
    /**
     * This is our actual method to {@link EnhancedVillagersExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }
}
