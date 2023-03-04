package latibro.minecraft.enhancedvillagers.inventory

import groovy.transform.CompileStatic
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.npc.AbstractVillager

@CompileStatic
public class VillagerInventory extends SimpleContainer {

    private static final int INVENTORY_SIZE = 27; // 3 x 9
    private final AbstractVillager villager;

    public VillagerInventory(AbstractVillager villager) {
        super(INVENTORY_SIZE);
        this.villager = villager;
    }

}
