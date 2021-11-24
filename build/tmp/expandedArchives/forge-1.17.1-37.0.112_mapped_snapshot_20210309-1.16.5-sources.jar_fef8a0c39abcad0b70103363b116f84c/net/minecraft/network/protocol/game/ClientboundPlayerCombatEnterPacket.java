package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ClientboundPlayerCombatEnterPacket implements Packet<ClientGamePacketListener> {
   public ClientboundPlayerCombatEnterPacket() {
   }

   public ClientboundPlayerCombatEnterPacket(FriendlyByteBuf p_179051_) {
   }

   public void m_5779_(FriendlyByteBuf p_179053_) {
   }

   public void m_5797_(ClientGamePacketListener p_179057_) {
      p_179057_.m_142058_(this);
   }
}