package latibro.minecraft.enhancedvillagers.inventoryinspector.villagerinventory;

import latibro.minecraft.enhancedvillagers.EnhancedVillagersMod;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.npc.AbstractVillager;
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

net.minecraft.world.inventory.ChestMenu
 */
public class VillagerInventoryMenu extends AbstractContainerMenu {
    private final AbstractVillager villager;

    /* Client side constructor */
    public VillagerInventoryMenu(int id, Inventory playerInventory) {
        this(id, playerInventory, null);
    }

    /* Server side constructor */
    public VillagerInventoryMenu(int id, Inventory playerInventory, AbstractVillager villager) {
        super(EnhancedVillagersMod.VILLAGER_INVENTORY_MENU.get(), id);
        this.villager = villager;

        int villagerInventoryPosXOffset = 18;

        // Villager inventory
        SimpleContainer villagerInventory = villager != null ? villager.getInventory() : new SimpleContainer(27);
        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 9; k++) {
                int villagerInventorySlotIndex = (j * 9) + k;
                int slotPosX = 8 + (k * 18);
                int slotPosY = villagerInventoryPosXOffset + (j * 18);
                this.addSlot(new Slot(villagerInventory, villagerInventorySlotIndex, slotPosX, slotPosY));
            }
        }

        int playerInventoryPosXOffset = villagerInventoryPosXOffset + 85 - 18;

        // Player inventory
        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 9; k++) {
                int playerInventorySlotIndex = (j * 9) + k + 9;
                int slotPosX = 8 + (k * 18);
                int slotPosY = playerInventoryPosXOffset + (j * 18);
                this.addSlot(new Slot(playerInventory, playerInventorySlotIndex, slotPosX, slotPosY));
            }
        }

        // Player hotbar
        for (int j = 0; j < 9; j++) {
            int playerInventorySlotIndex = j;
            int slotPosX = 8 + (j * 18);
            int slotPosY = playerInventoryPosXOffset + 58;
            this.addSlot(new Slot(playerInventory, playerInventorySlotIndex, slotPosX, slotPosY));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot) this.slots.get(i);
        if (slot != null && slot.hasItem()) {
            ItemStack itemStack2 = slot.getItem();
            itemStack = itemStack2.copy();
            if (i < 27 ? !this.moveItemStackTo(itemStack2, 27, this.slots.size(), true) : !this.moveItemStackTo(itemStack2, 0, 27, false)) {
                return ItemStack.EMPTY;
            }
            if (itemStack2.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return itemStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.villager.getInventory().stillValid(player);
    }
}
