package net.minecraft.network.protocol.login;

import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ServerboundCustomQueryPacket implements Packet<ServerLoginPacketListener>, net.minecraftforge.fmllegacy.network.ICustomPacket<ServerboundCustomQueryPacket> {
   private static final int f_179821_ = 1048576;
   private final int f_134825_;
   private final FriendlyByteBuf f_134826_;

   public ServerboundCustomQueryPacket(int p_134829_, @Nullable FriendlyByteBuf p_134830_) {
      this.f_134825_ = p_134829_;
      this.f_134826_ = p_134830_;
   }

   public ServerboundCustomQueryPacket(FriendlyByteBuf p_179823_) {
      this.f_134825_ = p_179823_.m_130242_();
      if (p_179823_.readBoolean()) {
         int i = p_179823_.readableBytes();
         if (i < 0 || i > 1048576) {
            throw new IllegalArgumentException("Payload may not be larger than 1048576 bytes");
         }

         this.f_134826_ = new FriendlyByteBuf(p_179823_.readBytes(i));
      } else {
         this.f_134826_ = null;
      }

   }

   public void m_5779_(FriendlyByteBuf p_134838_) {
      p_134838_.m_130130_(this.f_134825_);
      if (this.f_134826_ != null) {
         p_134838_.writeBoolean(true);
         p_134838_.writeBytes(this.f_134826_.copy());
      } else {
         p_134838_.writeBoolean(false);
      }

   }

   public void m_5797_(ServerLoginPacketListener p_134836_) {
      p_134836_.m_7223_(this);
   }

   public int m_179824_() {
      return this.f_134825_;
   }

   public FriendlyByteBuf m_179825_() {
      return this.f_134826_;
   }
}
