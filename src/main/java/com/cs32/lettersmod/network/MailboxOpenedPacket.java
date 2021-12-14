package com.cs32.lettersmod.network;

import java.util.function.Supplier;

import com.cs32.lettersmod.courier.MailCourier;
import com.cs32.lettersmod.saveddata.SavedDataClass;
import com.cs32.lettersmod.tileentity.MailboxTile;
import com.google.gson.Gson;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

public class MailboxOpenedPacket {
  private BlockPos pos;

  public MailboxOpenedPacket() {
  }

  public MailboxOpenedPacket(BlockPos pos) {
    this.pos = pos;
  }

  public MailboxOpenedPacket(PacketBuffer buf) {
    pos = buf.readBlockPos();
  }

  public void toBytes(PacketBuffer buf) {
    buf.writeBlockPos(this.pos);
  }

  public void handle(Supplier<NetworkEvent.Context> ctx) {
    ctx.get().enqueueWork(() -> {

      World world = ctx.get().getSender().world;


      if (!world.isRemote()) {
        TileEntity te = world.getTileEntity(pos);
        assert te != null;
        if (te instanceof MailboxTile) {
          MailboxTile tileEntity = (MailboxTile) te;

          // get number of empty slots
          int numEmptySlots = tileEntity.getEmptySlots(); // TODO fix/override so that it does not acct for player inventory
          System.out.println("empty slots: " + numEmptySlots);

          // call the getmail command to update the parcelList
          SavedDataClass saver = SavedDataClass.forWorld((ServerWorld) world);
          String resultString = MailCourier.getMail(saver, numEmptySlots);
          System.out.println(resultString);

          // now the parcel list should have data
          ListNBT parcelList = (ListNBT) saver.data.get("parcelList");
          assert (parcelList != null);

          // create a new gson to deserialize the parcelstrings
          Gson gson = new Gson();

          // destroy all of the items that were in there before, since we are REFRESHING
          tileEntity.removeAllItems();

          // loop through and repopulate the mailbox
          for (INBT p : parcelList) {
            // cast it to a CompoundNBT, then get the parcelString and turn into ItemStack

            // TODO fix getting the parcelString out of here idk if i should use getCompound
            CompoundNBT parcelNBT = ((CompoundNBT) p).getCompound("parcelString");

            System.out.println("parcelNBT: "+ parcelNBT);

            // TODO figure out how to deserialize into an item
//            ItemStack parcel = new ItemStack();
//            parcel.deserializeNBT(parcelNBT);

//            ItemStack parcel = gson.fromJson(parcelString, ItemStack.class);
//            System.out.println("parcel: " + parcel);

            // add to tile entity and error check
//            if (parcel != null) {
//              if (!tileEntity.addParcel(parcel)) {
//                throw new IllegalStateException("ERROR mailbox ran out of room");
//              }
//            }

          }
        } else {
          throw new IllegalStateException("ERROR this is not a MailboxTile");
        }
      }
    });
    ctx.get().setPacketHandled(true);
  }
}