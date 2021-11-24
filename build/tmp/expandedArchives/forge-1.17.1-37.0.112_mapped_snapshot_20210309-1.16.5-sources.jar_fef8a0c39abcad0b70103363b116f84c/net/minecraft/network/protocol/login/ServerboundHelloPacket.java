package net.minecraft.network.protocol.login;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ServerboundHelloPacket implements Packet<ServerLoginPacketListener> {
   private final GameProfile f_134839_;

   public ServerboundHelloPacket(GameProfile p_134842_) {
      this.f_134839_ = p_134842_;
   }

   public ServerboundHelloPacket(FriendlyByteBuf p_179827_) {
      this.f_134839_ = new GameProfile((UUID)null, p_179827_.m_130136_(16));
   }

   public void m_5779_(FriendlyByteBuf p_134851_) {
      p_134851_.m_130070_(this.f_134839_.getName());
   }

   public void m_5797_(ServerLoginPacketListener p_134848_) {
      p_134848_.m_5990_(this);
   }

   public GameProfile m_134849_() {
      return this.f_134839_;
   }
}