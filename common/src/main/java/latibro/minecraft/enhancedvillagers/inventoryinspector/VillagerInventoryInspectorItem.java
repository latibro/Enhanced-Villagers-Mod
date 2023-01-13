package latibro.minecraft.enhancedvillagers.inventoryinspector;

import dev.architectury.registry.menu.MenuRegistry;
import latibro.minecraft.enhancedvillagers.EnhancedVillagersMod;
import latibro.minecraft.enhancedvillagers.inventoryinspector.villagerinventory.VillagerInventoryMenuProvider;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class VillagerInventoryInspectorItem extends Item {
    public VillagerInventoryInspectorItem() {
        super(new Properties().tab(EnhancedVillagersMod.EXAMPLE_TAB));
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity entity, InteractionHand hand) {
        System.out.println("XXX: Interact entity");

        if (entity instanceof Villager villager) {
            System.out.println("XXX: Interact villager");
            if (player instanceof ServerPlayer serverPlayer) {
                MenuRegistry.openMenu(serverPlayer, new VillagerInventoryMenuProvider(villager));
                //player.openMenu(new VillagerInventoryMenuProvider(villager));
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    }

}
