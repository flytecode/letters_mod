package net.minecraft.network.protocol.game;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;

public class ServerboundCustomPayloadPacket implements Packet<ServerGamePacketListener>, net.minecraftforge.fmllegacy.network.ICustomPacket<ServerboundCustomPayloadPacket> {
   private static final int f_179586_ = 32767;
   public static final ResourceLocation f_133979_ = new ResourceLocation("brand");
   private final ResourceLocation f_133980_;
   private final FriendlyByteBuf f_133981_;

   public ServerboundCustomPayloadPacket(ResourceLocation p_133985_, FriendlyByteBuf p_133986_) {
      this.f_133980_ = p_133985_;
      this.f_133981_ = p_133986_;
   }

   public ServerboundCustomPayloadPacket(FriendlyByteBuf p_179588_) {
      this.f_133980_ = p_179588_.m_130281_();
      int i = p_179588_.readableBytes();
      if (i >= 0 && i <= 32767) {
         this.f_133981_ = new FriendlyByteBuf(p_179588_.readBytes(i));
      } else {
         throw new IllegalArgumentException("Payload may not be larger than 32767 bytes");
      }
   }

   public void m_5779_(FriendlyByteBuf p_133994_) {
      p_133994_.m_130085_(this.f_133980_);
      p_133994_.writeBytes((ByteBuf)this.f_133981_.copy()); //This may be access multiple times, from multiple threads, lets be safe like the S->C packet
   }

   public void m_5797_(ServerGamePacketListener p_133992_) {
      p_133992_.m_7423_(this);
      this.f_133981_.release();
   }

   public ResourceLocation m_179589_() {
      return this.f_133980_;
   }

   public FriendlyByteBuf m_179590_() {
      return this.f_133981_;
   }
}
