package com.latibro.minecraft.villager.platform;

import com.latibro.minecraft.villager.Constants;
import com.latibro.minecraft.villager.platform.services.CommonRegistryService;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class NeoForgeRegistryService extends CommonRegistryService {

    private final Map<String, Supplier<Block>> blocksToBeRegistred = new HashMap<>();
    private final Map<String, Supplier<Item>> itemsToBeRegistred = new HashMap<>();
    private final Map<String, Supplier<BlockEntityType<?>>> blockEntityTypesToBeRegistred = new HashMap<>();

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
    }

}
