package com.cs32.lettersmod.screen;

import com.cs32.lettersmod.LettersMod;
import com.cs32.lettersmod.container.CollectionBoxContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CRenameItemPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;


// see AnvilScreen.java for help
public class CollectionBoxScreen extends ContainerScreen<CollectionBoxContainer> {
  private final ResourceLocation GUI = new ResourceLocation(LettersMod.MOD_ID,
      "textures/gui/collection_box.png");
  private TextFieldWidget sendAddressField;

  public CollectionBoxScreen(CollectionBoxContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
    super(screenContainer, inv, titleIn); //for extends ContainerScreen<CollectionBoxContainer>
  }

  // taken from AnvilScreen.java
  public void tick() {
    super.tick();
    this.sendAddressField.tick();
  }

  // initialize the fields of the screen
  protected void initFields() {
    this.minecraft.keyboardListener.enableRepeatEvents(true);
    int i = (this.width - this.xSize) / 2;
    int j = (this.height - this.ySize) / 2;
    this.sendAddressField = new TextFieldWidget(this.font, i + 62, j + 24, 103, 12, new TranslationTextComponent("container.repair"));
    this.sendAddressField.setCanLoseFocus(false);
    this.sendAddressField.setTextColor(-1);
    this.sendAddressField.setDisabledTextColour(-1);
    this.sendAddressField.setEnableBackgroundDrawing(false);
    this.sendAddressField.setMaxStringLength(35);
    this.sendAddressField.setResponder(this::renameItem);
    this.children.add(this.sendAddressField);
    this.setFocusedDefault(this.sendAddressField);
  }

  // function for resizing screen? from AnvilScreen.java
  public void resize(Minecraft minecraft, int width, int height) {
    String s = this.sendAddressField.getText();
    this.init(minecraft, width, height);
    this.sendAddressField.setText(s);
  }

  // from AnvilScreen.java
  public void onClose() {
    super.onClose();
    this.minecraft.keyboardListener.enableRepeatEvents(false);
  }

  // handles escape
  public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
    if (keyCode == 256) {
      this.minecraft.player.closeScreen();
    }

    return !this.sendAddressField.keyPressed(keyCode, scanCode, modifiers) && !this.sendAddressField.canWrite() ? super.keyPressed(keyCode, scanCode, modifiers) : true;
  }

  // TODO modify so that we can use the MailCourier send functionality. taken from renameItem in AnvilScreen.java
  private void renameItem(String name) {
    if (!name.isEmpty()) {
      String s = name;
      Slot slot = this.container.getSlot(0);
      if (slot != null && slot.getHasStack() && !slot.getStack().hasDisplayName() && name.equals(slot.getStack().getDisplayName().getString())) {
        s = "";
      }

      this.container.updateItemName(s);
      this.minecraft.player.connection.sendPacket(new CRenameItemPacket(s));
    }
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

    // if is lightning storm
//    this.blit(matrixStack, i + 82, j + 9, 176, 0, 13, 17);
  }

  // not sure how this function fits in
  public void renderNameField(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
    this.sendAddressField.render(matrixStack, mouseX, mouseY, partialTicks);
  }

  // not sure how this function fits in
  /**
   * Sends the contents of an inventory slot to the client-side Container. This doesn't have to match the actual
   * contents of that slot.
   */
  public void sendSlotContents(Container containerToSend, int slotInd, ItemStack stack) {
    if (slotInd == 0) {
      this.sendAddressField.setText(stack.isEmpty() ? "" : stack.getDisplayName().getString());
      this.sendAddressField.setEnabled(!stack.isEmpty());
      this.setListener(this.sendAddressField);
    }

  }

}