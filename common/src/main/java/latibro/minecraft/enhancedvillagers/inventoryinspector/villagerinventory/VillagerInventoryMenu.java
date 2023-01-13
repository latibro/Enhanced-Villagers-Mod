package latibro.minecraft.enhancedvillagers.inventoryinspector.villagerinventory;

import latibro.minecraft.enhancedvillagers.EnhancedVillagersMod;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

/*
net.minecraft.world.inventory.HorseInventoryMenu
net.minecraft.world.entity.animal.horse.AbstractHorse.openCustomInventoryScreen
net.minecraft.server.level.ServerPlayer.openMenu
net.minecraft.world.inventory.ChestMenu
net.minecraft.world.entity.npc.AbstractVillager.getInventory
net.minecraft.world.inventory.MenuConstructor.createMenu

net.minecraft.world.inventory.MerchantMenu.MerchantMenu
net.minecraft.world.item.trading.Merchant.openTradingScreen
 */
public class VillagerInventoryMenu extends AbstractContainerMenu {
    private final Villager villager;

    /* Client side constructor */
    public VillagerInventoryMenu(int id, Inventory playerInventory) {
        this(id, playerInventory, (Villager) null);
    }

    /* Server side constructor */
    public VillagerInventoryMenu(int id, Inventory playerInventory, Villager villager) {
        super(EnhancedVillagersMod.VILLAGER_INVENTORY_MENU.get(), id);
        this.villager = villager;

        // Villager inventory
        for (int j = 0; j < 8; ++j) {
            int villagerInventorySlotIndex = j;
            int slotPosX = 8 + j * 18;
            int slotPosY = 18;
            this.addSlot(new Slot(villager != null ? villager.getInventory() : new SimpleContainer(8), villagerInventorySlotIndex, slotPosX, slotPosY));
        }

        // Player inventory
        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 9; k++) {
                int playerInventorySlotIndex = (j * 9) + k + 9;
                int slotPosX = 8 + (k * 18);
                int slotPosY = 84 + (j * 18);
                this.addSlot(new Slot(playerInventory, playerInventorySlotIndex, slotPosX, slotPosY));
            }
        }

        // Player hotbar
        for (int j = 0; j < 9; j++) {
            int playerInventorySlotIndex = j;
            int slotPosX = 8 + (j * 18);
            int slotPosY = 142;
            this.addSlot(new Slot(playerInventory, playerInventorySlotIndex, slotPosX, slotPosY));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null; //TODO implement
    }

    @Override
    public boolean stillValid(Player player) {
        return true; //TODO check if villager is still in range
    }
}
