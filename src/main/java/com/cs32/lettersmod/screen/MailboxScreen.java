package com.cs32.lettersmod.screen;

import com.cs32.lettersmod.LettersMod;
import com.cs32.lettersmod.container.MailboxContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class MailboxScreen extends ContainerScreen<MailboxContainer> {
  private final ResourceLocation GUI = new ResourceLocation(LettersMod.MOD_ID,
      "textures/gui/mailbox.png");

  private Button refreshButton;

  public MailboxScreen(MailboxContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
    super(screenContainer, inv, titleIn);
  }

  @Override
  // button for refreshing our mailbox
  protected void init() {
    super.init();
    int i = this.guiLeft;
    int j = this.guiTop;
    this.refreshButton = this.addButton(new Button(i + 95, j + 3, 70, 13, new TranslationTextComponent("mailBox.refreshButton"), (p_214318_1_) -> {
      System.out.println("REFRESHBUTTON");
    }));


//TODO code from vanilla
//    this.inventoryRows = container.getNumRows();
//    this.ySize = 114 + this.inventoryRows * 18;
//    this.playerInventoryTitleY = this.ySize - 94;
  }

  @Override
  public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
    this.renderBackground(matrixStack);
    super.render(matrixStack, mouseX, mouseY, partialTicks);
    this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
    RenderSystem.color4f(1f, 1f, 1f, 1f);
    this.minecraft.getTextureManager().bindTexture(GUI);
    int i = this.guiLeft;
    int j = this.guiTop;
    this.blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize);

    if(container.isLightningStorm()) {
      this.blit(matrixStack, i + 82, j + 9, 176, 0, 13, 17);
    }
  }
}