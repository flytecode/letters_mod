package com.mod.envelope.container;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;

public class EnvelopeCapabilityProvider implements ICapabilitySerializable<INBT> {
    private EnvelopeItemStackHandler envelopeItemStackHandler;
    private final LazyOptional<IItemHandler> lazyInitialisationSupplier = LazyOptional.of(this::getCachedInventory);

    public EnvelopeCapabilityProvider() {
    }

    @Nonnull
    private EnvelopeItemStackHandler getCachedInventory() {
        if (envelopeItemStackHandler == null) envelopeItemStackHandler = new EnvelopeItemStackHandler();
        return envelopeItemStackHandler;
    }

    @Nonnull @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return (LazyOptional<T>) (lazyInitialisationSupplier);
        return LazyOptional.empty();
    }

    public INBT serializeNBT() {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(this.getCachedInventory(), (Direction) null);
    }

    public void deserializeNBT(INBT nbt) {
        CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(this.getCachedInventory(),(Direction) null, nbt);
    }
}
