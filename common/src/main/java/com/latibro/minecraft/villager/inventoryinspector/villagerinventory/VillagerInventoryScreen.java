package com.latibro.minecraft.villager.inventoryinspector.villagerinventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

// Inspiration: net.minecraft.client.gui.screens.inventory.ContainerScreen
public class VillagerInventoryScreen extends AbstractContainerScreen<VillagerInventoryMenu> {
    private static final ResourceLocation CONTAINER_BACKGROUND = ResourceLocation.withDefaultNamespace(
            "textures/gui/container/generic_54.png"
    );

    public VillagerInventoryScreen(VillagerInventoryMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        this.imageHeight = 114 + (3 * 18);
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, CONTAINER_BACKGROUND);
        int k = (this.width - this.imageWidth) / 2;
        int l = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(CONTAINER_BACKGROUND, k, l, 0, 0, this.imageWidth, (3 * 18) + 17);
        guiGraphics.blit(CONTAINER_BACKGROUND, k, l + (3 * 18) + 17, 0, 126, this.imageWidth, 96 + 2);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

}
