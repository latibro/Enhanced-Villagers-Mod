package net.examplemod;

import com.google.common.base.Suppliers;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registries;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import net.examplemod.inventoryinspector.VillagerInventoryInspectorItem;
import net.examplemod.inventoryinspector.villagerinventory.VillagerInventoryMenu;
import net.examplemod.inventoryinspector.villagerinventory.VillagerInventoryScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class ExampleMod {
    public static final String MOD_ID = "examplemod";
    // We can use this if we don't want to use DeferredRegister
    public static final Supplier<Registries> REGISTRIES = Suppliers.memoize(() -> Registries.get(MOD_ID));
    // Registering a new creative tab
    public static final CreativeModeTab EXAMPLE_TAB = CreativeTabRegistry.create(new ResourceLocation(MOD_ID, "example_tab"), () ->
            new ItemStack(ExampleMod.EXAMPLE_ITEM.get()));

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registry.ITEM_REGISTRY);
    public static final RegistrySupplier<Item> EXAMPLE_ITEM = ITEMS.register("example_item", () ->
            new Item(new Item.Properties().tab(ExampleMod.EXAMPLE_TAB)));
    public static final RegistrySupplier<Item> VILLAGER_INVENTORY_INSPECTOR_ITEM = ITEMS.register("villager_inventory_inspector", VillagerInventoryInspectorItem::new);

    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(MOD_ID, Registry.MENU_REGISTRY);

    public static final RegistrySupplier<MenuType<VillagerInventoryMenu>> VILLAGER_INVENTORY_MENU = MENUS.register("villager_inventory_inspector", () -> new MenuType<>(VillagerInventoryMenu::new));

    public ExampleMod() {
        LifecycleEvent.SETUP.register(this::setup);
        initialize();

        EnvExecutor.runInEnv(Env.CLIENT, () -> Client::new);
    }

    private void initialize() {
        ITEMS.register();
        MENUS.register();

        System.out.println(ExampleExpectPlatform.getConfigDirectory().toAbsolutePath().normalize().toString());
    }

    private void setup() {
    }

    @Environment(EnvType.CLIENT)
    private static class Client {

        public Client() {
            ClientLifecycleEvent.CLIENT_SETUP.register(this::setup);
            initialize();
        }

        @Environment(EnvType.CLIENT)
        private void initialize() {
        }

        @Environment(EnvType.CLIENT)
        private void setup(Minecraft minecraft) {
            MenuRegistry.registerScreenFactory(VILLAGER_INVENTORY_MENU.get(), VillagerInventoryScreen::new);
        }
    }
}
