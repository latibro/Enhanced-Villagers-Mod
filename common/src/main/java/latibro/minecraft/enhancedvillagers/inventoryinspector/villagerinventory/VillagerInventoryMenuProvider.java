package latibro.minecraft.enhancedvillagers.inventoryinspector.villagerinventory;

import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.Nullable;

public class VillagerInventoryMenuProvider implements MenuProvider {

    private final Villager villager;

    public VillagerInventoryMenuProvider(Villager villager) {
        this.villager = villager;
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Villager Inventory");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
        return new VillagerInventoryMenu(id, playerInventory, villager);
    }
}
