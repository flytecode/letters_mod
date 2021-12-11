package com.cs32.lettersmod.tileentity;

import com.cs32.lettersmod.saveddata.SavedDataClass;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import com.cs32.lettersmod.item.ModItems;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CollectionBoxTile extends TileEntity {

  private final ItemStackHandler itemHandler = createHandler();
  private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

  public CollectionBoxTile(TileEntityType<?> tileEntityTypeIn) {
    super(tileEntityTypeIn);
  }

  public CollectionBoxTile() {
    this(ModTileEntities.COLLECTION_BOX_TILE.get());
  }

  @Override
  public void read(BlockState state, CompoundNBT nbt) {
    itemHandler.deserializeNBT(nbt.getCompound("inv"));
    super.read(state, nbt);
  }

  @Override
  public CompoundNBT write(CompoundNBT compound) {
    compound.put("inv", itemHandler.serializeNBT());
    return super.write(compound);
  }

  private ItemStackHandler createHandler() {
    return new ItemStackHandler(2) {
      @Override
      protected void onContentsChanged(int slot) {
        markDirty();
      }

      // TODO make this so that if it's a receiving slot always false, if it's a sending slot true only for parcels and stamps
      @Override
      public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        switch (slot) {
          case 0: return stack.getItem() == Items.GLASS_PANE;
          case 1: return stack.getItem() == ModItems.AMETHYST.get() ||
              stack.getItem() == ModItems.FIRESTONE.get();
          default:
            return false;
        }
      }

      // TODO here, for the send pane, we want to add logic to only allow 1 stamp and one parcel
      @Override
      public int getSlotLimit(int slot) {
        return 1;
      }

      @Nonnull
      @Override
      public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if(!isItemValid(slot, stack)) {
          return stack;
        }

        return super.insertItem(slot, stack, simulate);
      }
    };
  }

  //TODO remove, don't think we need this it's for being able to make hoppers feed in
  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
    if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
      return handler.cast();
    }

    return super.getCapability(cap, side);
  }

  public void lightningHasStruck() {
    boolean hasFocusInFirstSlot = this.itemHandler.getStackInSlot(0).getCount() > 0
        && this.itemHandler.getStackInSlot(0).getItem() == Items.GLASS_PANE;
    boolean hasAmethystInSecondSlot = this.itemHandler.getStackInSlot(1).getCount() > 0
        && this.itemHandler.getStackInSlot(1).getItem() == ModItems.AMETHYST.get();

    if(hasFocusInFirstSlot && hasAmethystInSecondSlot) {
      this.itemHandler.getStackInSlot(0).shrink(1);
      this.itemHandler.getStackInSlot(1).shrink(1);

      this.itemHandler.insertItem(1, new ItemStack(ModItems.FIRESTONE.get()), false);
    }
  }
}