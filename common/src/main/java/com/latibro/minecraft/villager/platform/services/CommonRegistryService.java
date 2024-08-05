package com.latibro.minecraft.villager.platform.services;

import com.latibro.minecraft.villager.Constants;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.Objects;
import java.util.function.Supplier;

public class CommonRegistryService implements RegistryService {

    public static ResourceLocation createResourceLocation(String name) {
        return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name);
    }

    @Override
    public void registerBlock(String name, Supplier<Block> blockSupplier) {
        Registry.register(BuiltInRegistries.BLOCK, createResourceLocation(name), blockSupplier.get());
    }

    @Override
    public Block getBlock(String name) {
        var block = BuiltInRegistries.BLOCK.get(createResourceLocation(name));
        Objects.requireNonNull(block);
        return block;
    }

    @Override
    public void registerItem(String name, Supplier<Item> itemSupplier) {
        Registry.register(BuiltInRegistries.ITEM, createResourceLocation(name), itemSupplier.get());
    }

    @Override
    public Item getItem(String name) {
        var item = BuiltInRegistries.ITEM.get(createResourceLocation(name));
        Objects.requireNonNull(item);
        return item;
    }

    @Override
    public void registerBlockEntityType(String name, Supplier<BlockEntityType<?>> blockEntityTypeSupplier) {
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE,
                          createResourceLocation(name),
                          blockEntityTypeSupplier.get());
    }

    @Override
    public BlockEntityType<?> getBlockEntityType(String name) {
        BlockEntityType<?> blockEntityType = BuiltInRegistries.BLOCK_ENTITY_TYPE.get(createResourceLocation(name));
        assert blockEntityType != null;
        return blockEntityType;
    }

    @Override
    public void registerMenuType(String name, Supplier<MenuType<?>> menuTypeSupplier) {
        Registry.register(BuiltInRegistries.MENU,
                          createResourceLocation(name),
                          menuTypeSupplier.get());
    }

    @Override
    public MenuType<?> getMenuType(String name) {
        MenuType<?> menuType = BuiltInRegistries.MENU.get(createResourceLocation(name));
        Objects.requireNonNull(menuType);
        return menuType;
    }

    @Override
    public <M extends AbstractContainerMenu, U extends Screen & MenuAccess<M>> void  registerMenuScreen(String name, Supplier<MenuScreens.ScreenConstructor<M,U>> menuScreenSupplier) {
        MenuType<? extends M> villagerInventoryMenu = (MenuType<? extends M>) getMenuType(name);

        MenuScreens.register(villagerInventoryMenu, menuScreenSupplier.get());
    }

}
