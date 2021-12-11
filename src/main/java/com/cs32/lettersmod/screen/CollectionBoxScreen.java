package com.cs32.lettersmod.screen;

import com.cs32.lettersmod.LettersMod;
import com.cs32.lettersmod.container.CollectionBoxContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;


// see AnvilScreen.java for help
public class CollectionBoxScreen extends ContainerScreen<CollectionBoxContainer> {
  private final ResourceLocation GUI = new ResourceLocation(LettersMod.MOD_ID,
      "textures/gui/collection_box.png");
  private Button sendButton;
  private TextFieldWidget textFieldAddress;
  private String sendAddress;

  public CollectionBoxScreen(CollectionBoxContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
    super(screenContainer, inv, titleIn);
  }

  public void tick() {
    this.textFieldAddress.tick();
  }

  protected void init() {
    this.minecraft.keyboardListener.enableRepeatEvents(true);
    this.textFieldAddress = new TextFieldWidget(this.font, this.width / 2 - 100, 66, 200, 20, new TranslationTextComponent("addServer.enterName"));
    this.textFieldAddress.setFocused2(true);
    this.textFieldAddress.setText(this.sendAddress);
    this.textFieldAddress.setResponder(this::textFieldResponder);
    this.children.add(this.textFieldAddress);

    this.sendButton = this.addButton(new Button(this.width / 2 - 100, this.height / 4 + 96 + 18, 200, 20, new TranslationTextComponent("collectionBox.sendButton"), (input) -> {
      this.onButtonServerAddPressed();
    }));
  }

  private void textFieldResponder(String s) { // don't actually use s, just to make compiler happy
    s = this.textFieldAddress.getText();
    boolean flag = !s.isEmpty() && s.split(":").length > 0 && s.indexOf(32) == -1;
    this.sendButton.active = flag && !this.textFieldAddress.getText().isEmpty();
  }

  private void onButtonServerAddPressed() {
    this.sendAddress = this.textFieldAddress.getText();
    //TODO call mailcourier functionality
    System.out.println(this.sendAddress);
  }

  @Override
  public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
    this.renderBackground(matrixStack);
    super.render(matrixStack, mouseX, mouseY, partialTicks);
    this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    this.textFieldAddress.render(matrixStack, mouseX, mouseY, partialTicks);
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
    RenderSystem.color4f(1f, 1f, 1f, 1f);
    this.minecraft.getTextureManager().bindTexture(GUI);
    int i = this.guiLeft;
    int j = this.guiTop;
    this.blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize);

    // if lightning storm
//    this.blit(matrixStack, i + 82, j + 9, 176, 0, 13, 17);
  }
}