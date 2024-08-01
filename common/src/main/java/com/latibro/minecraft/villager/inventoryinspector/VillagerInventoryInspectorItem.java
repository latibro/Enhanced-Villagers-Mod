package com.latibro.minecraft.villager.inventoryinspector;

import com.latibro.minecraft.villager.Constants;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class VillagerInventoryInspectorItem extends Item {

    public VillagerInventoryInspectorItem() {
        super(new Properties());
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(
            @NotNull ItemStack itemStack,
            @NotNull Player player,
            @NotNull LivingEntity entity,
            @NotNull InteractionHand hand
    ) {
        Constants.LOG.info("Interact entity {}", entity);

        if (entity instanceof AbstractVillager) {
            Constants.LOG.info("Interact villager {}", entity);
            if (player instanceof ServerPlayer) {
                Constants.LOG.info("Open inventory {}", entity);
                //MenuRegistry.openMenu(player, new VillagerInventoryMenuProvider(entity));
                //player.openMenu(new VillagerInventoryMenuProvider(villager));
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    }
}
