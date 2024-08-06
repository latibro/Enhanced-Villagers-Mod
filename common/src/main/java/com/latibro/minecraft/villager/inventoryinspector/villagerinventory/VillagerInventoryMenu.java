package com.latibro.minecraft.villager.inventoryinspector.villagerinventory;

import com.latibro.minecraft.villager.platform.Services;
import com.latibro.minecraft.villager.platform.services.RegistryService;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

/*
Inspiration:

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

    private static MenuType<VillagerInventoryMenu> getMenuType() {
        return (MenuType<VillagerInventoryMenu>) Services.get(RegistryService.class).getMenuType("villager_inventory"); //TODO better getter
    }

    public VillagerInventoryMenu(int id, Inventory playerInventory, AbstractVillager villager) {
        super(getMenuType(), id);
        this.villager = villager;

        int villagerInventoryPosXOffset = 18;

        // Villager inventory
        SimpleContainer villagerInventory = villager != null ? villager.getInventory() : new SimpleContainer(36);
        for (int rowIndex = 0; rowIndex < 4; rowIndex++) {
            for (int columnIndex = 0; columnIndex < 9; columnIndex++) {
                int villagerInventorySlotIndex = (rowIndex * 9) + columnIndex;
                int slotPosX = 8 + (columnIndex * 18);
                int slotPosY = villagerInventoryPosXOffset + (rowIndex * 18);
                this.addSlot(new Slot(villagerInventory, villagerInventorySlotIndex, slotPosX, slotPosY));
            }
        }

        int playerInventoryPosXOffset = villagerInventoryPosXOffset + (4 * 18) + 13;

        // Player inventory
        for (int rowIndex = 0; rowIndex < 3; rowIndex++) {
            for (int columnIndex = 0; columnIndex < 9; columnIndex++) {
                int playerInventorySlotIndex = (rowIndex * 9) + columnIndex + 9;
                int slotPosX = 8 + (columnIndex * 18);
                int slotPosY = playerInventoryPosXOffset + (rowIndex * 18);
                this.addSlot(new Slot(playerInventory, playerInventorySlotIndex, slotPosX, slotPosY));
            }
        }

        // Player hotbar
        for (int columnIndex = 0; columnIndex < 9; columnIndex++) {
            int playerInventorySlotIndex = columnIndex;
            int slotPosX = 8 + (columnIndex * 18);
            int slotPosY = playerInventoryPosXOffset + (3*18) + 4;
            this.addSlot(new Slot(playerInventory, playerInventorySlotIndex, slotPosX, slotPosY));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(i);
        if (slot != null && slot.hasItem()) {
            ItemStack itemStack2 = slot.getItem();
            itemStack = itemStack2.copy();
            if (i < 27 ? !this.moveItemStackTo(itemStack2, 27, this.slots.size(), true) :
                !this.moveItemStackTo(itemStack2, 0, 27, false)) {
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
