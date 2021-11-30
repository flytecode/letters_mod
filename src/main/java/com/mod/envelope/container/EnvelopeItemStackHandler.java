package com.mod.envelope.container;

import com.mod.envelope.item.SealedEnvelope;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import org.antlr.v4.runtime.misc.NotNull;

public class EnvelopeItemStackHandler extends ItemStackHandler {
    public static final int NUMBER_SLOTS = 27;

    public EnvelopeItemStackHandler() { super(NUMBER_SLOTS);}

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        if (slot < 0 || slot >= NUMBER_SLOTS)
            throw new IllegalArgumentException("Invalid Slot Number: " + slot);

        return !stack.isEmpty() && !(stack.getItem() instanceof SealedEnvelope);
    }
}
