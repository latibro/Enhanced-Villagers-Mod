package latibro.minecraft.enhancedvillagers.inventoryinspector

import dev.architectury.registry.menu.MenuRegistry
import groovy.transform.CompileStatic
import latibro.minecraft.enhancedvillagers.EnhancedVillagersMod
import latibro.minecraft.enhancedvillagers.inventoryinspector.villagerinventory.VillagerInventoryMenuProvider
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.npc.AbstractVillager
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack

@CompileStatic
public class VillagerInventoryInspectorItem extends Item {
    public VillagerInventoryInspectorItem() {
        super(new Properties().tab(EnhancedVillagersMod.ENHANGED_VILLAGERS_TAB));
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity entity, InteractionHand hand) {
        System.out.println("XXX: Interact entity " + entity);

        if (entity instanceof AbstractVillager) {
            System.out.println("XXX: Interact villager " + entity);
            if (player instanceof ServerPlayer) {
                MenuRegistry.openMenu(player, new VillagerInventoryMenuProvider(entity));
                //player.openMenu(new VillagerInventoryMenuProvider(villager));
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    }

}
