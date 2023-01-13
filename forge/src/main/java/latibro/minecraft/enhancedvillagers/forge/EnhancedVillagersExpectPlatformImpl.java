package latibro.minecraft.enhancedvillagers.forge;

import latibro.minecraft.enhancedvillagers.EnhancedVillagersExpectPlatform;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class EnhancedVillagersExpectPlatformImpl {
    /**
     * This is our actual method to {@link EnhancedVillagersExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }
}
