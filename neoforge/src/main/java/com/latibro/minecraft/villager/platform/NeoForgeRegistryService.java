package com.latibro.minecraft.villager.platform;

import com.latibro.minecraft.villager.Constants;
import com.latibro.minecraft.villager.platform.services.CommonRegistryService;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class NeoForgeRegistryService<M extends AbstractContainerMenu, U extends Screen & MenuAccess<M>> extends CommonRegistryService {

    private final Map<String, Supplier<Block>> blocksToBeRegistred = new HashMap<>();
    private final Map<String, Supplier<Item>> itemsToBeRegistred = new HashMap<>();
    private final Map<String, Supplier<BlockEntityType<?>>> blockEntityTypesToBeRegistred = new HashMap<>();
    private final Map<String, Supplier<MenuType<?>>> menuTypesToBeRegistred = new HashMap<>();
    private final Map<String, Supplier<MenuScreens.ScreenConstructor<M, U>>> menuScreenToBeRegistred = new HashMap<>();

    public NeoForgeRegistryService() {
        Constants.LOG.info("Add RegisterEvent listener");
        //NeoForge.EVENT_BUS.addListener((RegisterEvent event) -> {
        //    Constants.LOG.info("Add RegisterEvent listener");
        //});
        //NeoForge.EVENT_BUS.addListener(this::onRegisterEvent);
    }

    @Override
    public void registerBlock(String name, Supplier<Block> blockSupplier) {
        blocksToBeRegistred.put(name, blockSupplier);
    }

    @Override
    public void registerItem(String name, Supplier<Item> itemSupplier) {
        itemsToBeRegistred.put(name, itemSupplier);
    }

    @Override
    public void registerBlockEntityType(String name, Supplier<BlockEntityType<?>> blockEntityTypeSupplier) {
        blockEntityTypesToBeRegistred.put(name, blockEntityTypeSupplier);
    }

    @Override
    public void registerMenuType(String name, Supplier<MenuType<?>> menuTypeSupplier) {
        menuTypesToBeRegistred.put(name, menuTypeSupplier);
    }

    @Override
    public <M2 extends AbstractContainerMenu, U2 extends Screen & MenuAccess<M2>> void registerMenuScreen(String name,
                                                                                                       Supplier<MenuScreens.ScreenConstructor<M2, U2>> menuScreenSupplier) {
        //@SuppressWarnings("unchecked")
        //TODO fix this cheating java type system / casting
        var x = (Object) menuScreenSupplier;
        var typedSupplier2 = (Supplier<MenuScreens.ScreenConstructor<M, U>>) x;
        menuScreenToBeRegistred.put(name, typedSupplier2);
    }

    @SubscribeEvent
    public void onRegisterEvent(RegisterEvent event) {
        Constants.LOG.info("RegisterEvent {}", event);
        if (event.getRegistry() == BuiltInRegistries.BLOCK) {
            blocksToBeRegistred.forEach(super::registerBlock);
        }
        if (event.getRegistry() == BuiltInRegistries.ITEM) {
            itemsToBeRegistred.forEach(super::registerItem);
        }
        if (event.getRegistry() == BuiltInRegistries.BLOCK_ENTITY_TYPE) {
            blockEntityTypesToBeRegistred.forEach(super::registerBlockEntityType);
        }
        if (event.getRegistry() == BuiltInRegistries.MENU) {
            menuTypesToBeRegistred.forEach(super::registerMenuType);
        }
    }

    @SubscribeEvent
    private void registerScreens(RegisterMenuScreensEvent event) {
        Constants.LOG.info("RegisterMenuScreensEvent {}", event);
        menuScreenToBeRegistred.forEach(super::registerMenuScreen);
    }
}
