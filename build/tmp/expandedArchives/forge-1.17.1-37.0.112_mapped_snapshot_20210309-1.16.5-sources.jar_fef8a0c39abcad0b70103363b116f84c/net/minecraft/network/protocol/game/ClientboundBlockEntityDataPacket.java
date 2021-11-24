package net.minecraft.network.protocol.game;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ClientboundBlockEntityDataPacket implements Packet<ClientGamePacketListener> {
   public static final int f_178607_ = 1;
   public static final int f_178608_ = 2;
   public static final int f_178609_ = 3;
   public static final int f_178610_ = 4;
   public static final int f_178611_ = 5;
   public static final int f_178612_ = 6;
   public static final int f_178613_ = 7;
   public static final int f_178614_ = 8;
   public static final int f_178615_ = 9;
   public static final int f_178616_ = 11;
   public static final int f_178617_ = 12;
   public static final int f_178618_ = 13;
   public static final int f_178619_ = 14;
   private final BlockPos f_131690_;
   private final int f_131691_;
   private final CompoundTag f_131692_;

   public ClientboundBlockEntityDataPacket(BlockPos p_131695_, int p_131696_, CompoundTag p_131697_) {
      this.f_131690_ = p_131695_;
      this.f_131691_ = p_131696_;
      this.f_131692_ = p_131697_;
   }

   public ClientboundBlockEntityDataPacket(FriendlyByteBuf p_178621_) {
      this.f_131690_ = p_178621_.m_130135_();
      this.f_131691_ = p_178621_.readUnsignedByte();
      this.f_131692_ = p_178621_.m_130260_();
   }

   public void m_5779_(FriendlyByteBuf p_131706_) {
      p_131706_.m_130064_(this.f_131690_);
      p_131706_.writeByte((byte)this.f_131691_);
      p_131706_.m_130079_(this.f_131692_);
   }

   public void m_5797_(ClientGamePacketListener p_131703_) {
      p_131703_.m_7545_(this);
   }

   public BlockPos m_131704_() {
      return this.f_131690_;
   }

   public int m_131707_() {
      return this.f_131691_;
   }

   public CompoundTag m_131708_() {
      return this.f_131692_;
   }
}