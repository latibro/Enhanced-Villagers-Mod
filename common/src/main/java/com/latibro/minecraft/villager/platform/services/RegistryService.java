package com.latibro.minecraft.villager.platform.services;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public interface RegistryService {

    void registerBlock(String name, Supplier<Block> blockSupplier);

    void registerItem(String name, Supplier<Item> itemSupplier);

    void registerBlockEntityType(String name, Supplier<BlockEntityType<?>> blockEntityTypeSupplier);

    Block getBlock(String name);

    Item getItem(String name);

    BlockEntityType<?> getBlockEntityType(String name);

}
