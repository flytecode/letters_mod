package net.minecraft.network.protocol.login;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.core.SerializableUUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ClientboundGameProfilePacket implements Packet<ClientLoginPacketListener> {
   private final GameProfile f_134764_;

   public ClientboundGameProfilePacket(GameProfile p_134767_) {
      this.f_134764_ = p_134767_;
   }

   public ClientboundGameProfilePacket(FriendlyByteBuf p_179814_) {
      int[] aint = new int[4];

      for(int i = 0; i < aint.length; ++i) {
         aint[i] = p_179814_.readInt();
      }

      UUID uuid = SerializableUUID.m_123281_(aint);
      String s = p_179814_.m_130136_(16);
      this.f_134764_ = new GameProfile(uuid, s);
   }

   public void m_5779_(FriendlyByteBuf p_134776_) {
      for(int i : SerializableUUID.m_123277_(this.f_134764_.getId())) {
         p_134776_.writeInt(i);
      }

      p_134776_.m_130070_(this.f_134764_.getName());
   }

   public void m_5797_(ClientLoginPacketListener p_134773_) {
      p_134773_.m_7056_(this);
   }

   public GameProfile m_134774_() {
      return this.f_134764_;
   }
}