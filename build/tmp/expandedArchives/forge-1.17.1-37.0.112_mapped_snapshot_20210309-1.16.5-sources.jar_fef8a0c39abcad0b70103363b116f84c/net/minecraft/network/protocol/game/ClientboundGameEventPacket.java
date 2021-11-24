package net.minecraft.network.protocol.game;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ClientboundGameEventPacket implements Packet<ClientGamePacketListener> {
   public static final ClientboundGameEventPacket.Type f_132153_ = new ClientboundGameEventPacket.Type(0);
   public static final ClientboundGameEventPacket.Type f_132154_ = new ClientboundGameEventPacket.Type(1);
   public static final ClientboundGameEventPacket.Type f_132155_ = new ClientboundGameEventPacket.Type(2);
   public static final ClientboundGameEventPacket.Type f_132156_ = new ClientboundGameEventPacket.Type(3);
   public static final ClientboundGameEventPacket.Type f_132157_ = new ClientboundGameEventPacket.Type(4);
   public static final ClientboundGameEventPacket.Type f_132158_ = new ClientboundGameEventPacket.Type(5);
   public static final ClientboundGameEventPacket.Type f_132159_ = new ClientboundGameEventPacket.Type(6);
   public static final ClientboundGameEventPacket.Type f_132160_ = new ClientboundGameEventPacket.Type(7);
   public static final ClientboundGameEventPacket.Type f_132161_ = new ClientboundGameEventPacket.Type(8);
   public static final ClientboundGameEventPacket.Type f_132162_ = new ClientboundGameEventPacket.Type(9);
   public static final ClientboundGameEventPacket.Type f_132163_ = new ClientboundGameEventPacket.Type(10);
   public static final ClientboundGameEventPacket.Type f_132164_ = new ClientboundGameEventPacket.Type(11);
   public static final int f_178859_ = 0;
   public static final int f_178860_ = 101;
   public static final int f_178861_ = 102;
   public static final int f_178862_ = 103;
   public static final int f_178863_ = 104;
   private final ClientboundGameEventPacket.Type f_132165_;
   private final float f_132166_;

   public ClientboundGameEventPacket(ClientboundGameEventPacket.Type p_132170_, float p_132171_) {
      this.f_132165_ = p_132170_;
      this.f_132166_ = p_132171_;
   }

   public ClientboundGameEventPacket(FriendlyByteBuf p_178865_) {
      this.f_132165_ = ClientboundGameEventPacket.Type.f_132182_.get(p_178865_.readUnsignedByte());
      this.f_132166_ = p_178865_.readFloat();
   }

   public void m_5779_(FriendlyByteBuf p_132180_) {
      p_132180_.writeByte(this.f_132165_.f_132183_);
      p_132180_.writeFloat(this.f_132166_);
   }

   public void m_5797_(ClientGamePacketListener p_132177_) {
      p_132177_.m_7616_(this);
   }

   public ClientboundGameEventPacket.Type m_132178_() {
      return this.f_132165_;
   }

   public float m_132181_() {
      return this.f_132166_;
   }

   public static class Type {
      static final Int2ObjectMap<ClientboundGameEventPacket.Type> f_132182_ = new Int2ObjectOpenHashMap<>();
      final int f_132183_;

      public Type(int p_132186_) {
         this.f_132183_ = p_132186_;
         f_132182_.put(p_132186_, this);
      }
   }
}