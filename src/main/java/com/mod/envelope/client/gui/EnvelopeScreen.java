package com.mod.envelope.client.gui;

import com.mod.envelope.container.EnvelopeContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class EnvelopeScreen extends ContainerScreen<EnvelopeContainer> {
    private static final ResourceLocation ENVELOPE_TEX = new ResourceLocation("envelope", "textures/gui/container/envelope.png");

    public EnvelopeScreen(EnvelopeContainer container, PlayerInventory playerInv, ITextComponent title) {
        super(container, playerInv, title);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);

        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        getMinecraft().getTextureManager().bind(ENVELOPE_TEX);

        this.blit(matrixStack, this.getGuiLeft(), this.getGuiTop(), 0, 0, this.getXSize(), this.getYSize());
    }

    @Override
    protected void renderTooltip(MatrixStack matrixStack, int x, int y) {
        super.renderTooltip(matrixStack, x, y);
    }
}
