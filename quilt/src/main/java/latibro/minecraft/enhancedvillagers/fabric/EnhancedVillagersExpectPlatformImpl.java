package latibro.minecraft.enhancedvillagers.fabric;

import latibro.minecraft.enhancedvillagers.EnhancedVillagersExpectPlatform;
import org.quiltmc.loader.api.QuiltLoader;

import java.nio.file.Path;

public class EnhancedVillagersExpectPlatformImpl {
    /**
     * This is our actual method to {@link EnhancedVillagersExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return QuiltLoader.getConfigDir();
    }
}
