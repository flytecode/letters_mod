package com.cs32.lettersmod.network;

import java.util.function.Supplier;

import com.cs32.lettersmod.courier.MailCourier;
import com.cs32.lettersmod.saveddata.SavedDataClass;
import com.cs32.lettersmod.tileentity.MailboxTile;
import com.google.gson.Gson;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
          tileEntity.refreshItems();
        } else {
          throw new IllegalStateException("ERROR this is not a MailboxTile");
        }
      }
    });
    ctx.get().setPacketHandled(true);
  }
}