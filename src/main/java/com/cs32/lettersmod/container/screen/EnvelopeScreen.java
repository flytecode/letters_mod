package com.cs32.lettersmod.container.screen;


import com.cs32.lettersmod.item.custom.Envelope.EnvelopeContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class EnvelopeScreen extends ContainerScreen<EnvelopeContainer> {
  private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("textures/gui/envelope_gui.png");
  private TextFieldWidget toField;
  private TextFieldWidget fromField;

  public EnvelopeScreen(EnvelopeContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
    super(screenContainer, inv, titleIn);
    this.xSize = 176;
    this.ySize = 166;
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
    int xStart = (this.width - this.xSize) / 2;
    int yStart = (this.height - this.ySize) / 2;
    this.minecraft.getTextureManager().bindTexture(GUI_TEXTURE);
    this.blit(matrixStack, xStart, yStart, 0, 0, this.xSize, this.ySize);
  }

  @Override
  public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
    this.renderBackground(matrixStack);
    super.render(matrixStack, mouseX, mouseY, partialTicks);
    this.toField.render(matrixStack, mouseX, mouseY, partialTicks);
    this.fromField.render(matrixStack, mouseX, mouseY, partialTicks);
    this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
  }

}
