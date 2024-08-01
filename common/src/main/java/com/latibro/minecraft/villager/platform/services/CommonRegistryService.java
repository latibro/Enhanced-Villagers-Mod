package com.latibro.minecraft.villager.platform.services;

import com.latibro.minecraft.villager.Constants;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

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
    public void registerItem(String name, Supplier<Item> itemSupplier) {
        Registry.register(BuiltInRegistries.ITEM, createResourceLocation(name), itemSupplier.get());
    }

    @Override
    public void registerBlockEntityType(String name, Supplier<BlockEntityType<?>> blockEntityTypeSupplier) {
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE,
                          createResourceLocation(name),
                          blockEntityTypeSupplier.get());
    }

    @Override
    public Block getBlock(String name) {
        var block = BuiltInRegistries.BLOCK.get(createResourceLocation(name));
        return block;
    }

    @Override
    public Item getItem(String name) {
        var item = BuiltInRegistries.ITEM.get(createResourceLocation(name));
        return item;
    }

    @Override
    public BlockEntityType<?> getBlockEntityType(String name) {
        BlockEntityType<?> blockEntityType = BuiltInRegistries.BLOCK_ENTITY_TYPE.get(createResourceLocation(name));
        return blockEntityType;
    }

}
