package latibro.minecraft.enhancedvillagers.inventoryinspector.villagerinventory

import groovy.transform.CompileStatic
import net.minecraft.network.chat.Component
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.npc.AbstractVillager
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import org.jetbrains.annotations.Nullable

@CompileStatic
public class VillagerInventoryMenuProvider implements MenuProvider {

    private final AbstractVillager villager;

    public VillagerInventoryMenuProvider(AbstractVillager villager) {
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
