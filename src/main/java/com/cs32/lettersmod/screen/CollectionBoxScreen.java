package com.cs32.lettersmod.screen;

import com.cs32.lettersmod.LettersMod;
import com.cs32.lettersmod.container.CollectionBoxContainer;
import com.cs32.lettersmod.network.SendParcelPacket;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;


public class CollectionBoxScreen extends ContainerScreen<CollectionBoxContainer> {
  private final ResourceLocation GUI = new ResourceLocation(LettersMod.MOD_ID,
      "textures/gui/collection_box.png");
  private Button sendButton;
  private String displayString; //TODO displaying to the player status of stuff

  public CollectionBoxScreen(CollectionBoxContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
    super(screenContainer, inv, titleIn);
  }

  @Override
  // button for sending mail
  protected void init() {
    super.init();
    int i = this.guiLeft;
    int j = this.guiTop;
    this.sendButton = this.addButton(new Button(i + 44, j + 51, 89, 13, new TranslationTextComponent("collectionBox.sendButton"), (p_214318_1_) -> {
      displayString = this.container.send();
      System.out.println("displayString: " + displayString);
    }));
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
  }
}