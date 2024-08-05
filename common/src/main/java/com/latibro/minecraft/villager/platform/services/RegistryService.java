package com.latibro.minecraft.villager.platform.services;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public interface RegistryService {

    void registerBlock(String name, Supplier<Block> blockSupplier);

    Block getBlock(String name);

    void registerItem(String name, Supplier<Item> itemSupplier);

    Item getItem(String name);

    void registerBlockEntityType(String name, Supplier<BlockEntityType<?>> blockEntityTypeSupplier);

    BlockEntityType<?> getBlockEntityType(String name);

    void registerMenuType(String name, Supplier<MenuType<?>> menuTypeSupplier);

    MenuType<?> getMenuType(String name);

    <M extends AbstractContainerMenu, U extends Screen & MenuAccess<M>> void registerMenuScreen(String name, Supplier<MenuScreens.ScreenConstructor<M, U>> menuScreenSupplier);

}
