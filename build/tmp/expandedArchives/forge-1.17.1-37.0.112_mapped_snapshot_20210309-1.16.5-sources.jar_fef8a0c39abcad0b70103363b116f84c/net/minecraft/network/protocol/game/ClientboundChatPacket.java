package net.minecraft.network.protocol.game;

import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;

public class ClientboundChatPacket implements Packet<ClientGamePacketListener> {
   private final Component f_131821_;
   private final ChatType f_131822_;
   private final UUID f_131823_;

   public ClientboundChatPacket(Component p_131826_, ChatType p_131827_, UUID p_131828_) {
      this.f_131821_ = p_131826_;
      this.f_131822_ = p_131827_;
      this.f_131823_ = p_131828_;
   }

   public ClientboundChatPacket(FriendlyByteBuf p_178776_) {
      this.f_131821_ = p_178776_.m_130238_();
      this.f_131822_ = ChatType.m_130611_(p_178776_.readByte());
      this.f_131823_ = p_178776_.m_130259_();
   }

   public void m_5779_(FriendlyByteBuf p_131838_) {
      p_131838_.m_130083_(this.f_131821_);
      p_131838_.writeByte(this.f_131822_.m_130610_());
      p_131838_.m_130077_(this.f_131823_);
   }

   public void m_5797_(ClientGamePacketListener p_131835_) {
      p_131835_.m_5784_(this);
   }

   public Component m_131836_() {
      return this.f_131821_;
   }

   public ChatType m_131840_() {
      return this.f_131822_;
   }

   public UUID m_131841_() {
      return this.f_131823_;
   }

   public boolean m_6588_() {
      return true;
   }
}