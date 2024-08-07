package com.latibro.minecraft.villager.mixin;

import com.latibro.minecraft.villager.Constants;
import com.latibro.minecraft.villager.inventory.VillagerInventory;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.npc.AbstractVillager;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractVillager.class)
public class LargeInventoryVillagerMixin {

    private final VillagerInventory villagerInventory = new VillagerInventory((AbstractVillager) (Object) this);

    /**
     * @author Latibro
     * @reason Replacing the villagers inventory
     */
    @Overwrite
    public @NotNull SimpleContainer getInventory() {
        return this.villagerInventory;
    }

    /**
     * @author Latibro
     * @reason Make sure direct field access to "inventory" is redirected to "getInventory()"
     */
    @Redirect(
            method = "getSlot",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/entity/npc/AbstractVillager;inventory:Lnet/minecraft/world/SimpleContainer;"
                    //,opcode = Opcodes.GETFIELD
                    )
    )
    private SimpleContainer mixinFieldGetInventory(AbstractVillager villager) {
        Constants.LOG.info("Get inventory field redirect {}", this);
        return villager.getInventory();
    }

}
