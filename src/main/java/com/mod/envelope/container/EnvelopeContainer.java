package com.mod.envelope.container;

import com.mod.envelope.core.init.ModContainerTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.SlotItemHandler;

public class EnvelopeContainer extends Container {
    private final EnvelopeItemStackHandler envelopeItemStackHandler;
    private final ItemStack heldItem;

    public EnvelopeContainer(int windowId, PlayerInventory playerInv, PacketBuffer data) {
        this(windowId, playerInv, new EnvelopeItemStackHandler(), ItemStack.EMPTY);
    }

    public EnvelopeContainer(int windowId, PlayerInventory playerInv, EnvelopeItemStackHandler envelopeItemStackHandler, ItemStack stack) {
        super(ModContainerTypes.ENVELOPE.get(), windowId);

        this.envelopeItemStackHandler = envelopeItemStackHandler;
        this.heldItem = stack;

        // Backpack Slots
        int column2 = 4;
        int row2 = 1;
        this.addSlot(new SlotItemHandler(envelopeItemStackHandler, row2 * 3 + column2, 8 + column2 * 18, 18 + row2 * 18));

        // Player Inventory Slots
        for (int row = 0; row < 3; row++) for (int column = 0; column < 9; column++)
            this .addSlot(new Slot(playerInv, 9 + row * 9 + column, 8 + column * 18, 84 + row * 18));
        for (int column = 0; column < 9; column++)
            this.addSlot(new Slot(playerInv, column, 8 + column * 18, 84 + 18 * 3 + 4));
    }

    public ItemStack transferStackInSlot(PlayerEntity player, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = (Slot) this.slots.get(index);

        if (slot == null || !slot.hasItem()) return stack;

        ItemStack slotStack = slot.getItem();
        stack = slotStack.copy();

        if (index >= envelopeItemStackHandler.getSlots()) {
            if (!moveItemStackTo(slotStack, 0, envelopeItemStackHandler.getSlots(), false))
                return ItemStack.EMPTY;
        } else if (!moveItemStackTo(slotStack, envelopeItemStackHandler.getSlots(), slot.getMaxStackSize(), false))
            return ItemStack.EMPTY;

        if (slotStack.isEmpty()) slot.set(ItemStack.EMPTY);
        else slot.setChanged();

        return stack;
    }

    @Override
    public boolean stillValid(PlayerEntity playerEntity) {
        ItemStack main = playerEntity.getMainHandItem();
        ItemStack off = playerEntity.getOffhandItem();
        return (!main.isEmpty() && main == heldItem || !off.isEmpty() && off == heldItem);
    }

}
