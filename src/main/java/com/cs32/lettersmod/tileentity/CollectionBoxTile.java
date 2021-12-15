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

  // TODO fix bug with shift-click, fix item reappearing after you send

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
    return new ItemStackHandler(1) {
      @Override
      protected void onContentsChanged(int slot) {
        markDirty();
      }

      // TODO once envelopes are implemented, make this ensure you can only send envelopes
      @Override
      public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        switch (slot) {
          case 0: return true; // return stack.getItem() == ModItems.AMETHYST.get() || stack.getItem() == ModItems.FIRESTONE.get();
          default:
            return false;
        }
      }

      // TODO allow only 1 sealed envelope to be sent
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

  /**
   * Getter for items in the send slot
   * @return ItemStack of whatever is in send slot right now
   */
  public ItemStack getSendSlot() {
    System.out.println("getSendSlot");
    return this.itemHandler.getStackInSlot(0);
  }

  /**
   * function that is called after a parcel has been sent to remove from container
   */
  public void parcelSent() {
    System.out.println("parcelSent");
    boolean hasItemsInSlot = this.itemHandler.getStackInSlot(0).getCount() > 0;
    if(hasItemsInSlot) {
//      this.itemHandler.getStackInSlot(0).shrink(1);
//      markDirty();
      this.itemHandler.extractItem(0, 64, false);
      markDirty();
    }
  }
}